package hasaki.community.dto;

import lombok.Data;

/**
 * Create by hanzp on 2020-03-02
 */
@Data
public class CommentDTO {
    private long parentId;
    private String content;
    private Integer type;
}
