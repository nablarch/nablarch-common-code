package nablarch.common.code;

/**
 * コードパターン
 * 
 */
public class CodePattern {
    
    public CodePattern() {
    };
    
    public CodePattern(String id, String value, String pattern1, String pattern2, String pattern3) {
        this.id = id;
        this.value = value;
        this.pattern1 = pattern1;
        this.pattern2 = pattern2;
        this.pattern3 = pattern3;
    }

    public String id;

    public String value;

    public String pattern1;

    public String pattern2;

    public String pattern3;
}