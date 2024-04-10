package shop.mtcoding.blog.board;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardJPARepository extends JpaRepository<Board,Integer> {
}
