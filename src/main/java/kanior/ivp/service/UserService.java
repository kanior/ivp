package kanior.ivp.service;

import kanior.ivp.dto.LoginUserInfo;
import kanior.ivp.dto.UserInfoResponse;
import kanior.ivp.dto.ReservationListResponse;
import kanior.ivp.dto.UserSaveRequest;
import kanior.ivp.entity.User;
import kanior.ivp.repository.ReservationRepository;
import kanior.ivp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public boolean isLoginIdDuplicated(String loginId) {
        return !userRepository.findByLoginId(loginId).isEmpty();
    }

    @Transactional
    public Long save(UserSaveRequest form) {
        return userRepository.save(form.toEntity()).getId();
    }

    @Transactional
    public Long modifyPassword(String loginId, String password) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 정보가 존재하지 않습니다. loginId=" + loginId))
                .setPassword(password)
                .getId();
    }

    public LoginUserInfo login(String loginId, String password) {
        return userRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .map(LoginUserInfo::new)
                .orElse(null);
    }

    public UserInfoResponse findInfoByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
                .map(UserInfoResponse::new)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 정보가 존재하지 않습니다. loginId=" + loginId));
    }

    @Transactional
    public void delete(String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 정보가 존재하지 않습니다. loginId=" + loginId));

        userRepository.delete(user);
    }

    public List<ReservationListResponse> findAllReservationByLoginId(String loginId) {
        Long id = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 정보가 존재하지 않습니다. loginId=" + loginId))
                .getId();

        return reservationRepository.findAllByUserId(id)
                .stream().map(ReservationListResponse::new)
                .collect(Collectors.toList());
    }
}
