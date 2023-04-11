package kanior.ivp.user.command.application;

import kanior.ivp.user.command.domain.User;
import kanior.ivp.user.command.domain.UserRepository;
import kanior.ivp.user.command.domain.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SaveUserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public boolean isLoginIdExisted(String loginId) {
        return userRepository.findByLoginId(loginId).isPresent();
    }

    @Transactional
    public Long save(SaveUserRequest saveRequest) {
        return userRepository.save(User.builder()
                .loginId(saveRequest.getLoginId())
                .password(saveRequest.getPassword())
                .name(saveRequest.getName())
                .birthDate(saveRequest.getBirthDate())
                .role(UserRole.USER)
                .build()).getId();
    }
}
