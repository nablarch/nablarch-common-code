package nablarch.common.code;

import java.util.ArrayList;
import java.util.List;

/**
 * テスト用のコードを作成するクラス。
 */
public final class TestCodeCreator {
    public static List<CodePattern> createPatternList() {
        List<CodePattern> patternList = new ArrayList<CodePattern>();
        patternList.add(new CodePattern("0001", "01", "1", "0", "0"));
        patternList.add(new CodePattern("0001", "02", "1", "0", "0"));
        patternList.add(new CodePattern("0002", "01", "1", "0", "0"));
        patternList.add(new CodePattern("0002", "02", "1", "0", "0"));
        patternList.add(new CodePattern("0002", "03", "0", "1", "0"));
        patternList.add(new CodePattern("0002", "04", "0", "1", "0"));
        patternList.add(new CodePattern("0002", "05", "1", "0", "0"));
        return patternList;
    }

    public static List<CodeName> createNameList() {
        List<CodeName> nameList = new ArrayList<CodeName>();
        nameList.add(new CodeName("0001", "01", "en", 2L, "Male", "M", "01:Male", "0001-01-en"));
        nameList.add(new CodeName("0001", "02", "en", 1L, "Female", "F", "02:Female", "0001-02-en"));
        nameList.add(new CodeName("0002", "01", "en", 1L, "Initial State", "Initial", "", "0002-01-en"));
        nameList.add(new CodeName("0002", "02", "en", 2L, "Waiting For Batch Start", "Waiting", "", "0002-02-en"));
        nameList.add(new CodeName("0002", "03", "en", 3L, "Batch Running", "Running", "", "0002-03-en"));
        nameList.add(new CodeName("0002", "04", "en", 4L, "Batch Execute Completed Checked", "Completed", "", "0002-04-en"));
        nameList.add(new CodeName("0002", "05", "en", 5L, "Batch Result Checked", "Checked", "", "0002-05-en"));
        nameList.add(new CodeName("0001", "01", "ja", 1L, "男性", "男", "01:Male", "0001-01-ja"));
        nameList.add(new CodeName("0001", "02", "ja", 2L, "女性", "女", "02:Female", "0001-02-ja"));
        nameList.add(new CodeName("0002", "01", "ja", 1L, "初期状態", "初期", "", "0002-01-ja"));
        nameList.add(new CodeName("0002", "02", "ja", 2L, "処理開始待ち", "待ち", "", "0002-02-ja"));
        nameList.add(new CodeName("0002", "03", "ja", 3L, "処理実行中", "実行", "", "0002-03-ja"));
        nameList.add(new CodeName("0002", "04", "ja", 4L, "処理実行完了", "完了", "", "0002-04-ja"));
        nameList.add(new CodeName("0002", "05", "ja", 5L, "処理結果確認完了", "確認", "", "0002-05-ja"));
        return nameList;
    }
}
