package io.chorok2.modules.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.chorok2.infra.yml.MessageSourceUtil;
import io.chorok2.modules.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final MessageSourceUtil messageSourceUtil;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponse er = ErrorResponse.of(messageSourceUtil.getMessage("authenticationError.code"), messageSourceUtil.getMessage("authenticationError.message"));

        response.setStatus(HttpStatus.valueOf(Integer.parseInt(messageSourceUtil.getMessage("authenticationError.status"))).value());
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(er));
        writer.flush();
    }

}
