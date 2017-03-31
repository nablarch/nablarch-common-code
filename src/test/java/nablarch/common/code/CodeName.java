package nablarch.common.code;

/**
 * コード名称
 * 
 */
public class CodeName {
    
    public CodeName() {
    };
    
    public CodeName(String id, String value, String lang, Long sortOrder, String name,
            String shortName, String nameWithValue, String option01) {
        this.id = id;
        this.value = value;
        this.lang = lang;
        this.sortOrder = sortOrder;
        this.name = name;
        this.shortName = shortName;
        this.nameWithValue = nameWithValue;
        this.option01 = option01;
    }

    public String id;

    public String value;

    public String lang;

    public Long sortOrder;

    public String name;

    public String shortName;

    public String nameWithValue;

    public String option01;
}
