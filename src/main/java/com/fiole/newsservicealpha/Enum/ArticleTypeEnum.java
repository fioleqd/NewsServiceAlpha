package com.fiole.newsservicealpha.Enum;

public enum ArticleTypeEnum {
    SOCIAL(1,"社会新闻"),
    WORLD(2,"国际"),
    BASKETBALL(3,"篮球"),
    FOOTBALL(4,"足球"),
    CAR(5,"汽车");

    private int type;
    private String description;

    ArticleTypeEnum(int type,String description){
        this.type = type;
        this.description = description;
    }

    public static ArticleTypeEnum getByType(int type){
        for (ArticleTypeEnum typeEnum : ArticleTypeEnum.values()){
            if (typeEnum.type == type)
                return typeEnum;
        }
        return null;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
