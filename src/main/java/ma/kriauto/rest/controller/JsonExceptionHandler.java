package ma.kriauto.rest.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ma.kriauto.rest.domain.ResponseMessage;
import ma.kriauto.rest.util.Constant;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Called when an exception occurs during request processing. Transforms the exception message into JSON format.
 */
@Component
public class JsonExceptionHandler implements HandlerExceptionResolver {
    private ObjectMapper mapper = new ObjectMapper();

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
        	if( null != ex.getMessage() && null != Constant.getLabels().get(ex.getMessage()).toString()){
        	   response.setStatus(401);
               mapper.writeValue(response.getWriter(), new ResponseMessage(ResponseMessage.Type.danger, ex.getMessage(),Constant.getLabels().get(ex.getMessage()).toString()));
        	}else{
        	   response.setStatus(401);
               mapper.writeValue(response.getWriter(), new ResponseMessage(ResponseMessage.Type.danger, ex.getMessage(),null));
        	}
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView();
    }
}
