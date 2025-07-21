package tr.com.workintech.s20.app.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tr.com.workintech.s20.app.entity.User;
import tr.com.workintech.s20.app.exception.AuthenticationException;
import tr.com.workintech.s20.app.service.JwtService;

import java.io.IOException;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

  @Autowired
  private JwtService jwtService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    // Check if we need authentication
    if (request.getServletPath().startsWith("/auth/")) {

      log.debug("Skipping JWT filter for auth path...");

      // Do the rest of filter
      filterChain.doFilter(request, response);

      return;
    }

    // Get authorization header
    String authHeader = request.getHeader("Authorization");

    // Check if auth header exists
    if (authHeader == null || authHeader.isBlank()) {

      log.debug("Authorization header is missing");

      throw new AuthenticationException("Authorization header is missing");
    }

    // Check if auth header starts with Bearer
    if (!authHeader.startsWith("Bearer")) {

      log.debug("Authorization header does not start with bearer prefix");

      throw new AuthenticationException("Authorization header is missing bearer prefix");
    }

    // Get token
    String token = authHeader.substring(7);

    log.debug("Checking token...");

    User user = jwtService.verify(token);

    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null));

    // Do the rest of filter
    filterChain.doFilter(request, response);
  }
}
