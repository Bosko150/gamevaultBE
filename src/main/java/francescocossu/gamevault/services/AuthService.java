package francescocossu.gamevault.services;

import francescocossu.gamevault.entities.User;
import francescocossu.gamevault.exceptions.UnauthorizedException;
import francescocossu.gamevault.payloads.UserLoginDTO;
import francescocossu.gamevault.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bCrypt;

    public String authenticateUtenteAndGenerateToken(UserLoginDTO payload) {
        User user = this.userService.findByEmail(payload.email());
        if (bCrypt.matches(payload.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Credenziali non corrette");
        }
    }
}
