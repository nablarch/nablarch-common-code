package nablarch.common.code;

import java.util.List;
import java.util.Locale;

import nablarch.core.util.annotation.Published;

/**
 * コードの値と名称を取り扱うインタフェース。
 * 
 * @author Koichi Asano
 */
@Published(tag = "architect")
public interface CodeManager {

    /**
     * コードID、コード値を条件に、対応するコード名称を取得する。<br/>
     * 取得対象言語は、{@link nablarch.core.ThreadContext}でデフォルトとして指定された言語とする。
     * {@link nablarch.core.ThreadContext}からデフォルト言語を取得できなかった場合、
     * デフォルトロケールから言語を取得する。
     *
     * @param codeId コードID
     * @param value コード値
     * @return 対応するコード名称
     * @throws IllegalArgumentException 指定したコードIDが存在しないか、
     *                                   対象のコード値または言語に対応するデータが存在しない場合
     */
    String getName(String codeId, String value) throws IllegalArgumentException;

    /**
     * コードID、コード値、取得対象の言語を条件に、対応するコード名称を取得する。<br/>
     * 
     * @param codeId コードID
     * @param value コード値
     * @param locale 取得対象の言語
     * @return 対応するコード名称
     * @throws IllegalArgumentException 指定したコードIDが存在しないか、
     *                                   対象のコード値または言語に対応するデータが存在しない場合
     */
    String getName(String codeId, String value, Locale locale) throws IllegalArgumentException;

    /**
     * コードID、コード値を条件に、対応するコードの略称を取得する。<br/>
     * 取得対象言語は、{@link nablarch.core.ThreadContext}でデフォルトとして指定された言語とする。
     * {@link nablarch.core.ThreadContext}からデフォルト言語を取得できなかった場合、
     * デフォルトロケールから言語を取得する。
     * 
     * @param codeId コードID
     * @param value コード値
     * @return 対応するコードの略称
     * @throws IllegalArgumentException 指定したコードIDが存在しないか、
     *                                   対象のコード値または言語に対応するデータが存在しない場合
     */
    String getShortName(String codeId, String value) throws IllegalArgumentException;

    /**
     * コードID、コード値、取得対象の言語を条件に、対応するコードの略称を取得する。<br/>
     * 
     * @param codeId コードID
     * @param value コード値
     * @param locale 取得対象の言語
     * @return 対応するコードの略称
     * @throws IllegalArgumentException 指定したコードIDが存在しないか、
     *                                   対象のコード値または言語に対応するデータが存在しない場合
     */
    String getShortName(String codeId, String value, Locale locale) throws IllegalArgumentException;

    /**
     * コードID、コード値、取得するオプション名称のカラム名を条件に、
     * 対応するコードのオプション名称を取得する。<br/>
     * 取得対象言語は、{@link nablarch.core.ThreadContext}でデフォルトとして指定された言語とする。
     * {@link nablarch.core.ThreadContext}からデフォルト言語を取得できなかった場合、
     * デフォルトロケールから言語を取得する。
     * 
     * @param codeId コードID
     * @param value コード値
     * @param optionColumnName 取得するオプション名称のカラム名（大文字・小文字を区別せずに使用する）
     * @return 対応するオプション名称
     * @throws IllegalArgumentException 指定したコードIDが存在しない場合、
     *                                   指定したオプション名称のカラムが存在しない場合、
     *                                   対象のコード値または言語に対応するデータが存在しない場合
     */
    String getOptionalName(String codeId, String value, String optionColumnName) throws IllegalArgumentException;

    /**
     * コードID、コード値、取得するオプション名称のカラム名、言語を条件に、
     * 対応するコードのオプション名称を取得する。<br/>
     * 取得対象言語は、{@link nablarch.core.ThreadContext}でデフォルトとして指定された言語とする。
     * {@link nablarch.core.ThreadContext}からデフォルト言語を取得できなかった場合、
     * デフォルトロケールから言語を取得する。
     *
     * @param codeId コードID
     * @param value コード値
     * @param optionColumnName 取得するオプション名称のカラム名（大文字・小文字を区別せずに使用する）
     * @param locale 言語
     * @return 対応するコードのオプション名称
     * @throws IllegalArgumentException 指定したコードIDが存在しない場合、
     *                                   指定したオプション名称のカラムが存在しない場合、
     *                                   対象のコード値または言語に対応するデータが存在しない場合
     */
    String getOptionalName(String codeId, String value, String optionColumnName, Locale locale) throws IllegalArgumentException;

