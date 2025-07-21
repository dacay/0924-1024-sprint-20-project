package tr.com.workintech.s20.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.workintech.s20.app.entity.User;
import tr.com.workintech.s20.app.exception.AuthenticationException;
import tr.com.workintech.s20.app.exception.UserAlreadyExistsException;
import tr.com.workintech.s20.app.repository.UserRepository;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(String email, String password) throws UserAlreadyExistsException {

        log.debug("Checking if a user with email '{}' exists...", email);

        Optional<User> existingUser = repository.findByEmail(email);

        // Check if user exists
        if (existingUser.isPresent()) {

            log.error("User already exists with email '{}'.", email);

            throw new UserAlreadyExistsException();
        }

        log.debug("Encoding password...");

        // Encode the password
        String encodedPassword = passwordEncoder.encode(password);

        // Create user object
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(encodedPassword);

        log.debug("Saving user: {}", newUser);

        // Save user
        newUser = repository.save(newUser);

        log.debug("Saved user: {}", newUser);

        // Return the newly created user
        return newUser;
    }

    @Override
    public String login(String email, String password) throws AuthenticationException {

        log.debug("Checking if a user with email '{}' exists...", email);

        Optional<User> existingUser = repository.findByEmail(email);

        // Check if user exists
        if (!existingUser.isPresent()) {

            log.error("User does not exist with email '{}'.", email);

            throw new AuthenticationException("User does not exist");
        }

        boolean passwordsMatch = this.passwordEncoder.matches(password, existingUser.get().getPassword());

        if (!passwordsMatch) {

            log.error("User passwords do not match.");

            throw new AuthenticationException("User passwords do not match.");
        }

        log.debug("User passwords match.");

        String token = this.jwtService.sign(existingUser.get());

        log.debug("Signed token for user '{}': {}", email, token);

        return token;
    }

    @Override
    public void deactive(Long id) {

    }
}
