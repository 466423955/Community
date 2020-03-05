package hasaki.community.controller;

import hasaki.community.dto.CommentDTO;
import hasaki.community.dto.QuestionDTO;
import hasaki.community.enums.CommentTypeEnum;
import hasaki.community.service.CommentService;
import hasaki.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Create by hanzp on 2020-02-29
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Long id,
                           Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        List<CommentDTO> commentDTOList = commentService.getByParentId(id, CommentTypeEnum.QUESTION);
        questionService.increaseView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("commentList", commentDTOList);
        return "question";
    }
}
