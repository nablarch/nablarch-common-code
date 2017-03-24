package nablarch.common.code.validator.ee;

import java.lang.annotation.Annotation;

import javax.validation.*;

import nablarch.common.code.MockCodeLoader;
import nablarch.common.code.TestCodeCreator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import nablarch.test.support.SystemRepositoryResource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * {@link CodeValueValidatorTest}のテストクラス。
 *
 * @author T.Kawasaki
 */
public class CodeValueValidatorTest {

    @Rule
    public SystemRepositoryResource repositoryResource
            = new SystemRepositoryResource("nablarch/common/code/validator/code-value-validator-test.xml");

    private ConstraintValidatorContext unused = null;

    @Before
    public void setUp() throws Exception {
        MockCodeLoader codeLoader = repositoryResource.getComponent("codeLoader");
        codeLoader.setPatterns(TestCodeCreator.createPatternList());
        codeLoader.setNames(TestCodeCreator.createNameList());
        codeLoader.initialize();
    }

    private interface NormalUser {}
    private interface PremiumUser {}

    private static class SampleBean {
        @CodeValue(codeId = "0001")
        String code;

        @CodeValue(codeId = "0002", pattern = "PATTERN1")
        String codeWithPattern01;

        @CodeValue.List({
                @CodeValue(codeId = "normal", groups = NormalUser.class),
                @CodeValue(codeId = "premium", groups = PremiumUser.class)
        })
        String list;
    }


    private Annotation annotation(String fieldName, Class annotationClass) {
        try {
            return SampleBean.class.getDeclaredField(fieldName).getAnnotation(annotationClass);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private Annotation annotation(String fieldName) {
        return annotation(fieldName, CodeValue.class);
    }

    @Test
    public void testNullValue() throws NoSuchFieldException {
        ConstraintValidator sut = new CodeValue.CodeValueValidator();
        sut.initialize(annotation("code"));
        assertThat(sut.isValid(null, unused), is(true));
    }

    @Test
    public void testEmptyValue() {
        ConstraintValidator sut = new CodeValue.CodeValueValidator();
        sut.initialize(annotation("code"));
        assertThat(sut.isValid("", unused), is(true));
    }

    @Test
    public void testValidCode() {
        ConstraintValidator sut = new CodeValue.CodeValueValidator();
        sut.initialize(annotation("code"));
        assertThat(sut.isValid("01", unused), is(true));
    }

    @Test
    public void testInvalidCode() {
        ConstraintValidator sut = new CodeValue.CodeValueValidator();
        sut.initialize(annotation("code"));
        assertThat(sut.isValid("INVALID", unused), is(false));
    }

    @Test
    public void testValidCodeWithPattern() {
        ConstraintValidator sut = new CodeValue.CodeValueValidator();
        sut.initialize(annotation("codeWithPattern01"));
        assertThat(sut.isValid("01", unused), is(true));
    }

    @Test
    public void testInValidCodeWithPattern() {
        ConstraintValidator sut = new CodeValue.CodeValueValidator();
        sut.initialize(annotation("codeWithPattern01"));
        assertThat(sut.isValid("03", unused), is(false));
    }

    @Test
    public void testListAndGroups() throws Exception {
        // JDK7依存してしまうのでBeanValidatorを実行することができないので静的にテストする。
        CodeValue.List annotation = (CodeValue.List) annotation("list", CodeValue.List.class);
        assertThat(annotation.value()[0].groups()[0].getName(), is(NormalUser.class.getName()));
        assertThat(annotation.value()[1].groups()[0].getName(), is(PremiumUser.class.getName()));
    }
}