package br.arturslampert.minisena.modules.user.usecases;

import br.arturslampert.minisena.modules.user.UserEntity;
import br.arturslampert.minisena.modules.user.UserRepository;
import br.arturslampert.minisena.modules.user.dtos.AuthUserRequestDTO;
import br.arturslampert.minisena.modules.user.dtos.AuthUserResponseDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;


@Service
public class AuthUserUseCase {

    @Value("${security.token.secret}")
    private String secretKey;
    private final UserRepository bettorRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthUserUseCase(UserRepository bettorRepository, PasswordEncoder passwordEncoder) {
        this.bettorRepository = bettorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthUserResponseDTO authenticate(AuthUserRequestDTO authUserRequestDTO){

        UserEntity user = this.bettorRepository.findByEmail(authUserRequestDTO.getEmail()).orElseThrow(() -> new RuntimeException("Username/password incorrect"));

        boolean passwordMatches = this.passwordEncoder.matches(authUserRequestDTO.getPassword(), user.getPassword());

        if (!passwordMatches) {
            throw new RuntimeException("Username/password incorrect");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey );

        Instant expiresIn = Instant.now().plus(Duration.ofMinutes(10));

        String token = JWT
                .create()
                .withExpiresAt(expiresIn)
                .withSubject(Integer.valueOf(user.getId()).toString())
                .withClaim("role", List.of("USER"))
                .sign(algorithm);

        return AuthUserResponseDTO.builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();
    }
}
