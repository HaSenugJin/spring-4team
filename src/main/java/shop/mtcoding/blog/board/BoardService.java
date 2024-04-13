package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.reply.Reply;
import shop.mtcoding.blog.reply.ReplyJPARepository;
import shop.mtcoding.blog.user.User;
import shop.mtcoding.blog.user.UserRequest;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardJPARepository boardJPARepository;
    private final ReplyJPARepository replyJPARepository;

    public List<Board> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        return boardJPARepository.findAll(sort);
    }

    public BoardResponse.DetailDTO findByBoardAndUser(Integer boardId, User sessionUser) {
        Board board = boardJPARepository.findByIdJoinUser(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));

        List<Reply> replyList = replyJPARepository.findByBoardIdJoinUser(boardId);

        return BoardResponse.DetailDTO.builder()
                .board(board)
                .user(sessionUser)
                .replyList(replyList)
                .build();
    }

    @Transactional
    public void save(BoardRequest.SaveDTO reqDTO, User sessionUser) {
        boardJPARepository.save(reqDTO.toEntity(sessionUser));
    }

    public Board updateForm(Integer id) {
        Board board = boardJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다"));


        return board;
    }

    @Transactional
    public Board update(Integer id, BoardRequest.UpdateDTO reqDTO, User sessionUser) {
        Board board = boardJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));

        if (sessionUser.getId() != board.getUser().getId()) {
            throw new Exception403("게시글을 수정할 권한이 없습니다.");
        }

        board.setTitle(reqDTO.getTitle());
        board.setContent(reqDTO.getContent());

        return board;
    }

    @Transactional
    public void delete(Integer id, User sessionUser) {
        Board board = boardJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));

        if (sessionUser.getId() != board.getUser().getId()) {
            throw new Exception403("게시글을 삭제할 권한이 없습니다.");
        }

        boardJPARepository.deleteById(id);
    }
}
