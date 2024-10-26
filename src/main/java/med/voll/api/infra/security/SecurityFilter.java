package med.voll.api.infra.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Ignora a verificação de token para o endpoint de login
        if (request.getRequestURI().equals("/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        var tokenJWT = resuperarToken(request);

        System.out.println(tokenJWT);
        filterChain.doFilter(request, response);
    }

    private String resuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null){
            throw new RuntimeException("Token JWT nao enviado no cabeçalho Authorization");
        }
        return authorizationHeader;
    }


}
