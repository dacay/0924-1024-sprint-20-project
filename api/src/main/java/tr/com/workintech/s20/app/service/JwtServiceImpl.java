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

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Date;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

  private static final String AUDIENCE = "s20app.workintech.com.tr";
  private static final String ISSUER = "s20app.workintech.com.tr";

  @Value("${jwt.secret}")
  private String jwtSecret;
  private JWTVerifier jwtVerifier;

  @Autowired
  private UserRepository userRepository;

  public JWTVerifier getVerifier() {

    if (this.jwtVerifier != null)
      return this.jwtVerifier;

    Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

    this.jwtVerifier = JWT
            .require(algorithm)
            .withAudience(AUDIENCE)
            .withIssuer(ISSUER)
            .build();

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
  public String sign(User user) throws JWTCreationException {

    log.debug("Creating token...");

    Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

    return JWT
            .create()
            .withSubject(Long.toString(user.getId()))
            .withExpiresAt(Instant.now().plus(Duration.ofDays(3)))
            .withIssuedAt(Instant.now())
            .withIssuer(ISSUER)
            .withAudience(AUDIENCE)
            .withClaim("email", user.getEmail())
            .sign(algorithm);
  }
}
