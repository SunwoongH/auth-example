package example.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.common.BaseResponse;
import example.common.Constants;
import example.common.HttpStatusConstants;
import example.exception.UnauthorizedException;
import example.message.ErrorMessage;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (UnauthorizedException e) {
            handleUnauthorizedException(response, e);
        } catch (Exception ee) {
            handleException(response, ee);
        }
    }

    private void handleUnauthorizedException(HttpServletResponse response, Exception e) throws IOException {
        UnauthorizedException ue = (UnauthorizedException) e;
        ErrorMessage errorMessage = ue.getErrorMessage();
        int status = errorMessage.getStatus();
        setResponse(response, status, errorMessage);
    }

    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        log.error(">>> Exception Handler Filter : ", e);
        setResponse(response, HttpStatusConstants.INTERNAL_SERVER_ERROR, ErrorMessage.INTERNAL_SERVER_ERROR);
    }

    private void setResponse(HttpServletResponse response, int status, ErrorMessage errorMessage) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(Constants.CHARACTER_TYPE);
        response.setStatus(status);
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(BaseResponse.of(errorMessage)));
    }
}
