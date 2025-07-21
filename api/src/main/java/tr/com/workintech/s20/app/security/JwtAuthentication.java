package tr.com.workintech.s20.app.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import tr.com.workintech.s20.app.entity.User;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JwtAuthentication implements Authentication {

  private User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getDetails() {
    return user;
  }

  @Override
  public Object getPrincipal() {
    return user;
  }

  @Override
  public boolean isAuthenticated() {
    return true;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
  }

  @Override
  public String getName() {
    return "";
  }
}
