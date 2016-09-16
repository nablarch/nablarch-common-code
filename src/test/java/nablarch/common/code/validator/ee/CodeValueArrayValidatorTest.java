package nablarch.common.code.validator.ee;

import nablarch.common.code.validator.ee.CodeValue.CodeValueArrayValidator;
import nablarch.test.support.SystemRepositoryResource;
import nablarch.test.support.db.helper.DatabaseTestRunner;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.validation.*;
import java.lang.annotation.Annotation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * {@link CodeValueArrayValidator}のテストクラス。
 *
 * @author Naoki Yamamoto
 */
@RunWith(DatabaseTestRunner.class)
public class CodeValueArrayValidatorTest {

    @Rule
    public SystemRepositoryResource repositoryResource
            = new SystemRepositoryResource("nablarch/common/code/validator/code-value-validator-test.xml");

    private ConstraintValidatorContext unused = null;

    @BeforeClass
    public static void classSetUp() throws Exception {
        nablarch.common.code.validator.CodeValueValidatorTest.classSetup();
    }

    private static class SampleBean {
        @CodeValue(codeId = "0001")
        String[] code;

        @CodeValue(codeId = "0002", pattern = "PATTERN1")
        String[] codeWithPattern01;
    }

    private Annotation annotation(String fieldName) {
        try {
            return SampleBean.class.getDeclaredField(fieldName).getAnnotation(CodeValue.class);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testNullValue() {
        ConstraintValidator sut = new CodeValue.CodeValueArrayValidator();
        sut.initialize(annotation("code"));
        assertThat(sut.isValid(null, unused), is(true));
    }
    
    @Test
    public void testEmptyValue() {
        ConstraintValidator sut = new CodeValue.CodeValueArrayValidator();
        sut.initialize(annotation("code"));
        assertThat(sut.isValid(new String[0], unused), is(true));
        assertThat(sut.isValid(new String[] {""}, unused), is(true));
        assertThat(sut.isValid(new String[] {"","",""}, unused), is(true));
    }
    
    @Test
    public void testValidCode() {
        ConstraintValidator sut = new CodeValue.CodeValueArrayValidator();
        sut.initialize(annotation("code"));
        assertThat(sut.isValid(new String[] {"01"}, unused), is(true));
        assertThat(sut.isValid(new String[] {"", "02"}, unused), is(true));
        assertThat(sut.isValid(new String[] {null, "01"}, unused), is(true));
        assertThat(sut.isValid(new String[] {"01", "02"}, unused), is(true));
    }

    @Test
    public void testInvalidCode() {
        ConstraintValidator sut = new CodeValue.CodeValueArrayValidator();
        sut.initialize(annotation("code"));
        assertThat(sut.isValid(new String[] {"INVALID"}, unused), is(false));
        assertThat(sut.isValid(new String[] {"", "INVALID"}, unused), is(false));
        assertThat(sut.isValid(new String[] {"", "INVALID", "01"}, unused), is(false));
    }

    @Test
    public void testValidCodeWithPattern() {
        ConstraintValidator sut = new CodeValue.CodeValueArrayValidator();
        sut.initialize(annotation("codeWithPattern01"));
        assertThat(sut.isValid(new String[] {"01"}, unused), is(true));
        assertThat(sut.isValid(new String[] {"01", ""}, unused), is(true));
        assertThat(sut.isValid(new String[] {null, "", "01", "02"}, unused), is(true));
    }

    @Test
    public void testInValidCodeWithPattern() {
        ConstraintValidator sut = new CodeValue.CodeValueArrayValidator();
        sut.initialize(annotation("codeWithPattern01"));
        assertThat(sut.isValid(new String[] {"03"}, unused), is(false));
        assertThat(sut.isValid(new String[] {"03", ""}, unused), is(false));
        assertThat(sut.isValid(new String[] {"02", "", "03"}, unused), is(false));
    }
}