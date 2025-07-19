package tr.com.workintech.s20.app.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tr.com.workintech.s20.app.entity.User;
import tr.com.workintech.s20.app.repository.UserRepository;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

  @Value("${jwt.secret}")
  private String jwtSecret;
  private JWTVerifier jwtVerifier;

  @Autowired
  private UserRepository userRepository;

  public JWTVerifier getVerifier() {

    if (this.jwtVerifier != null)
      return this.jwtVerifier;

    Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

    this.jwtVerifier = JWT.require(algorithm).build();

    return this.jwtVerifier;
  }

  @Override
  public User verify(String token) throws JWTVerificationException {

    log.debug("Verifying token '{}'...", token);

    JWTVerifier verifier = getVerifier();

    DecodedJWT decodedJWT = verifier.verify(token);

    long userId = Long.parseLong(decodedJWT.getSubject());

    return userRepository.findById(userId).get();
  }

  @Override
  public String create(User user) throws JWTCreationException {

    log.debug("Creating token...");

    Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

    return JWT
            .create()
            .withSubject(Long.toString(user.getId()))
            .withClaim("email", user.getEmail())
            .sign(algorithm);
  }
}
