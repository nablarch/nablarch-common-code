package nablarch.common.code;

import java.util.List;
import java.util.Locale;

import nablarch.core.ThreadContext;
import nablarch.core.cache.StaticDataCache;

/**
 * CodeManagerの基本実装クラス。
 * 
 * @author Koichi Asano
 */
public class BasicCodeManager implements CodeManager {

    /**
     * Codeのキャッシュ。
     */
    private StaticDataCache<Code> codeDefinitionCache;

    /**
     * Codeのキャッシュをセットする。<br/>
     * 
     * Codeのキャッシュは、コード値をキーとしてCodeインタフェースを実装したクラスが取得できなければならない。
     * 
     * @param codeDefinitionCache Codeのキャッシュ
     */
    public void setCodeDefinitionCache(StaticDataCache<Code> codeDefinitionCache) {
        this.codeDefinitionCache = codeDefinitionCache;
    }

    /**
     * {@inheritDoc}
     */
    public boolean contains(String codeId, String value) {
        Code def = codeDefinitionCache.getValue(codeId);
        if (def == null) {
            throw new IllegalArgumentException("code id is not valid." 
                    + " code id = " + codeId);
        }
        return def.contains(value);
    }

    /**
     * {@inheritDoc}
     */
    public boolean contains(String codeId, String pattern, String value) {
        Code def = codeDefinitionCache.getValue(codeId);
        if (def == null) {
            throw new IllegalArgumentException("code id is not valid." 
                    + " code id = " + codeId);
        }
        return def.contains(pattern, value);
    }

    /**
     * {@inheritDoc}
     */
    public String getName(String codeId, String value) {
        Code def = codeDefinitionCache.getValue(codeId);
        if (def == null) {
            throw new IllegalArgumentException("code id is not valid." 
                    + " code id = " + codeId);
        }
        return def.getName(value, getLanguage());
    }

    /**
     * {@inheritDoc}
     */
    public String getName(String codeId, String value, Locale locale) {
        Code def = codeDefinitionCache.getValue(codeId);
        if (def == null) {
            throw new IllegalArgumentException("code id is not valid." 
                    + " code id = " + codeId);
        }
        return def.getName(value, locale);
    }

    /**
     * {@inheritDoc}
     */
    public String getShortName(String codeId, String value) {
        Code def = codeDefinitionCache.getValue(codeId);
        if (def == null) {
            throw new IllegalArgumentException("code id is not valid." 
                    + " code id = " + codeId);
        }
        return def.getShortName(value, getLanguage());
    }

    /**
     * {@inheritDoc}
     */
    public String getShortName(String codeId, String value, Locale locale) {
        Code def = codeDefinitionCache.getValue(codeId);
        if (def == null) {
            throw new IllegalArgumentException("code id is not valid." 
                    + " code id = " + codeId);
        }
        return def.getShortName(value, locale);
    }

    /**
     * {@inheritDoc}
     */
    public String getOptionalName(String codeId, String value,
            String optionColumnName) {
        Code def = codeDefinitionCache.getValue(codeId);
        if (def == null) {
            throw new IllegalArgumentException("code id is not valid." 
                    + " code id = " + codeId);
        }
        return def.getOptionalName(value, optionColumnName, getLanguage());
    }

    /**
     * {@inheritDoc}
     */
    public String getOptionalName(String codeId, String value,
            String optionColumnName, Locale locale) {
        Code def = codeDefinitionCache.getValue(codeId);
        if (def == null) {
            throw new IllegalArgumentException("code id is not valid." 
                    + " code id = " + codeId);
        }
        return def.getOptionalName(value, optionColumnName, locale);
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getValues(String codeId) {
        Code def = codeDefinitionCache.getValue(codeId);
        if (def == null) {
            throw new IllegalArgumentException("code id is not valid." 
                    + " code id = " + codeId);
        }
        return def.getValues(getLanguage());
    }

    /**
     * {@inheritDoc}
     */
    public List<String>  getValues(String codeId, String pattern) {
        Code def = codeDefinitionCache.getValue(codeId);
        if (def == null) {
            throw new IllegalArgumentException("code id is not valid." 
                    + " code id = " + codeId);
        }
        return def.getValues(pattern, getLanguage());
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getValues(String codeId, Locale locale) {
        Code def = codeDefinitionCache.getValue(codeId);
        if (def == null) {
            throw new IllegalArgumentException("code id is not valid." 
                    + " code id = " + codeId);
        }
        return def.getValues(locale);
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getValues(String codeId, String pattern, Locale locale) {
        Code def = codeDefinitionCache.getValue(codeId);
        if (def == null) {
            throw new IllegalArgumentException("code id is not valid." 
                    + " code id = " + codeId);
        }
        return def.getValues(pattern, locale);
    }

    /** デフォルトの言語 */
    private static final Locale DEFAULT_LOCALE = new Locale(Locale.getDefault().getLanguage());

    /**
     * スレッドコンテキストから言語を取得する。
     *
     * スレッドコンテキストに設定されていない場合は
     * {@link Locale#getDefault()}から取得した言語を返す。
     *
     * @return 言語
     */
    private static Locale getLanguage() {
        final Locale language = ThreadContext.getLanguage();
        return language != null ? language : DEFAULT_LOCALE;
    }
}