    /**
     * コードIDに紐付く全てのコード値を取得する。<br/>
     * 取得対象言語は、{@link nablarch.core.ThreadContext}でデフォルトとして指定された言語とする。
     * {@link nablarch.core.ThreadContext}からデフォルト言語を取得できなかった場合、
     * デフォルトロケールから言語を取得する。
     * <p/>
     * 返却値は、あらかじめ言語ごとに定義されたソート順に従い、並び替えを行う。
     * <p/>
     * 
     * @param codeId コードID
     * @return コードIDに紐付く全てのコード値
     * @throws IllegalArgumentException 指定したコードIDが存在しないか、
     *                                   対象のコード値または言語に対応するデータが存在しない場合
     */
    List<String> getValues(String codeId) throws IllegalArgumentException;

    /**
     * コードID、パターンを条件に、対応するコード値を全て取得する。<br/>
     * 取得対象言語は、{@link nablarch.core.ThreadContext}でデフォルトとして指定された言語とする。
     * {@link nablarch.core.ThreadContext}からデフォルト言語を取得できなかった場合、
     * デフォルトロケールから言語を取得する。
     * 返却値は、あらかじめ言語ごとに定義されたソート順に従い、並び替えを行う。
     * 
     * @param codeId コードID
     * @param pattern 使用するパターンのカラム名（大文字・小文字を区別せずに使用する）
     * @return コードIDとパターンに紐付くコード値
     * @throws IllegalArgumentException 指定したコードIDが存在しないか、
     *                                   パターンまたは言語に対応するデータが存在しない場合
     */
    List<String> getValues(String codeId, String pattern) throws IllegalArgumentException;

    /**
     * コードIDと言語を条件に、対応するコード値を全て取得する。<br/>
     * <p/>
     * 返却値は、あらかじめ言語ごとに定義されたソート順に従い、並び替えを行う。
     * <p/>
     * 
     * @param codeId コードID
     * @param locale 言語
     * @return コードIDに紐付く全てのコード値
     * @throws IllegalArgumentException 対応するコードが存在しなかった場合。
     */
    List<String> getValues(String codeId, Locale locale) throws IllegalArgumentException;

    /**
     * コードID、パターン、言語を条件に、対応するコード値を全て取得する。<br/>
     * <p/>
     * 返却値は、あらかじめ言語ごとに定義されたソート順に従い、並び替えを行う。
     * <p/>
     * 
     * @param codeId コードID
     * @param pattern 使用するパターンのカラム名（大文字・小文字を区別せずに使用する）
     * @param locale 言語
     * @return コードIDとパターンに紐付くコード値
     * @throws IllegalArgumentException 指定したコードIDが存在しないか、
     *                                   パターンまたは言語に対応するデータが存在しない場合
     */
    List<String> getValues(String codeId, String pattern, Locale locale) throws IllegalArgumentException;

    /**
     * コードID、コード値を条件に、そのコード値を持ったコードが存在するかチェックする。<br/>
     * コードIDが存在する上で、そのコード値に対応するコードが存在する場合はtrueを、
     * 存在しない場合はfalseを返却する。
     * 
     * @param codeId コードID
     * @param value コード値
     * @return コード値がコードに存在する場合 true
     * @throws IllegalArgumentException コードIDが存在しなかった場合。
     */
    boolean contains(String codeId, String value) throws IllegalArgumentException;

    /**
     * コードID、使用するパターンのカラム名、コード値を条件に、
     * そのコード値に対応するコードが、パターンに存在するかチェックする。<br/>
     * コードID、パターンが存在する上で、
     * そのコード値に対応するコードがパターン内に存在する場合にはtrueを、存在しない場合にはfalseを返却する。
     *
     * @param codeId コードID
     * @param pattern 使用するパターンのカラム名（大文字・小文字を区別せずに使用する）
     * @param value コード値
     * @return コード値がコードに存在する場合 true
     * @throws IllegalArgumentException 指定したコードIDが存在しないか、
     *                                   指定したパターンが存在しない場合
     */
    boolean contains(String codeId, String pattern, String value) throws IllegalArgumentException;
}
