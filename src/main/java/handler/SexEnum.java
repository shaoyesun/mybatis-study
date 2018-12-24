package handler;

public enum SexEnum implements IEnum{
    BOY("BOY", "男"),
    GIRL("GIRL", "女");

    private String value;
    private String name;

    SexEnum(String name, String value) {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
