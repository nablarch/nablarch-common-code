package nablarch.common.code.validator.ee;

import nablarch.common.code.CodeUtil;
import nablarch.common.code.validator.ee.CodeValue.CodeValueArrayValidator;
import nablarch.common.code.validator.ee.CodeValue.CodeValueValidator;
import nablarch.core.util.StringUtil;
import nablarch.core.util.annotation.Published;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

/**
 * 指定したコードIDの値と、プロパティの値が合致するかチェックするアノテーション。
 *
 * <p>使用例</p>
 * <pre>
 *     genderがcodeIdとpatternで選択された値と合致するかチェック
 *     {@code public class Sample}{
 *         {@code @CodeValue(codeId = "GENDER", pattern = "PATTERN1")
 *         String gender;
 *     }}
 *
 *     genderがcodeIdで選択された値と合致するかチェック
 *     {@code public class Sample}{
 *         {@code @CodeValue(codeId = "GENDER")
 *         String gender;
 *     }}
 * </pre>
 *
 * @author T.Kawasaki
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {CodeValueValidator.class, CodeValueArrayValidator.class})
@Published
public @interface CodeValue {

    /** グループ */
    Class<?>[] groups() default {};

    /** メッセージ */
    String message() default "{nablarch.common.code.validator.ee.CodeValue.message}";

    /** payload */
    Class<? extends Payload>[] payload() default {};

    /**
     * {@link CodeValue}アノテーションを複数指定するためのアノテーション。
     * <p/>
     * 使用例
     * <pre>
     *     genderがcodeIdとpatternで選択された値と合致するかチェック
     *     {@code public class Sample}{
     *         {@code @CodeValue.List(}{
     *             {@code @CodeValue(codeId = "GENDER", pattern = "PATTERN1"),}
     *             {@code @CodeValue(codeId = "GENDER", pattern = "PATTERN2")}
     *         })
     *         String gender;
     *     }
     * </pre>
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        CodeValue[] value();
    }

    /** コードID */
    String codeId();

    /** 使用するパターンのカラム名 */
    String pattern() default "";

    /**
     * 検証対象の値が、指定したコードに適合することを検証する{@link ConstraintValidator}実装クラス(抽象クラス)。
     *
     * @author Naoki Yamamoto
     */
    abstract class AbstractCodeValueValidator<T> implements ConstraintValidator<CodeValue, T> {

        /** コードID */
        private String codeId;

        /** パターン */
        private String pattern;

        @Override
        public void initialize(CodeValue constraintAnnotation) {
            codeId = constraintAnnotation.codeId();
            pattern = constraintAnnotation.pattern();
        }

        @Override
        public abstract boolean isValid(T t, final ConstraintValidatorContext context);

        /**
         * 対象の値が指定したコードに適合するかを検証する。
         *
         * @param value コード値
         * @return 適合する場合に{@code true}を返す
         */
        protected boolean isValidCodeValue(String value) {
            if (StringUtil.isNullOrEmpty(value)) {
                return true;
            }

            return StringUtil.hasValue(pattern) ?
                    CodeUtil.contains(codeId, pattern, value) :  // パターンあり
                    CodeUtil.contains(codeId, value);            // パターンなし
        }
    }

    /**
     * 検証対象の値が、指定したコードに適合することを検証する{@link AbstractCodeValueValidator}の継承クラス。
     *
     * * @author Naoki Yamamoto
     */
    class CodeValueValidator extends AbstractCodeValueValidator<String> {

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return isValidCodeValue(value);
        }
    }

    /**
     * 検証対象の値の配列が、指定したコードに全て適合することを検証する{@link AbstractCodeValueValidator}の継承クラス。
     *
     * * @author Naoki Yamamoto
     */
    class CodeValueArrayValidator extends AbstractCodeValueValidator<String[]> {

        @Override
        public boolean isValid(String[] values, ConstraintValidatorContext context) {
            if (StringUtil.isNullOrEmpty(values)) {
                return true;
            }
            for (String value : values) {
                if (!isValidCodeValue(value)) {
                    return false;
                }
            }
            return true;
        }
    }
}
