package hasaki.community.service;

import hasaki.community.enums.CommentTypeEnum;
import hasaki.community.exception.CustomizeErrorCode;
import hasaki.community.exception.CustomizeException;
import hasaki.community.mapper.CommentMapper;
import hasaki.community.mapper.QuestionExtMapper;
import hasaki.community.mapper.QuestionMapper;
import hasaki.community.model.Comment;
import hasaki.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by hanzp on 2020-03-02
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    public void insert(Comment comment) {
        if(comment.getParentType() == null || comment.getParentId() == null){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if(comment.getParentType() == null || !CommentTypeEnum.isExsit(comment.getParentType())){
            throw new CustomizeException(CustomizeErrorCode.PARENT_TYPE_ERROR);
        }

        if(comment.getParentType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment parentComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(parentComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            else{
                commentMapper.insert(comment);
            }
        }else{
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            else{
                commentMapper.insert(comment);
                Question dbQuestion = new Question();
                dbQuestion.setId(question.getId());
                dbQuestion.setViewCount(1);
                questionExtMapper.increaseComment(dbQuestion);
            }
        }
    }
}
