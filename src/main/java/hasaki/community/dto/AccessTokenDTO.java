package hasaki.community.dto;

import lombok.Data;

/**
 * Create by hanzp on 2020-02-26
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
