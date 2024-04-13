package shop.mtcoding.blog.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User,Integer> {

    // 로그인용
    Optional<User> findByUsernameAndPassword(@Param("username") String username,
                                             @Param("password") String password);

    // 유저네임 중복체크용
    Optional<User> findByUsername(@Param("username") String username);
}
