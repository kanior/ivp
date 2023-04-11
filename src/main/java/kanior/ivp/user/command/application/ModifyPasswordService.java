package kanior.ivp.user.command.application;

import kanior.ivp.user.command.domain.User;
import kanior.ivp.user.command.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ModifyPasswordService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public boolean isOldPasswordValid(String loginId, String oldPassword) {
        User user = userRepository.findByLoginId(loginId).orElse(null);
        if (user != null && user.isPasswordCorrect(oldPassword)) {
            return true;
        }
        return false;
    }

    @Transactional
    public Long modifyPassword(ModifyPasswordRequest modifyRequest) {
        return userRepository.findByLoginId(modifyRequest.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 정보가 존재하지 않습니다. loginId=" + modifyRequest.getLoginId()))
                .changePassword(modifyRequest.getOldPassword(), modifyRequest.getNewPassword())
                .getId();
    }
}
