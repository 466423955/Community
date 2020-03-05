package hasaki.community.dto;

import hasaki.community.model.User;
import lombok.Data;

/**
 * Create by hanzp on 2020-03-04
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer parentType;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModify;
    private Long likeCount;
    private String description;
    private Integer commentCount;
    private User user;
}
