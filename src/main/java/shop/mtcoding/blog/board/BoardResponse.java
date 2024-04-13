package shop.mtcoding.blog.board;

import lombok.Builder;
import lombok.Data;
import shop.mtcoding.blog.reply.Reply;
import shop.mtcoding.blog.user.User;

import java.util.ArrayList;
import java.util.List;

public class BoardResponse {
    @Data
    public static class DetailDTO {
        private Integer id;
        private String title;
        private String content;
        private Integer userId;
        private String username;
        private boolean isBoardOwner;
        private List<ReplyDTO> replies = new ArrayList<>();

        @Builder
        public DetailDTO(Board board, User user, List<Reply> replyList) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.userId = board.getUser().getId();
            this.username = board.getUser().getUsername();
            this.isBoardOwner = false;
            if (user != null) {
                if (user.getId() == userId) isBoardOwner = true;
            }
            this.replies = replyList.stream().map(reply -> new ReplyDTO(reply, user)).toList();
        }

        @Data
        public class ReplyDTO {
            private Integer id;
            private String content;
            private Integer userId;
            private Integer boardId;
            private String username;
            private boolean isReplyOwner;

            public ReplyDTO(Reply reply, User user) {
                this.id = reply.getId();
                this.content = reply.getComment();
                this.userId = reply.getUser().getId();
                this.username = reply.getUser().getUsername();
                this.boardId = reply.getBoard().getId();
                this.isReplyOwner = false;
                if (user != null) {
                    if (user.getId() == userId) isReplyOwner = true;
                }
            }
        }
    }
}
