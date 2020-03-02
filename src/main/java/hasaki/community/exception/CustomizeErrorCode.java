package hasaki.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"你找的问题不在了，要不换个试试?"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论"),
    NO_LOGIN(2003,"当前操作需要登录，请登录后重试"),
    SYSTEM_ERROR(2004,"服务冒烟了，要不然你过会儿再来试试... ..."),
    PARENT_TYPE_ERROR(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不在了，要不换个试试?");

    private Integer code;
    private String message;

    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
