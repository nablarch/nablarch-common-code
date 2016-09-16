package nablarch.common.code;

import nablarch.core.repository.SystemRepository;
import nablarch.core.util.annotation.Published;

import java.util.List;
import java.util.Locale;

/**
 * コードの値、及び名称の取り扱いのために使用するユーティリティ。
 * </p>
 * コードの値、及び名称の取得処理は{@link CodeManager}によって提供される。
 * {@link CodeManager}の実装は、SystemRepositoryからコンポーネント名{@value #CODE_MANGER_NAME}で取得される。
 * </p>
 *
 *
 * @see CodeManager
 * @author Koichi Asano
 */
@Published
public final class CodeUtil {


    /**
     * メッセージリソースのコンポーネント名。
     */
    private static final String CODE_MANGER_NAME = "codeManager";
    /**
     * 隠蔽コンストラクタ。
     */
    private CodeUtil() {
        
    }

    /**
     * コード値に対応するコード名称を取得する。
     * <p/>
     * 対象の言語は{@link nablarch.core.ThreadContext}で設定された言語となる。
     * {@link nablarch.core.ThreadContext}で設定が行われていない場合は、デフォルトロケールの言語となる。
     * <p/>
     *
     * @param codeId コードID
     * @param value コード値
     * @return コード値に対応するコード名称
     * @throws IllegalArgumentException 指定したコードIDが存在しないか、対象のコード値または言語に対応するデータが存在しない場合
     */
    public static String getName(String codeId, String value) throws IllegalArgumentException {
        return getCodeManager().getName(codeId, value);
    }

    /**
     * コード値、言語に対応するコード名称を取得する。
     *
     * @param codeId コードID
     * @param value コード値
     * @param locale 言語
     * @return コード値に対応するコード名称
     * @throws IllegalArgumentException 指定したコードIDが存在しないか、対象のコード値または言語に対応するデータが存在しない場合
     */
    public static String getName(String codeId, String value, Locale locale) throws IllegalArgumentException {
        return getCodeManager().getName(codeId, value, locale);
    }

    /**
     * コード値に対応するコードの略称を取得する。
     * <p/>
     * 対象の言語は{@link nablarch.core.ThreadContext}にて設定された言語となる。
     * {@link nablarch.core.ThreadContext}で設定が行われていない場合は、デフォルトロケールの言語となる。
     * <p/>
     *
     * @param codeId コードID
     * @param value コード値
     * @return コード値に対応するコードの略称
     * @throws IllegalArgumentException 指定したコードIDが存在しないか、対象のコード値または言語に対応するデータが存在しない場合
     */
    public static String getShortName(String codeId, String value) throws IllegalArgumentException {
        return getCodeManager().getShortName(codeId, value);
    }

    /**
     * コード値、言語に対応するコードの略称を取得する。
     * 
     * @param codeId コードID
     * @param value コード値
     * @param locale 言語
     * @return コード値に対応するコードの略称
     * @throws IllegalArgumentException 指定したコードIDが存在しないか、対象のコード値または言語に対応するデータが存在しない場合
     */
    public static String getShortName(String codeId, String value, Locale locale) throws IllegalArgumentException {
        return getCodeManager().getShortName(codeId, value, locale);
    }

    /**
     * コード値に対応するコードのオプション名称(名称、略称の他に使用する補助名称)を取得する。
     * <p/>
     * 対象の言語は{@link nablarch.core.ThreadContext}にて設定された言語となる。
     * {@link nablarch.core.ThreadContext}で設定が行われていない場合は、デフォルトロケールの言語となる。
     *
     * @param codeId コードID
     * @param value コード値
     * @param optionColumnName 取得するオプション名称のカラム名（大文字・小文字を区別せずに使用する）
     * @return コード値に対応するコードのオプション名称
     * @throws IllegalArgumentException 指定したコードIDが存在しないか、対象のコード値または言語に対応するデータが存在しない場合
     */
    public static String getOptionalName(String codeId, String value, String optionColumnName) throws IllegalArgumentException {
        return getCodeManager().getOptionalName(codeId, value, optionColumnName);
    }

    /**
     * コード値、言語に対応するコードのオプション名称を取得する。
     * <p/>
     * 対象の言語は引数の言語によって決定される。
     * 
     * @param codeId コードID
     * @param value コード値
     * @param optionColumnName 取得するオプション名称のカラム名（大文字・小文字を区別せずに使用する）
     * @param locale 言語
     * @return コード値に対応するコードのオプション名称
     * @throws IllegalArgumentException 指定したコードIDが存在しないか、対象のコード値または言語に対応するデータが存在しない場合
     */
    public static String getOptionalName(String codeId, String value, String optionColumnName, Locale locale) throws IllegalArgumentException {
        return getCodeManager().getOptionalName(codeId, value, optionColumnName, locale);
    }

