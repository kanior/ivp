package kanior.ivp.service;

import kanior.ivp.dto.LoginUserInfo;
import kanior.ivp.dto.UserSaveRequest;
import kanior.ivp.entity.User;
import kanior.ivp.entity.UserRole;
import kanior.ivp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public boolean isLoginIdDuplicated(String loginId) {
        return !userRepository.findByLoginId(loginId).isEmpty();
    }

    @Transactional
    public Long save(UserSaveRequest form) {
        return userRepository.save(User.builder()
                .loginId(form.getLoginId())
                .password(form.getPassword())
                .name(form.getName())
                .birthDate(form.getBirthDate())
                .role(UserRole.USER)
                .build()
        ).getId();
    }

    public LoginUserInfo login(String loginId, String password) {
        return userRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .map(LoginUserInfo::new)
                .orElse(null);
    }
}
