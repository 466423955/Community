package hasaki.community.model;

import lombok.Data;

/**
 * Create by hanzp on 2020-02-27
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModify;
    private String thirdParty;
    private String avatarUrl;
}
