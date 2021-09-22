package mk.gp.emt.userbase.config.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mk.gp.emt.userbase.config.JWTAuthConstants;
import mk.gp.emt.userbase.services.forms.PersonForm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTAuthorizationFilter  extends BasicAuthenticationFilter {

    private final UserDetailsService userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JWTAuthConstants.HEADER_STRING);
        if (header==null || !header.startsWith(JWTAuthConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken token = getToken(header);
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }

    public UsernamePasswordAuthenticationToken getToken(String header) throws JsonProcessingException {
        // parse the token.
        String user = JWT.require(Algorithm.HMAC256(JWTAuthConstants.SECRET.getBytes()))
                .build()
                .verify(header.replace(JWTAuthConstants.TOKEN_PREFIX, ""))
                .getSubject();
        if (user == null) {
            return null;
        }
        PersonForm userDetails = new ObjectMapper().readValue(user, PersonForm.class);
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", Collections.singleton(userDetails.getRole()));
    }
}
