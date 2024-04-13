package shop.mtcoding.blog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception400;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog._core.errors.exception.Exception404;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserJPARepository userJPARepository ;

    // 로그인
    public User login(UserRequest.LoginDTO reqDTO) {
        User sessionUser = userJPARepository.findByUsernameAndPassword(
                reqDTO.getUsername(), reqDTO.getPassword())
                .orElseThrow(() -> new Exception401("인증되지 않았습니다."));

        return sessionUser;
    }

    @Transactional
    public User join(UserRequest.JoinDTO reqDTO) {
        Optional<User> userOP = userJPARepository.findByUsername(reqDTO.getUsername());

        if(userOP.isPresent()) {
            throw new Exception400("중복된 유저네임 입니다.");
        }

        User sessionUser = userJPARepository.save(reqDTO.toEntity());
        return sessionUser;
    }

    public User updateForm(Integer sessionUserId) {
        return userJPARepository.findById(sessionUserId)
                .orElseThrow(() -> new Exception404("회원정보를 찾을 수 없습니다."));
    }

    @Transactional
    public User update(Integer sessionUserId, UserRequest.UpdateDTO reqDTO) {
        User user = userJPARepository.findById(sessionUserId).orElseThrow(()
                -> new Exception404("회원정보를 찾을 수 없습니다."));
        user.setPassword(reqDTO.getPassword());
        user.setEmail(reqDTO.getEmail());
        userJPARepository.save(user);

        return user;
    }
}
