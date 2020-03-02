package hasaki.community.enums;

/**
 * Create by hanzp on 2020-03-02
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    public static boolean isExsit(Integer parentType) {
        for(CommentTypeEnum typeEnum: CommentTypeEnum.values()){
            if(typeEnum.getType() == parentType){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
