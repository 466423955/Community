package hasaki.community.dto;

import hasaki.community.exception.CustomizeErrorCode;
import hasaki.community.exception.CustomizeException;
import lombok.Data;
import org.springframework.web.servlet.ModelAndView;

/**
 * Create by hanzp on 2020-03-02
 */
@Data
public class ResponseResultDTO {
    private Integer code;
    private String message;

    public static ResponseResultDTO errorOf(int code, String message){
        ResponseResultDTO resultDTO = new ResponseResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static Object errorOf(CustomizeErrorCode errorCode) {
        return ResponseResultDTO.errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static Object errorOf(CustomizeException e) {
        return ResponseResultDTO.errorOf(e.getCode(), e.getMessage());
    }

    public static Object successOf() {
        ResponseResultDTO resultDTO = new ResponseResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }
}
