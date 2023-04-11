package kanior.ivp.user.command.application;

import kanior.ivp.user.command.domain.User;
import kanior.ivp.user.command.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User findByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 정보가 존재하지 않습니다. loginId=" + loginId));
    }

    @Transactional(readOnly = true)
    public boolean isLoginIdAndPasswordValid(String loginId, String password) {
        User user = userRepository.findByLoginId(loginId).orElse(null);
        if (user != null && user.isPasswordCorrect(password)) {
            return true;
        }
        return false;
    }

    @Transactional
    public void delete(String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 정보가 존재하지 않습니다. loginId=" + loginId));

        userRepository.delete(user);
    }
}
