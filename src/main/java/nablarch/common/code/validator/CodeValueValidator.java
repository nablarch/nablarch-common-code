package nablarch.common.code.validator;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import nablarch.common.code.CodeUtil;
import nablarch.core.message.StringResource;
import nablarch.core.util.StringUtil;
import nablarch.core.validation.ValidationContext;
import nablarch.core.validation.ValidationResultMessageUtil;
import nablarch.core.validation.validator.StringValidatorSupport;


/**
 * コード値をチェックするクラス。
 *
 * <p>
 *   {@link CodeValue}で指定したコード値と、プロパティの値が合致するかチェックする。
 *
 *   <p>
 *     <b>使用するための設定</b>
 *   </p>
 *   本バリデータを使用するためにはデフォルトのメッセージIDを指定する必要がある。
 *   <pre>
 *     {@code <component name="codeValueValidator" class="nablarch.common.code.validator.CodeValueValidator">
 *         <property name="messageId" value="MSGXXXXXX"/>
 *     </component>}
 *   </pre>
 *
 *   <p>
 *     <b>プロパティの設定</b>
 *   </p>
 *   プロパティのセッタに{@link CodeValue}アノテーションを次のように設定する。
 *   <pre>
 *     genderがcodeIdとpatternで選択された値と合致するかチェック
 *     {@code @PropertyName("性別")}
 *     {@code @CodeValue(codeId="0001", pattern="PATTERN1")}
 *     {@code public String setGender(String gender) {
 *       this.gender = gender;
 *     }}
 *
 *     genderがコード値として有効であるかのみをチェック
 *     {@code @PropertyName("性別")}
 *     {@code @CodeValue(codeId="0001")}
 *     {@code public String setGender(String gender) {
 *       this.gender = gender;
 *     }}
 *   </pre>
 * </p>
 * @author Koichi Asano
 *
 */
public class CodeValueValidator extends StringValidatorSupport<CodeValue> {

    /**
     * コードに含まれない値が指定された場合のデフォルトのエラーメッセージのメッセージID。
     */
    private String messageId;

    /**
     * コードに含まれない値が指定された場合のデフォルトのエラーメッセージのメッセージIDを設定する。<br/>
     * 例 : "{0}には\"{1}\"のいずれかの値を指定してください。"<br/>
     * <br/>
     * ※通常コードは画面上のドロップダウンやセレクトボックスで選択することが多い。
     * この場合、このエラーメッセージはプログラムバグかユーザによるプログラム改竄以外に表示されることはない。
     * このため、本エラーメッセージはインプットボックスでコードを入力するという特殊な入力を行う画面以外では使用されない。
     * 
     * @param messageId コードに含まれない値が指定された場合のデフォルトのエラーメッセージのメッセージID
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * {@inheritDoc}
     */
    public Class<? extends Annotation> getAnnotationClass() {
        return CodeValue.class;
    }

    /**
     * {@inheritDoc}<br/>
     * コード値の有効性をチェックするバリデーションを行なう。
     * 
     */
    @Override 
    public <T> boolean validateSingleValue(ValidationContext<T> context,
            String propertyName, Object propertyDisplayObject,
            CodeValue codeValue, String value) {
        if (StringUtil.isNullOrEmpty(value)) {
            return true;
        }
        if (codeValue.pattern().equals("")) {
            // パターン指定なしの場合のコード値チェック。
            if (!CodeUtil.contains(codeValue.codeId(), value)) {
                addMessage(context, propertyName, propertyDisplayObject, codeValue);
                return false;
            }
        } else {
            if (!CodeUtil.contains(codeValue.codeId(), codeValue.pattern(), value)) {
                addMessage(context, propertyName, propertyDisplayObject, codeValue);
                return false;
            }
        }
        return true;
    }

    /**
     * メッセージをValidationContextに追加する。
     * 
     * @param <T> バリデーション結果で取得できる型
     * @param context ValidationContext
     * @param propertyName プロパティ名
     * @param propertyDisplayName プロパティの表示名オブジェクト
     * @param codeValue バリデーション条件のアノテーション
     */
    private <T> void addMessage(ValidationContext<T> context,
            String propertyName, Object propertyDisplayName, CodeValue codeValue) {
        ValidationResultMessageUtil.addResultMessage(context, propertyName,
                getMessageId(codeValue), propertyDisplayName, new AllCodeValuesStringResource(
                codeValue.codeId(), codeValue.pattern()));
    }

    /**
     * メッセージIDを取得する。<br/>
     * <p/>
     * バリデーション条件のアノテーションにメッセージIDが指定されていた場合には、そのメッセージIDを返却する。
     * アノテーションにメッセージIDが指定されていない場合には、{@link #setMessageId(String)}で設定された
     * メッセージIDを返却する。
     *
     * @param codeValue バリデーション条件のアノテーション
     * @return メッセージID
     */
    private String getMessageId(CodeValue codeValue) {
        if (StringUtil.isNullOrEmpty(codeValue.messageId())) {
            return messageId;
        } else {
            return codeValue.messageId();
        }
    }

    /**
     * コード値を全て取得する文字列リソース。
     * 
     */
    private static class AllCodeValuesStringResource implements StringResource {

        /**
         * コンストラクタ。
         * 
         * @param codeId コードID
         * @param pattern 使用するパターンのカラム名
         */
        public AllCodeValuesStringResource(String codeId, String pattern) {
            this.codeId = codeId;
            this.pattern = pattern;
        }

        /**
         * コードID。
         */
        private final String codeId;
        /**
         * 使用するパターンのカラム名
         */
        private final String pattern;
        /**
         * {@inheritDoc}
         */
        public String getId() {
            // このメソッドは使用されません。
            return null;
        }

        /**
         * {@inheritDoc}<br/>
         * <br/>
         * Localeに紐付くコード値のリストを取得する。
         */
        public String getValue(Locale locale) {
            StringBuilder allowValues = new StringBuilder();

            boolean isFirst = true;

            List<String> values;
            if ("".equals(pattern)) {
                values = CodeUtil.getValues(codeId, locale);
            } else {
                values = CodeUtil.getValues(codeId, pattern, locale);
            }

            for (String value : values) {
                if (!isFirst) {
                    allowValues.append(" , ");
                }
                allowValues.append('\"');
                allowValues.append(value);
                allowValues.append('\"');

                isFirst = false;
            }

            return allowValues.toString();
        }
        
    }

    @Override
    public CodeValue createAnnotation(final Map<String, Object> params) {
        return new CodeValue() {
            public Class<? extends Annotation> annotationType() {
                return CodeValue.class;
            }
            public String codeId() {
                String codeId = (String) params.get("codeId"); 
                if (codeId == null) {
                    throw new IllegalArgumentException(
                    "codeId must be assigned to execute the validation of @CodeValue."
                    );
                }
                return codeId;
            }

            public String pattern() {
                String pattern = (String) params.get("pattern");
                return (pattern == null) ? ""
                                         : pattern;
            }

            public String messageId() {
                String messageId = (String) params.get("messageId");
                return (messageId == null) ? ""
                                           : messageId;
            }
        };
    }
}
