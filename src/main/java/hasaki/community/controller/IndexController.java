package hasaki.community.controller;

import hasaki.community.dto.PaginationDTO;
import hasaki.community.dto.QuestionDTO;
import hasaki.community.mapper.QuestionMapper;
import hasaki.community.mapper.UserMapper;
import hasaki.community.model.Question;
import hasaki.community.model.User;
import hasaki.community.provider.GithubProvider;
import hasaki.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.Console;
import java.util.List;

/**
 *
 */

@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name="page",defaultValue = "1") Integer page,
                        @RequestParam(name="size",defaultValue = "5") Integer size){

        PaginationDTO pagination = questionService.list(page, size);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}
