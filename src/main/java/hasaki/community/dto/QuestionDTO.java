package hasaki.community.dto;

import hasaki.community.model.User;
import lombok.Data;

/**
 * Create by hanzp on 2020-02-28
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModify;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
