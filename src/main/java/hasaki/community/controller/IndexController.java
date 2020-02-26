package hasaki.community.controller;

import hasaki.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Console;

/**
 *
 */

@Controller
public class IndexController {


    @GetMapping("/")
    public String index(){
        return "index";
    }
}
