package nablarch.common.code.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import nablarch.common.code.MockCodeLoader;
import nablarch.common.code.TestCodeCreator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import nablarch.common.code.CodeName;
import nablarch.common.code.CodePattern;
import nablarch.core.ThreadContext;

import nablarch.core.validation.PropertyName;
import nablarch.core.validation.ValidateFor;
import nablarch.core.validation.ValidationContext;
import nablarch.core.validation.ValidationUtil;
import nablarch.test.support.SystemRepositoryResource;
import nablarch.test.support.message.MockStringResourceHolder;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CodeValueValidatorTest {

    @Rule
    public SystemRepositoryResource repositoryResource = new SystemRepositoryResource(
            "nablarch/common/code/validator/code-value-validator-test.xml");

    private static final String[][] MESSAGES = {
            {"MSG00001", "ja", "{0}には'{'{1}'}'のいずれかの値を指定してください。", "en", "{0}''s value must be in '{'{1}'}'"},
            {"MSG00002", "ja", "テストメッセージ01。", "en", "test message 01."},
            {"PROP0001", "ja", "性別", "en", "gender"},
            {"PROP0002", "ja", "状態", "en", "state"},
    };

    private static List<CodePattern> patternList = new ArrayList<CodePattern>();
    private static List<CodeName> nameList = new ArrayList<CodeName>();

    @BeforeClass
    public static void classSetup() throws Exception {
        patternList = TestCodeCreator.createPatternList();
        nameList = TestCodeCreator.createNameList();
    }

    @Before
    public void setUp() throws Exception {
        repositoryResource.getComponentByType(MockStringResourceHolder.class)
                .setMessages(MESSAGES);
        Map<String, String[]> params = new HashMap<String, String[]>();
        params.put("param", new String[] {"10"});
        MockCodeLoader codeLoader = repositoryResource.getComponent("codeLoader");
        codeLoader.setPatterns(patternList);
        codeLoader.setNames(nameList);
        codeLoader.initialize();
    }

    @Test
    public void testValidate() throws Exception {

        {
            Map<String, String[]> params = new HashMap<String, String[]>();
            params.put("gender", new String[] {"01"});
            ValidationContext<UserEntity> result = ValidationUtil.validateAndConvertRequest("", UserEntity.class,
                    params, "test");

            assertTrue(result.isValid());
        }

        {
            Map<String, String[]> params = new HashMap<String, String[]>();
            params.put("gender", new String[] {"02"});
            ValidationContext<UserEntity> result = ValidationUtil.validateAndConvertRequest("", UserEntity.class,
                    params, "test");
            assertTrue(result.isValid());
        }

        {
            Map<String, String[]> params = new HashMap<String, String[]>();
            params.put("gender", new String[] {"03"});
            ValidationContext<UserEntity> result = ValidationUtil.validateAndConvertRequest("", UserEntity.class,
                    params, "test");
            assertFalse(result.isValid());
            assertEquals(1, result.getMessages()
                    .size());

            // エラーメッセージのコード表示順は言語依存
            ThreadContext.setLanguage(Locale.JAPANESE);
            assertEquals("性別には{\"01\" , \"02\"}のいずれかの値を指定してください。", result.getMessages()
                    .get(0)
                    .formatMessage());

            ThreadContext.setLanguage(Locale.ENGLISH);
            assertEquals("gender's value must be in {\"02\" , \"01\"}", result.getMessages()
                    .get(0)
                    .formatMessage());
        }

        {
            // 空文字列はOK
            Map<String, String[]> params = new HashMap<String, String[]>();
            params.put("gender", new String[] {""});
            ValidationContext<UserEntity> result = ValidationUtil.validateAndConvertRequest("", UserEntity.class,
                    params, "test");
            assertTrue(result.isValid());
        }

        {
            // 空文字列はOK
            Map<String, String> params = new HashMap<String, String>();
            params.put("gender", "");
            ValidationContext<UserEntity> result = ValidationUtil.validateAndConvertRequest("", UserEntity.class,
                    params, "test");
            assertTrue(result.isValid());
        }
    }


    @Test
    public void testValidateWithPattern() throws Exception {
        {
            Map<String, String[]> params = new HashMap<String, String[]>();
            params.put("state", new String[] {"01"});
            ValidationContext<BatchExecutionEntity> result = ValidationUtil.validateAndConvertRequest("",
                    BatchExecutionEntity.class, params, "validateAll");

            assertTrue(result.isValid());
        }

        {
            Map<String, String[]> params = new HashMap<String, String[]>();
            params.put("state", new String[] {"02"});
            ValidationContext<BatchExecutionEntity> result = ValidationUtil.validateAndConvertRequest("",
                    BatchExecutionEntity.class, params, "validateAll");
            assertTrue(result.isValid());
        }

        {
            Map<String, String[]> params = new HashMap<String, String[]>();
            params.put("state", new String[] {"05"});
            ValidationContext<BatchExecutionEntity> result = ValidationUtil.validateAndConvertRequest("",
                    BatchExecutionEntity.class, params, "validateAll");
            assertTrue(result.isValid());
        }

        {
            Map<String, String[]> params = new HashMap<String, String[]>();
            params.put("state", new String[] {"03"});
            ValidationContext<BatchExecutionEntity> result = ValidationUtil.validateAndConvertRequest("",
                    BatchExecutionEntity.class, params, "validateAll");
            assertFalse(result.isValid());
            assertEquals(1, result.getMessages()
                    .size());

            ThreadContext.setLanguage(Locale.JAPANESE);
            assertEquals("状態には{\"01\" , \"02\" , \"05\"}のいずれかの値を指定してください。", result.getMessages()
                    .get(0)
                    .formatMessage());

            ThreadContext.setLanguage(Locale.ENGLISH);
            assertEquals("state's value must be in {\"01\" , \"02\" , \"05\"}", result.getMessages()
                    .get(0)
                    .formatMessage());
        }

        {
            Map<String, String[]> params = new HashMap<String, String[]>();
            params.put("state", new String[] {"04"});
            ValidationContext<BatchExecutionEntity> result = ValidationUtil.validateAndConvertRequest("",
                    BatchExecutionEntity.class, params, "validateAll");
            assertFalse(result.isValid());
            assertEquals(1, result.getMessages()
                    .size());

            ThreadContext.setLanguage(Locale.JAPANESE);
            assertEquals("状態には{\"01\" , \"02\" , \"05\"}のいずれかの値を指定してください。", result.getMessages()
                    .get(0)
                    .formatMessage());

            ThreadContext.setLanguage(Locale.ENGLISH);
            assertEquals("state's value must be in {\"01\" , \"02\" , \"05\"}", result.getMessages()
                    .get(0)
                    .formatMessage());
        }
    }

    @Test
    public void testValidateMulti() throws Exception {

        {
            Map<String, String[]> params = new HashMap<String, String[]>();
            params.put("state", new String[] {"01"});
            ValidationContext<MultiValueEntity> result = ValidationUtil.validateAndConvertRequest("",
                    MultiValueEntity.class, params, "validateAll");
            MultiValueEntity entity = result.createDirtyObject();

            assertTrue(result.isValid());
            assertArrayEquals(new Object[] {"01"}, entity.getState());
        }

        {
            Map<String, String[]> params = new HashMap<String, String[]>();
            params.put("state", new String[] {"01", "02", "05"});
            ValidationContext<MultiValueEntity> result = ValidationUtil.validateAndConvertRequest("",
                    MultiValueEntity.class, params, "validateAll");
            MultiValueEntity entity = result.createDirtyObject();

            assertTrue(result.isValid());
            assertArrayEquals(new Object[] {"01", "02", "05"}, entity.getState());
        }

        {
            Map<String, String[]> params = new HashMap<String, String[]>();
            params.put("state", new String[] {"01", "02", "04"});
            ValidationContext<MultiValueEntity> result = ValidationUtil.validateAndConvertRequest("",
                    MultiValueEntity.class, params, "validateAll");

            assertFalse(result.isValid());
            assertEquals(1, result.getMessages()
                    .size());

            ThreadContext.setLanguage(Locale.JAPANESE);
            assertEquals("テストメッセージ01。", result.getMessages()
                    .get(0)
                    .formatMessage());

            ThreadContext.setLanguage(Locale.ENGLISH);
            assertEquals("test message 01.", result.getMessages()
                    .get(0)
                    .formatMessage());
        }

        {
            Map<String, String[]> params = new HashMap<String, String[]>();
            params.put("state", new String[] {"03", "04"});
            ValidationContext<MultiValueEntity> result = ValidationUtil.validateAndConvertRequest("",
                    MultiValueEntity.class, params, "validateAll");

            assertFalse(result.isValid());
            assertEquals(1, result.getMessages()
                    .size());
            assertEquals("MSG00002", result.getMessages()
                    .get(0)
                    .getMessageId());

            ThreadContext.setLanguage(Locale.JAPANESE);
            assertEquals("テストメッセージ01。", result.getMessages()
                    .get(0)
                    .formatMessage());

            ThreadContext.setLanguage(Locale.ENGLISH);
            assertEquals("test message 01.", result.getMessages()
                    .get(0)
                    .formatMessage());
        }

    }

    /**
     * {@link CodeValueValidator#createAnnotation(Map)}のテスト。
     */
    @Test
    public void testCreateAnnotation() {
        CodeValueValidator sut = new CodeValueValidator();

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("codeId", "id");
        CodeValue annotation = sut.createAnnotation(param);

        assertThat(annotation, is(instanceOf(annotation.annotationType())));
        assertThat(annotation.codeId(), is("id"));
        assertThat("パターンは未指定なので空文字列", annotation.pattern(), is(""));
        assertThat("メッセージIDは未指定なので空文字列", annotation.messageId(), is(""));

        param.put("pattern", "1");
        param.put("messageId", "id");
        annotation = sut.createAnnotation(param);
        assertThat("指定したパターンが取得できる", annotation.pattern(), is("1"));
        assertThat("指定したメッセージIDが取得できる", annotation.messageId(), is("id"));

        param.put("codeId", null);
        annotation = sut.createAnnotation(param);

        try {
            annotation.codeId();
            fail();
        } catch (Exception e) {
            assertThat(e.getMessage(), is("codeId must be assigned to execute the validation of @CodeValue."));
        }
    }

    public static class UserEntity {

        private String gender;

        public UserEntity(Map<String, Object> params) {
            this.gender = (String) params.get("gender");
        }

        @PropertyName(messageId = "PROP0001")
        @CodeValue(codeId = "0001")
        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getGender() {
            return gender;
        }

        private static final String[] INSERT_PARAMS = new String[] {"gender"};

        @ValidateFor({"test"})
        public static void validateForTest(ValidationContext<UserEntity> context) {
            ValidationUtil.validate(context, INSERT_PARAMS);
        }
    }

    public static class BatchExecutionEntity {

        private String state;

        public BatchExecutionEntity(Map<String, Object> params) {
            this.state = (String) params.get("state");
        }

        @PropertyName(messageId = "PROP0002")
        @CodeValue(codeId = "0002", pattern = "PATTERN1")
        public void setState(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }

        @ValidateFor("validateAll")
        public static void validateAll(ValidationContext<BatchExecutionEntity> context) {
            ValidationUtil.validateWithout(context, new String[0]);
        }
    }


    public static class MultiValueEntity {

        public MultiValueEntity(Map<String, Object> params) {
            this.state = (String[]) params.get("state");
        }

        // 複数の状態を選択できる場合（select box の複数選択を想定）
        private String[] state;

        @PropertyName(messageId = "PROP0002")
        @CodeValue(codeId = "0002", pattern = "PATTERN1", messageId = "MSG00002")
        public void setState(String[] state) {
            this.state = state;
        }

        public String[] getState() {
            return state;
        }

        @ValidateFor("validateAll")
        public static void validateAll(ValidationContext<BatchExecutionEntity> context) {
            ValidationUtil.validateWithout(context, new String[0]);
        }
    }
}