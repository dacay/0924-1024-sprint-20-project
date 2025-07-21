package tr.com.workintech.s20.app.service;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import tr.com.workintech.s20.app.entity.User;

public interface JwtService {

  User verify(String token) throws JWTVerificationException;

  String sign(User user) throws JWTCreationException;
}
