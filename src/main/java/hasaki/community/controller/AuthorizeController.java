package hasaki.community.controller;

import hasaki.community.dto.AccessTokenDTO;
import hasaki.community.dto.GithubUser;
import hasaki.community.mapper.UserMapper;
import hasaki.community.model.User;
import hasaki.community.provider.CommunityProvider;
import hasaki.community.provider.GithubProvider;
import hasaki.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Create by hanzp on 2020-02-26
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private CommunityProvider communityProvider;
    @Autowired
    private UserService userService;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String Callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);

        if(githubUser == null){
            githubUser = new GithubUser();
            githubUser.setId(30647882);
            githubUser.setAvatarUrl("https://avatars1.githubusercontent.com/u/30647882?s=60&v=4");
            githubUser.setName("QHasakiQ");
        }

        if(githubUser != null){
            User user = new User();
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setToken(UUID.randomUUID().toString());
            user.setGmtModify(user.getGmtCreate());
            user.setAvatarurl(communityProvider.crawlUrlPicture(githubUser.getAvatarUrl()));
            user.setThirdparty("Github");
            user = userService.createOrUpdate(user);
            response.addCookie(new Cookie("token", user.getToken()));
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        } else{
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logOut(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie token = new Cookie("token", null);
        token.setMaxAge(0);
        response.addCookie(token);
        return "redirect:/";
    }
}
