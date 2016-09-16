package nablarch.common.code.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import nablarch.core.util.annotation.Published;
import nablarch.core.validation.Validation;

/**
 * コード値を表わすアノテーション。
 * <p>
 *  バリデーションの内容と設定については{@link CodeValueValidator}を参照。
 * </p>
 * @author Koichi Asano
 */
@Validation
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Published
public @interface CodeValue {

    /**
     * コードID。
     */
    String codeId();
    
    /**
     * 使用するパターンのカラム名。
     */
    String pattern() default "";

    /**
     * コード値以外が含まれた場合に出力するエラーメッセージ。
     */
    String messageId() default "";
}
