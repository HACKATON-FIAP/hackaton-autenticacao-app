package br.com.fiap.usuarios.config;

import br.com.fiap.usuarios.domain.service.JwtService;
import br.com.fiap.usuarios.domain.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = usuarioService.loadUserByUsername(username);
            if(jwtService.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }

        filterChain.doFilter(request, response);
    }
}
