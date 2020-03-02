package hasaki.community.mapper;

import hasaki.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int increaseView(Question record);
    int increaseComment(Question record);
}