package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.user.User;
import shop.mtcoding.blog.user.UserRequest;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardJPARepository boardJPARepository;

    public List<Board> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        return boardJPARepository.findAll(sort);
    }

    public Board findByBoardAndUser(Integer boardId, User sessionUser) {
        Board board = boardJPARepository.findByIdJoinUser(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));
        boolean isBoardOwner = false;
        if (sessionUser != null) {
            if(sessionUser.getId() == board.getUser().getId()) {
                isBoardOwner = true;
            }
        }

        board.setBoardOwner(isBoardOwner);

        return board;
    }
}
