package hasaki.community.controller;

import hasaki.community.dto.QuestionDTO;
import hasaki.community.mapper.QuestionMapper;
import hasaki.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Create by hanzp on 2020-02-29
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionMapper;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Integer id,
                           Model model){
        QuestionDTO questionDTO = questionMapper.getById(id);
        model.addAttribute("question", questionDTO);
        return "question";
    }
}
