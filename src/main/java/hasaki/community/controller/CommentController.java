package hasaki.community.controller;

import hasaki.community.dto.CommentCreateDTO;
import hasaki.community.dto.CommentDTO;
import hasaki.community.dto.PaginationDTO;
import hasaki.community.dto.ResponseResultDTO;
import hasaki.community.enums.CommentTypeEnum;
import hasaki.community.exception.CustomizeErrorCode;
import hasaki.community.model.Comment;
import hasaki.community.model.User;
import hasaki.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Create by hanzp on 2020-03-02
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentCreateDTO commentDTO,
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return ResponseResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if(commentDTO.getContent() == null || StringUtils.isEmpty(commentDTO.getContent())){
            return ResponseResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setDescription(commentDTO.getContent());
        comment.setParentType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModify(System.currentTimeMillis());
        comment.setLikeCount(0L);
        comment.setCommentCount(0);
        comment.setCommentator(user.getId());
        commentService.insert(comment);
        return ResponseResultDTO.successOf();
    }

    @ResponseBody
    @RequestMapping(value="/comment/{id}", method=RequestMethod.GET)
    public Object post(@PathVariable("id") Long ParentId,
                       @RequestParam(value = "page",defaultValue = "1") Integer page,
                       @RequestParam(value = "size",defaultValue = "5") Integer size){
        PaginationDTO<CommentDTO> commentDTOList = commentService.getByParentId(ParentId, CommentTypeEnum.COMMENT, page, size);
        return ResponseResultDTO.successOf(commentDTOList);
    }
}
