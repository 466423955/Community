package hasaki.community.service;

import hasaki.community.dto.CommentDTO;
import hasaki.community.enums.CommentTypeEnum;
import hasaki.community.exception.CustomizeErrorCode;
import hasaki.community.exception.CustomizeException;
import hasaki.community.mapper.CommentMapper;
import hasaki.community.mapper.QuestionExtMapper;
import hasaki.community.mapper.QuestionMapper;
import hasaki.community.mapper.UserMapper;
import hasaki.community.model.Comment;
import hasaki.community.model.CommentExample;
import hasaki.community.model.Question;
import hasaki.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by hanzp on 2020-03-02
 */
@Service
public class CommentService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Transactional
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
                Question dbQuestion = new Question();
                dbQuestion.setId(question.getId());
                commentMapper.insert(comment);
                dbQuestion.setCommentCount(1);
                questionExtMapper.increaseComment(dbQuestion);
            }
        }
    }

    public List<CommentDTO> getByQuestionId(Long id) {
        List<CommentDTO> commentDTOS = new ArrayList<>();
        CommentExample example = new CommentExample();
        example.createCriteria()
                .andParentIdEqualTo(id)
                .andParentTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        List<Comment> comments = commentMapper.selectByExampleWithBLOBs(example);

        Map<Long,User> userMap = new HashMap();
        for(Comment comment:comments){
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            User user;
            Long commentator = comment.getCommentator();
            if(userMap.containsKey(commentator)) {
                user = userMap.get(commentator);
            }else{
                user = userMapper.selectByPrimaryKey(commentator);
            }
            commentDTO.setUser(user);
            commentDTOS.add(commentDTO);
        }



        return commentDTOS;
    }
}
