package hasaki.community.mapper;

import hasaki.community.model.Comment;

/**
 * Create by hanzp on 2020-03-05
 */
public interface CommentExtMapper {
    int increaseCommentCount(Comment record);
}
