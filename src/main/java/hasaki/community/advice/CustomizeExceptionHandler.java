package hasaki.community.advice;

import com.alibaba.fastjson.JSON;
import hasaki.community.dto.ResponseResultDTO;
import hasaki.community.exception.CustomizeErrorCode;
import hasaki.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model, HttpServletResponse response) {
        String contentType = request.getContentType();
        if("application/json".equalsIgnoreCase(contentType)){
            ResponseResultDTO resultDTO = new ResponseResultDTO();
            if(e instanceof CustomizeException){
                resultDTO = (ResponseResultDTO) ResponseResultDTO.errorOf((CustomizeException) e);
            }else{
                resultDTO = (ResponseResultDTO) ResponseResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }

            try{
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(200);
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }
        else{
            if(e instanceof CustomizeException){
                model.addAttribute("message", e.getMessage());
            }else{
                model.addAttribute("message", CustomizeErrorCode.SYSTEM_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
