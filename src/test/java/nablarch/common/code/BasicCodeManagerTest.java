package nablarch.common.code;


import java.util.List;
import java.util.Locale;

import nablarch.core.cache.BasicStaticDataCache;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import nablarch.core.ThreadContext;
import nablarch.test.support.SystemRepositoryResource;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class BasicCodeManagerTest {

    @Rule
    public SystemRepositoryResource repositoryResource = new SystemRepositoryResource("nablarch/common/code/basic-code-manager-test.xml");

    /** test target */
    private BasicCodeManager target;

    private static List<CodePattern> patternList;
    private static List<CodeName> nameList;

    @BeforeClass
    public static void setUpClass() throws Exception {
        patternList = TestCodeCreator.createPatternList();
        nameList = TestCodeCreator.createNameList();
    }

    @Before
    public void setUp() throws Exception {
        target = repositoryResource.getComponentByType(BasicCodeManager.class);
        MockCodeLoader codeLoader = repositoryResource.getComponent("codeLoader");
        codeLoader.setPatterns(patternList);
        codeLoader.setNames(nameList);
        codeLoader.initialize();
    }

    /**
     * スレッドコンテキストに言語が設定されていない場合、
     * VMのデフォルトロケールの言語が使用されること。
     */
    @Test
    public void testDefaultLocale() {
        ThreadContext.setLanguage(null);
        assertEquals("男性", target.getName("0001", "01"));
        assertEquals("処理実行中", target.getName("0002", "03"));
    }

    @Test
    public void testGetNameStringString() {
        ThreadContext.setLanguage(Locale.ENGLISH);
        assertEquals("Male", target.getName("0001", "01"));
        assertEquals("Batch Running", target.getName("0002", "03"));

        ThreadContext.setLanguage(Locale.JAPANESE);
        assertEquals("男性", target.getName("0001", "01"));
        assertEquals("処理実行中", target.getName("0002", "03"));


        // 存在しないコードID
        try {
            target.getName("0003", "01");
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }

        // 存在しないコード値
        try {
            target.getName("0001", "03");
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
        // 存在しない言語
        try {
            ThreadContext.setLanguage(Locale.CHINESE);
            target.getName("0001", "03");
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testGetNameStringStringLocale() {
        assertEquals("Male", target.getName("0001", "01", Locale.ENGLISH));
        assertEquals("Batch Running", target.getName("0002", "03", Locale.ENGLISH));

        assertEquals("男性", target.getName("0001", "01", Locale.JAPANESE));
        assertEquals("処理実行中", target.getName("0002", "03", Locale.JAPANESE));


        // 存在しないコードID
        try {
            target.getName("0003", "01", Locale.JAPANESE);
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
        // 存在しないコード値
        try {
            target.getName("0001", "03", Locale.JAPANESE);
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
        // 存在しない言語
        try {
            target.getName("0001", "01", Locale.CHINESE);
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testGetShortNameStringString() {
        ThreadContext.setLanguage(Locale.ENGLISH);
        assertEquals("M", target.getShortName("0001", "01"));
        assertEquals("Running", target.getShortName("0002", "03"));

        ThreadContext.setLanguage(Locale.JAPANESE);
        assertEquals("男", target.getShortName("0001", "01"));
        assertEquals("実行", target.getShortName("0002", "03"));


        // 存在しないコードID
        try {
            target.getShortName("0003", "01");
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
        // 存在しないコード値
        try {
            target.getShortName("0001", "03");
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
        // 存在しない言語
        ThreadContext.setLanguage(Locale.CHINESE);
        try {
            target.getShortName("0001", "03");
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testGetShortNameStringStringLocale() {
        assertEquals("M", target.getShortName("0001", "01", Locale.ENGLISH));
        assertEquals("Running", target.getShortName("0002", "03", Locale.ENGLISH));

        assertEquals("男", target.getShortName("0001", "01", Locale.JAPANESE));
        assertEquals("実行", target.getShortName("0002", "03", Locale.JAPANESE));

        // 存在しないコードID
        try {
            target.getShortName("0003", "01", Locale.JAPANESE);

            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
        // 存在しないコード値
        try {
            target.getShortName("0001", "03", Locale.JAPANESE);
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
        // 存在しない言語
        try {
            target.getShortName("0001", "01", Locale.CHINESE);
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testGetOptionalNameStringStringString() {
        ThreadContext.setLanguage(Locale.ENGLISH);
        assertEquals("0001-01-en", target.getOptionalName("0001", "01", "OPTION01"));
        assertEquals("0002-03-en", target.getOptionalName("0002", "03", "OPTION01"));

        ThreadContext.setLanguage(Locale.JAPANESE);
        assertEquals("0001-01-ja", target.getOptionalName("0001", "01", "OPTION01"));
        assertEquals("0002-03-ja", target.getOptionalName("0002", "03", "OPTION01"));


        // 存在しないコードID
        try {
            target.getOptionalName("0003", "01", "OPTION01");
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
        // 存在しないコード値
        try {
            target.getOptionalName("0001", "03", "OPTION01");
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
        // 存在しない言語
        ThreadContext.setLanguage(Locale.CHINESE);
        try {
            target.getOptionalName("0001", "01", "OPTION01");
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testGetOptionalNameStringStringStringLocale() {
        assertEquals("0001-01-en", target.getOptionalName("0001", "01", "OPTION01", Locale.ENGLISH));
        assertEquals("0002-03-en", target.getOptionalName("0002", "03", "OPTION01", Locale.ENGLISH));

        assertEquals("0001-01-ja", target.getOptionalName("0001", "01", "OPTION01", Locale.JAPANESE));
        assertEquals("0002-03-ja", target.getOptionalName("0002", "03", "OPTION01", Locale.JAPANESE));


        // 存在しないコードID
        try {
            target.getOptionalName("0003", "01",  "OPTION01", Locale.JAPANESE);
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
        // 存在しないコード値
        try {
            target.getOptionalName("0001", "03",  "OPTION01", Locale.JAPANESE);
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
        // 存在しない言語
        try {
            target.getOptionalName("0001", "01",  "OPTION01", Locale.CHINESE);
        fail("例外が発生するはず。");
    } catch (IllegalArgumentException e) {
        // OK
    }

    }

    @Test
    public void testGetValuesString() {
        ThreadContext.setLanguage(Locale.ENGLISH);
        assertArrayEquals(new String[]{"02", "01"}, target.getValues("0001").toArray());
        assertArrayEquals(new String[]{"01", "02", "03", "04", "05"}, target.getValues("0002").toArray());

        ThreadContext.setLanguage(Locale.JAPANESE);
        assertArrayEquals(new String[]{"01", "02"}, target.getValues("0001").toArray());
        assertArrayEquals(new String[]{"01", "02", "03", "04", "05"}, target.getValues("0002").toArray());


        // 存在しないコードID
        try {
            target.getValues("0003");
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
        // 存在しない言語
        ThreadContext.setLanguage(Locale.CHINESE);
        try {
            target.getValues("0001");
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testGetValuesStringString() {
        ThreadContext.setLanguage(Locale.ENGLISH);
        assertArrayEquals(new String[]{"02", "01"}, target.getValues("0001", "PATTERN1").toArray());
        assertArrayEquals(new String[]{}, target.getValues("0001", "PATTERN2").toArray());
        assertArrayEquals(new String[]{}, target.getValues("0001", "PATTERN3").toArray());
        assertArrayEquals(new String[]{"01", "02", "05"}, target.getValues("0002", "PATTERN1").toArray());
        assertArrayEquals(new String[]{"03", "04"}, target.getValues("0002", "PATTERN2").toArray());
        assertArrayEquals(new String[]{}, target.getValues("0002", "PATTERN3").toArray());

        ThreadContext.setLanguage(Locale.JAPANESE);
        assertArrayEquals(new String[]{"01", "02"}, target.getValues("0001", "PATTERN1").toArray());
        assertArrayEquals(new String[]{}, target.getValues("0001", "PATTERN2").toArray());
        assertArrayEquals(new String[]{}, target.getValues("0001", "PATTERN3").toArray());
        assertArrayEquals(new String[]{"01", "02", "05"}, target.getValues("0002", "PATTERN1").toArray());
        assertArrayEquals(new String[]{"03", "04"}, target.getValues("0002", "PATTERN2").toArray());
        assertArrayEquals(new String[]{}, target.getValues("0002", "PATTERN3").toArray());


        // 存在しないコードID
        try {
            target.getValues("0003", "PATTERN3");
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
        // 存在しないパターン
        try {
            target.getValues("0002", "PATTERN4");
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
        // 存在しない言語
        ThreadContext.setLanguage(Locale.CHINESE);
        try {
            target.getValues("0002", "PATTERN3");
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }
    @Test
    public void testGetValuesStringLocale() {
        assertArrayEquals(new String[]{"02", "01"}, target.getValues("0001", Locale.ENGLISH).toArray());
        assertArrayEquals(new String[]{"01", "02", "03", "04", "05"}, target.getValues("0002", Locale.ENGLISH).toArray());

        assertArrayEquals(new String[]{"01", "02"}, target.getValues("0001", Locale.JAPANESE).toArray());
        assertArrayEquals(new String[]{"01", "02", "03", "04", "05"}, target.getValues("0002", Locale.JAPANESE).toArray());


        // 存在しないコードID
        try {
            target.getValues("0003", Locale.ENGLISH);
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testGetValuesStringStringLocale() {
        assertArrayEquals(new String[]{"02", "01"}, target.getValues("0001", "PATTERN1", Locale.ENGLISH).toArray());
        assertArrayEquals(new String[]{}, target.getValues("0001", "PATTERN2", Locale.ENGLISH).toArray());
        assertArrayEquals(new String[]{}, target.getValues("0001", "PATTERN3", Locale.ENGLISH).toArray());
        assertArrayEquals(new String[]{"01", "02", "05"}, target.getValues("0002", "PATTERN1", Locale.ENGLISH).toArray());
        assertArrayEquals(new String[]{"03", "04"}, target.getValues("0002", "PATTERN2", Locale.ENGLISH).toArray());
        assertArrayEquals(new String[]{}, target.getValues("0002", "PATTERN3", Locale.ENGLISH).toArray());

        assertArrayEquals(new String[]{"01", "02"}, target.getValues("0001", "PATTERN1", Locale.JAPANESE).toArray());
        assertArrayEquals(new String[]{}, target.getValues("0001", "PATTERN2", Locale.JAPANESE).toArray());
        assertArrayEquals(new String[]{}, target.getValues("0001", "PATTERN3", Locale.JAPANESE).toArray());
        assertArrayEquals(new String[]{"01", "02", "05"}, target.getValues("0002", "PATTERN1", Locale.JAPANESE).toArray());
        assertArrayEquals(new String[]{"03", "04"}, target.getValues("0002", "PATTERN2", Locale.JAPANESE).toArray());
        assertArrayEquals(new String[]{}, target.getValues("0002", "PATTERN3", Locale.JAPANESE).toArray());


        // 存在しないコードID
        try {
            target.getValues("0003", "PATTERN1", Locale.ENGLISH);
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
        // 存在しないパターン
        try {
            target.getValues("0002", "PATTERN4", Locale.ENGLISH);
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testContainsStringString() {
        assertTrue(target.contains("0001", "01"));
        assertTrue(target.contains("0001", "02"));

        assertTrue(target.contains("0002", "01"));
        assertTrue(target.contains("0002", "02"));
        assertTrue(target.contains("0002", "03"));
        assertTrue(target.contains("0002", "04"));
        assertTrue(target.contains("0002", "05"));


        assertFalse(target.contains("0001", "00"));
        assertFalse(target.contains("0001", "03"));

        assertFalse(target.contains("0002", "00"));
        assertFalse(target.contains("0002", "06"));


        // 存在しないコードID
        try {
            target.contains("0003", "01");
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testContainsStringStringString() {
        assertTrue(target.contains("0002", "PATTERN1","01"));
        assertTrue(target.contains("0002", "PATTERN1","02"));
        assertTrue(target.contains("0002", "PATTERN1","05"));

        assertTrue(target.contains("0002", "PATTERN2","03"));
        assertTrue(target.contains("0002", "PATTERN2","04"));


        assertFalse(target.contains("0002", "PATTERN1","03"));
        assertFalse(target.contains("0002", "PATTERN1","04"));

        assertFalse(target.contains("0002", "PATTERN2","01"));
        assertFalse(target.contains("0002", "PATTERN2","02"));
        assertFalse(target.contains("0002", "PATTERN2","05"));


        // 存在しないコードID
        try {
            target.contains("0003", "PATTERN1", "01");
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }
        // 存在しないパターン
        try {
            target.contains("0002", "PATTERN4", "01");
            fail("例外が発生するはず。");
        } catch (IllegalArgumentException e) {
            // OK
        }

    }

    @Test
    public void testInitialLoad() {

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
