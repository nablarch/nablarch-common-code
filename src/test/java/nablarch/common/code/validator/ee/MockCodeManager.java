package nablarch.common.code.validator.ee;

import java.util.List;
import java.util.Locale;

import nablarch.common.code.CodeManager;

/**
 * TODO write document comment.
 *
 * @author T.Kawasaki
 */
public class MockCodeManager implements CodeManager {

    boolean containsResponse = false;

    @Override
    public String getName(String codeId, String value) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String getName(String codeId, String value, Locale locale) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String getShortName(String codeId, String value) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String getShortName(String codeId, String value, Locale locale) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String getOptionalName(String codeId, String value,
            String optionColumnName) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String getOptionalName(String codeId, String value, String optionColumnName,
            Locale locale) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<String> getValues(String codeId) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<String> getValues(String codeId, String pattern) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<String> getValues(String codeId, Locale locale) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<String> getValues(String codeId, String pattern, Locale locale) throws IllegalArgumentException {
        return null;
    }

    @Override
    public boolean contains(String codeId, String value) throws IllegalArgumentException {
        return false;
    }

    @Override
    public boolean contains(String codeId, String pattern, String value) throws IllegalArgumentException {
        return false;
    }
}
