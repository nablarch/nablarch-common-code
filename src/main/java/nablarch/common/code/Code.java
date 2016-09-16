package nablarch.common.code;

import java.util.List;
import java.util.Locale;

/**
 * 単一のコードデータ(コードIDに紐づくデータ)にアクセスするインタフェース。<br/>
 * 
 * 
 * @author Koichi Asano
 */
public interface Code {

    /**
     * コードIDを取得する。
     * 
     * @return コードID
     */
    String getCodeId();

    /**
     * コードにコード値が含まれるかチェックする。
     * 
     * @param value コード値
     * @return コードにコード値が含まれる場合true
     */
    boolean contains(String value);

    /**
     * パターンにコード値が含まれるかチェックする。
     * @param pattern 使用するパターンのカラム名
     * @param value コード値
     * 
     * @return パターンにコード値が含まれる場合true
     * 
     * @throws IllegalArgumentException パターンが存在しなかった場合。
     */
    boolean contains(String pattern, String value) throws IllegalArgumentException;
    
    /**
     * コード値を全て取得する。
     * 
     * @param locale 言語
     * 
     * @return コードに含まれる全てのコード値のリスト
     * 
     * @throws IllegalArgumentException 言語に対応するデータが存在しなかった場合。
     */
    List<String> getValues(Locale locale) throws IllegalArgumentException;

    /**
     * パターンを指定してコード値を取得する。<br/>
     * 
     * @param pattern 使用するパターンのカラム名
     * @param locale 言語
     * 
     * @return パターンに含まれるコード値のリスト
     * @throws IllegalArgumentException パターンまたは言語に対応するデータが存在しなかった場合。
     */
    List<String> getValues(String pattern, Locale locale) throws IllegalArgumentException;

    /**
     * 言語を指定してコード名称を取得する。
     * 
     * @param value コード値
     * @param locale 取得するコード名称の言語
     * @return 言語に対応する名称
     * @throws IllegalArgumentException コード値または言語に対応するデータが存在しなかった場合。
     */
    String getName(String value, Locale locale) throws IllegalArgumentException;

    /**
     * 言語を指定してコードの略称を取得する。
     * 
     * @param value コード値
     * @param locale 取得するコード名称の言語
     * @return 言語に対応する略称
     * @throws IllegalArgumentException コード値または言語に対応するデータが存在しなかった場合。
     */
    String getShortName(String value, Locale locale) throws IllegalArgumentException;

    /**
     * 言語を指定してオプションコード名称を取得する。
     * 
     * @param value コード値
     * @param optionColumnName オプション名称のカラム名
     * @param locale 取得するコード名称の言語
     * @return 言語に対応する名称
     * @throws IllegalArgumentException コード値、オプション名称のカラム名、または言語に対応するデータが存在しなかった場合。
     */
    String getOptionalName(String value, String optionColumnName, Locale locale) throws IllegalArgumentException;
}
