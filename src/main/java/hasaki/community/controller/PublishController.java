package hasaki.community.controller;

import com.sun.org.apache.xpath.internal.objects.XNull;
import hasaki.community.dto.QuestionDTO;
import hasaki.community.mapper.QuestionMapper;
import hasaki.community.mapper.UserMapper;
import hasaki.community.model.Question;
import hasaki.community.model.User;
import hasaki.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Create by hanzp on 2020-02-27
 */
@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer questionId,
                       Model model) {
        QuestionDTO questionDTO = questionService.getById(questionId);
        model.addAttribute("title", questionDTO.getTitle());
        model.addAttribute("description", questionDTO.getDescription());
        model.addAttribute("tag", questionDTO.getTag());
        model.addAttribute("id", questionDTO.getId());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false) Integer id,
            HttpServletRequest request,
            Model model
    ) {

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("id", id);

        if (title == null || title == "") {
            model.addAttribute("error", "用户未登录！");
            return "/publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "问题补充不允许为空！");
            return "/publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空！");
            return "/publish";
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "/publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
