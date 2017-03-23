package nablarch.common.code;

import nablarch.core.ThreadContext;
import nablarch.core.cache.BasicStaticDataCache;
import nablarch.core.repository.SystemRepository;
import nablarch.test.support.SystemRepositoryResource;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

public class CodeUtilTest {

    @Rule
    public SystemRepositoryResource repositoryResource = new SystemRepositoryResource("nablarch/common/code/basic-code-manager-test.xml");

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static List<CodePattern> patternList = new ArrayList<CodePattern>();
    private static List<CodeName> nameList = new ArrayList<CodeName>();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        patternList = TestCodeCreator.createPatternList();
        nameList = TestCodeCreator.createNameList();
    }

    @Before
    public void setUp() throws Exception {
        MockCodeLoader codeLoader = repositoryResource.getComponent("codeLoader");
        codeLoader.setPatterns(patternList);
        codeLoader.setNames(nameList);
        codeLoader.initialize();
    }

    @Test
    public void testGetNameStringString() {
        ThreadContext.setLanguage(Locale.ENGLISH);
        assertEquals("Male", CodeUtil.getName("0001", "01"));
        assertEquals("Batch Running", CodeUtil.getName("0002", "03"));

        ThreadContext.setLanguage(Locale.JAPANESE);
        assertEquals("男性", CodeUtil.getName("0001", "01"));
        assertEquals("処理実行中", CodeUtil.getName("0002", "03"));
    }

    /**
     * {@link CodeUtil#getName(String, String)}のテスト。
     * <p/>
     * リポジトリに値が無い場合、例外を送出するかどうか。
     * @throws Exception
     */
    @Test
    public void testGetNameStringStringErr() throws Exception{
        SystemRepository.clear();
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("specified codeManager is not registered in SystemRepository.");

        CodeUtil.getName("0001", "01");
    }

    @Test
    public void testGetNameStringStringLocale() {
        assertEquals("Male", CodeUtil.getName("0001", "01", Locale.ENGLISH));
        assertEquals("Batch Running", CodeUtil.getName("0002", "03", Locale.ENGLISH));

        assertEquals("男性", CodeUtil.getName("0001", "01", Locale.JAPANESE));
        assertEquals("処理実行中", CodeUtil.getName("0002", "03", Locale.JAPANESE));
    }

    @Test
    public void testGetShortNameStringString() {
        ThreadContext.setLanguage(Locale.ENGLISH);
        assertEquals("M", CodeUtil.getShortName("0001", "01"));
        assertEquals("Running", CodeUtil.getShortName("0002", "03"));

        ThreadContext.setLanguage(Locale.JAPANESE);
        assertEquals("男", CodeUtil.getShortName("0001", "01"));
        assertEquals("実行", CodeUtil.getShortName("0002", "03"));
    }

    @Test
    public void testGetShortNameStringStringLocale() {
        assertEquals("M", CodeUtil.getShortName("0001", "01", Locale.ENGLISH));
        assertEquals("Running", CodeUtil.getShortName("0002", "03", Locale.ENGLISH));

        assertEquals("男", CodeUtil.getShortName("0001", "01", Locale.JAPANESE));
        assertEquals("実行", CodeUtil.getShortName("0002", "03", Locale.JAPANESE));
    }

    @Test
    public void testGetOptionalNameStringStringString() {
        ThreadContext.setLanguage(Locale.ENGLISH);
        assertEquals("0001-01-en", CodeUtil.getOptionalName("0001", "01", "OPTION01"));
        assertEquals("0002-03-en", CodeUtil.getOptionalName("0002", "03", "OPTION01"));

        ThreadContext.setLanguage(Locale.JAPANESE);
        assertEquals("0001-01-ja", CodeUtil.getOptionalName("0001", "01", "OPTION01"));
        assertEquals("0002-03-ja", CodeUtil.getOptionalName("0002", "03", "OPTION01"));
    }

    @Test
    public void testGetOptionalNameStringStringStringLocale() {
        ThreadContext.setLanguage(Locale.ENGLISH);
        assertEquals("0001-01-en", CodeUtil.getOptionalName("0001", "01", "OPTION01", Locale.ENGLISH));
        assertEquals("0002-03-en", CodeUtil.getOptionalName("0002", "03", "OPTION01", Locale.ENGLISH));

        ThreadContext.setLanguage(Locale.JAPANESE);
        assertEquals("0001-01-ja", CodeUtil.getOptionalName("0001", "01", "OPTION01", Locale.JAPANESE));
        assertEquals("0002-03-ja", CodeUtil.getOptionalName("0002", "03", "OPTION01", Locale.JAPANESE));
    }

    @Test
    public void testGetValuesString() {
        ThreadContext.setLanguage(Locale.ENGLISH);
        assertArrayEquals(new String[]{"02", "01"}, CodeUtil.getValues("0001").toArray());
        assertArrayEquals(new String[]{"01", "02", "03", "04", "05"}, CodeUtil.getValues("0002").toArray());

        ThreadContext.setLanguage(Locale.JAPANESE);
        assertArrayEquals(new String[]{"01", "02"}, CodeUtil.getValues("0001").toArray());
        assertArrayEquals(new String[]{"01", "02", "03", "04", "05"}, CodeUtil.getValues("0002").toArray());
    }

    @Test
    public void testGetValuesStringString() {
        ThreadContext.setLanguage(Locale.ENGLISH);
        assertArrayEquals(new String[]{"02", "01"}, CodeUtil.getValues("0001", "PATTERN1").toArray());
        assertArrayEquals(new String[]{}, CodeUtil.getValues("0001", "PATTERN2").toArray());
        assertArrayEquals(new String[]{}, CodeUtil.getValues("0001", "PATTERN3").toArray());
        assertArrayEquals(new String[]{"01", "02", "05"}, CodeUtil.getValues("0002", "PATTERN1").toArray());
        assertArrayEquals(new String[]{"03", "04"}, CodeUtil.getValues("0002", "PATTERN2").toArray());
        assertArrayEquals(new String[]{}, CodeUtil.getValues("0002", "PATTERN3").toArray());

        ThreadContext.setLanguage(Locale.JAPANESE);
        assertArrayEquals(new String[]{"01", "02"}, CodeUtil.getValues("0001", "PATTERN1").toArray());
        assertArrayEquals(new String[]{}, CodeUtil.getValues("0001", "PATTERN2").toArray());
        assertArrayEquals(new String[]{}, CodeUtil.getValues("0001", "PATTERN3").toArray());
        assertArrayEquals(new String[]{"01", "02", "05"}, CodeUtil.getValues("0002", "PATTERN1").toArray());
        assertArrayEquals(new String[]{"03", "04"}, CodeUtil.getValues("0002", "PATTERN2").toArray());
        assertArrayEquals(new String[]{}, CodeUtil.getValues("0002", "PATTERN3").toArray());
    }
    @Test
    public void testGetValuesStringLocale() {
        assertArrayEquals(new String[]{"02", "01"}, CodeUtil.getValues("0001", Locale.ENGLISH).toArray());
        assertArrayEquals(new String[]{"01", "02", "03", "04", "05"}, CodeUtil.getValues("0002", Locale.ENGLISH).toArray());

        assertArrayEquals(new String[]{"01", "02"}, CodeUtil.getValues("0001", Locale.JAPANESE).toArray());
        assertArrayEquals(new String[]{"01", "02", "03", "04", "05"}, CodeUtil.getValues("0002", Locale.JAPANESE).toArray());
    }

    @Test
    public void testGetValuesStringStringLocale() {
        assertArrayEquals(new String[]{"02", "01"}, CodeUtil.getValues("0001", "PATTERN1", Locale.ENGLISH).toArray());
        assertArrayEquals(new String[]{}, CodeUtil.getValues("0001", "PATTERN2", Locale.ENGLISH).toArray());
        assertArrayEquals(new String[]{}, CodeUtil.getValues("0001", "PATTERN3", Locale.ENGLISH).toArray());
        assertArrayEquals(new String[]{"01", "02", "05"}, CodeUtil.getValues("0002", "PATTERN1", Locale.ENGLISH).toArray());
        assertArrayEquals(new String[]{"03", "04"}, CodeUtil.getValues("0002", "PATTERN2", Locale.ENGLISH).toArray());
        assertArrayEquals(new String[]{}, CodeUtil.getValues("0002", "PATTERN3", Locale.ENGLISH).toArray());

        assertArrayEquals(new String[]{"01", "02"}, CodeUtil.getValues("0001", "PATTERN1", Locale.JAPANESE).toArray());
        assertArrayEquals(new String[]{}, CodeUtil.getValues("0001", "PATTERN2", Locale.JAPANESE).toArray());
        assertArrayEquals(new String[]{}, CodeUtil.getValues("0001", "PATTERN3", Locale.JAPANESE).toArray());
        assertArrayEquals(new String[]{"01", "02", "05"}, CodeUtil.getValues("0002", "PATTERN1", Locale.JAPANESE).toArray());
        assertArrayEquals(new String[]{"03", "04"}, CodeUtil.getValues("0002", "PATTERN2", Locale.JAPANESE).toArray());
        assertArrayEquals(new String[]{}, CodeUtil.getValues("0002", "PATTERN3", Locale.JAPANESE).toArray());
    }

    @Test
    public void testContainsStringString() {
        assertTrue(CodeUtil.contains("0001", "01"));
        assertTrue(CodeUtil.contains("0001", "02"));

        assertTrue(CodeUtil.contains("0002", "01"));
        assertTrue(CodeUtil.contains("0002", "02"));
        assertTrue(CodeUtil.contains("0002", "03"));
        assertTrue(CodeUtil.contains("0002", "04"));
        assertTrue(CodeUtil.contains("0002", "05"));


        assertFalse(CodeUtil.contains("0001", "00"));
        assertFalse(CodeUtil.contains("0001", "03"));

        assertFalse(CodeUtil.contains("0002", "00"));
        assertFalse(CodeUtil.contains("0002", "06"));
    }

    @Test
    public void testContainsStringStringString() {
        assertTrue(CodeUtil.contains("0002", "PATTERN1", "01"));
        assertTrue(CodeUtil.contains("0002", "PATTERN1", "02"));
        assertTrue(CodeUtil.contains("0002", "PATTERN1", "05"));

        assertTrue(CodeUtil.contains("0002", "PATTERN2", "03"));
        assertTrue(CodeUtil.contains("0002", "PATTERN2", "04"));

        assertFalse(CodeUtil.contains("0002", "PATTERN1", "03"));
        assertFalse(CodeUtil.contains("0002", "PATTERN1", "04"));

        assertFalse(CodeUtil.contains("0002", "PATTERN2", "01"));
        assertFalse(CodeUtil.contains("0002", "PATTERN2", "02"));
        assertFalse(CodeUtil.contains("0002", "PATTERN2", "05"));
    }

    @Test
    public void getInitialLoad() {
    	BasicStaticDataCache<MockCodeLoader> codeCache = repositoryResource.getComponent("codeCache");
    	codeCache.setLoadOnStartup(true);
    	codeCache.initialize();

        testGetNameStringString();
        testGetNameStringStringLocale();
        testGetShortNameStringString();
        testGetShortNameStringStringLocale();
        testGetOptionalNameStringStringString();
        testGetOptionalNameStringStringStringLocale();
        testGetValuesString();
        testGetValuesStringLocale();
        testGetValuesStringString();
        testGetValuesStringStringLocale();
        testContainsStringString();
        testContainsStringStringString();
    }

}
