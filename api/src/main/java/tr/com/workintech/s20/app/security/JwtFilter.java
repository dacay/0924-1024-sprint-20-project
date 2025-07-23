package tr.com.workintech.s20.app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tr.com.workintech.s20.app.entity.User;
import tr.com.workintech.s20.app.exception.AuthenticationException;
import tr.com.workintech.s20.app.service.JwtService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

  @Autowired
  private JwtService jwtService;
  
  // List of protected path prefixes
  private final List<String> protectedPaths = Arrays.asList("/movies", "/categories");

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getServletPath();

    log.debug("Request method is {}.", request.getMethod());

    if (request.getMethod().equals("OPTIONS")) {
      return true;
    }

    // Skip auth paths
    if (path.startsWith("/auth/")) {
      return true;
    }
    
    // Only apply filter to protected paths
    boolean isProtectedPath = protectedPaths.stream()
        .anyMatch(prefix -> path.startsWith(prefix));
        
    // Return true to skip filter if path is not protected
    return !isProtectedPath;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
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

    SecurityContextHolder.getContext().setAuthentication(new JwtAuthentication(user));

    // Do the rest of filter
    filterChain.doFilter(request, response);
  }
}