    /**
     * コードIDに紐付く全てのコード値を取得する。
     * </p>
     * コード値の順序は、コード名称テーブルのソート順カラムに、言語ごとに設定された値で決定される。
     * 返却値は、言語ごとに定義されたソート順に従い、並び替えが行われる。
     *
     * @param codeId コードID
     * @return コードIDに紐付く全てのコード値
     * @throws IllegalArgumentException 指定したコードIDが存在しないか、対象のコード値または言語に対応するデータが存在しない場合
     */
    public static List<String> getValues(String codeId) throws IllegalArgumentException {
        return getCodeManager().getValues(codeId);
    }

    /**
     * コードIDとパターンに紐付くコード値を取得する。
     * </p>
     * コード値の順序は、コード名称テーブルのソート順カラムに、言語ごとに設定された値で決定される。
     * 返却値は、言語ごとに定義されたソート順に従い、並び替えが行われる。
     * 対象の言語は{@link nablarch.core.ThreadContext}にて設定された言語となる。
     * {@link nablarch.core.ThreadContext}で設定が行われていない場合は、デフォルトロケールの言語となる。
     *
     * @param codeId コードID
     * @param pattern 使用するパターンのカラム名（大文字・小文字を区別せずに使用する）
     * @return コードIDとパターンに紐付くコード値
     * @throws IllegalArgumentException 指定したコードIDが存在しないか、パターンまたは言語に対応するデータが存在しない場合
     */
    public static List<String> getValues(String codeId, String pattern) throws IllegalArgumentException {
        return getCodeManager().getValues(codeId, pattern);
    }

    /**
     * コードIDに紐付く全てのコード値を取得する。
     * </p>
     * コード値の順序は、コード名称テーブルのソート順カラムに、言語ごとに設定された値で決定される。
     * 返却値は、言語ごとに定義されたソート順に従い、並び替えが行われる。
     *
     * @param codeId コードID
     * @param locale 言語
     * @return コードIDに紐付く全てのコード値
     * @throws IllegalArgumentException 指定したコードIDが存在しないか、コード値または言語に対応するデータが存在しない場合
     */
    public static List<String> getValues(String codeId, Locale locale) throws IllegalArgumentException {
        return getCodeManager().getValues(codeId, locale);
    }

    /**
     * コードIDとパターンに紐付くコード値を取得する。
     * </p>
     * コード値の順序は、コード名称テーブルのソート順カラムに、言語ごとに設定された値で決定される。
     * 返却値は、言語ごとに定義されたソート順に従い、並び替えが行われる。
     * 
     * @param codeId コードID
     * @param pattern 使用するパターンのカラム名（大文字・小文字を区別せずに使用する）
     * @param locale 言語
     * @return コードIDとパターンに紐付くコード値
     * @throws IllegalArgumentException 指定したコードIDが存在しないか、パターンまたは言語に対応するデータが存在しない場合
     */
    public static List<String> getValues(String codeId, String pattern, Locale locale) throws IllegalArgumentException {
        return getCodeManager().getValues(codeId, pattern, locale);
    }

    /**
     * コード値がコードに存在するかチェックする。
     * 
     * @param codeId コードID
     * @param value コード値
     * @return コード値がコードに存在する場合 true
     * @throws IllegalArgumentException 指定したコードIDが存在しない場合
     */
    public static boolean contains(String codeId, String value) throws IllegalArgumentException {
        return getCodeManager().contains(codeId, value);
    }

    /**
     * コード値がパターンに存在するかチェックする。
     *
     * @param codeId コードID
     * @param pattern 使用するパターンのカラム名（大文字・小文字を区別せずに使用する）
     * @param value コード値
     * @return コード値がコードに存在する場合 true
     * @throws IllegalArgumentException 指定したコードIDが存在しない場合か、指定したパターンが存在しない場合
     */
    public static boolean contains(String codeId, String pattern, String value) throws IllegalArgumentException {
        return getCodeManager().contains(codeId, pattern, value);
    }

    /**
     * CodeManagerをリポジトリから取得する。
     * 
     * @return リポジトリから取得したCodeManager
     */
    private static CodeManager getCodeManager() {
        CodeManager manager = (CodeManager) SystemRepository.get(CODE_MANGER_NAME);
        if(manager == null){
            throw new IllegalArgumentException("specified " + CODE_MANGER_NAME + " is not registered in SystemRepository.");
        }
        return manager;
    }
}
