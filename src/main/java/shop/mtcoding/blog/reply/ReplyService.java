package shop.mtcoding.blog.reply;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.board.BoardJPARepository;
import shop.mtcoding.blog.user.User;
import shop.mtcoding.blog.board.Board;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyJPARepository replyJPARepository;
    private final BoardJPARepository boardJPARepository;

    @Transactional
    public void save(Integer boardId, User user, ReplyRequest.SaveDTO reqDTO) {
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("없는 게시글에 댓글을 작성할 수 없습니다."));

        Reply reply = reqDTO.toEntity(user, board);

        replyJPARepository.save(reply);
    }

    @Transactional
    public void delete(Integer id, User sessionUser) {
        Reply reply = replyJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("없는 댓글을 삭제할 수 없습니다."));

        if (sessionUser.getId() != reply.getUser().getId()) {
            throw new Exception403("댓글을 삭제할 권한이 없습니다.");
        }

        replyJPARepository.deleteById(id);
    }
}
