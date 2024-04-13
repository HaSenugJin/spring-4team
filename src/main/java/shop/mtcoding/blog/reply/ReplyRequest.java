package shop.mtcoding.blog.reply;
import shop.mtcoding.blog.board.Board;

import lombok.Data;
import shop.mtcoding.blog.user.User;

public class ReplyRequest {

    @Data
    public static class SaveDTO {
        private Integer boardId;
        private String comment;

        public Reply toEntity(User user, Board board) {
            return Reply.builder()
                    .comment(comment)
                    .board(board)
                    .user(user)
                    .build();
        }
    }
}
