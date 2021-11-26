package cn.nolaurence.anno.config;

import cn.nolaurence.anno.entity.User;
import cn.nolaurence.anno.exception.BadRequestException;
import cn.nolaurence.anno.utils.JacksonUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {
    ThreadLocal<String> currentUsername = new ThreadLocal<>();
    protected JwtLoginFilter(String defaultFilterProcessesUrl,
                             AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        try {
            if (!"POST".equals(request.getMethod())) {
                throw new BadRequestException("请求方法错误");
            }
            User user = JacksonUtils.readValue(request.getInputStream(), User.class);
            currentUsername.set(user.getUsername());
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (BadRequestException exception) {
            response.setContentType("application/json;");
        }
    }
}
