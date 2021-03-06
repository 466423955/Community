package hasaki.community.service;

import hasaki.community.dto.PaginationDTO;
import hasaki.community.dto.QuestionDTO;
import hasaki.community.exception.CustomizeErrorCode;
import hasaki.community.exception.CustomizeException;
import hasaki.community.mapper.QuestionExtMapper;
import hasaki.community.mapper.QuestionMapper;
import hasaki.community.mapper.UserMapper;
import hasaki.community.model.Question;
import hasaki.community.model.QuestionExample;
import hasaki.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by hanzp on 2020-02-28
 */
@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO();

        long totalCount = questionMapper.countByExample(new QuestionExample());
        paginationDTO.setPagination(totalCount, page, size);

        if(page < 1)   {
            page = 1;
        }
        if(page > paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }

        Integer offset = size * (page - 1);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        List<QuestionDTO> questionDTOS = getDTOSByQuestion(questionList);
        paginationDTO.setDatas(questionDTOS);
        return paginationDTO;
    }

    public PaginationDTO list(long userId, Integer page, Integer size) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        long totalCount = questionMapper.countByExample(questionExample);
        paginationDTO.setPagination(totalCount, page, size);

        if(page < 1)   {
            page = 1;
        }
        if(page > paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }

        Integer offset = size * (page - 1);
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        List<QuestionDTO> questionDTOS = getDTOSByQuestion(questionList);
        paginationDTO.setDatas(questionDTOS);
        return paginationDTO;
    }

    public QuestionDTO getById(long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = getDTOByQuestion(question);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModify(System.currentTimeMillis());
            questionMapper.insert(question);
        } else{
            question.setGmtModify(System.currentTimeMillis());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(question, questionExample);
        }
    }

    public void increaseView(Long questionId) {
        Question question = new Question();
        question.setId(questionId);
        question.setViewCount(1);
        questionExtMapper.increaseView(question);
    }

    public List<QuestionDTO> getRelated(QuestionDTO questionDTO) {
        if(StringUtils.isEmpty(questionDTO.getTag())){
            return new ArrayList<>();
        }
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(StringUtils.joinWith("|", questionDTO.getTag().split(",")));
        List<Question> questions = questionExtMapper.selectRelated(question);
        if(questions.size() > 10){
            return getDTOSByQuestion(questions.subList(0, 9));
        }
        return getDTOSByQuestion(questions);
    }

    @NotNull
    public List<QuestionDTO> getDTOSByQuestion(List<Question> questionList) {
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questionList) {
            QuestionDTO questionDTO = getDTOByQuestion(question);
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }

    @NotNull
    public QuestionDTO getDTOByQuestion(Question question) {
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }
}
