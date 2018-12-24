package handler;

public enum Country implements IEnum{
    CHINA("CHINA", "中国"),
    JAPAN("JAPAN", "小日本");

    private String name;
    private String value;

    Country(String name, String value) {
        this.name = name;
        this.value = value;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
