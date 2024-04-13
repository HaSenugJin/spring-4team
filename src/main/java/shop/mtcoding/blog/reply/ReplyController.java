package shop.mtcoding.blog.reply;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.mtcoding.blog.user.User;

@RequiredArgsConstructor
@Controller
public class ReplyController {

    private final ReplyService replyService ;
    private final HttpSession session;

    @PostMapping("/reply/save")
    public String save(ReplyRequest.SaveDTO reqDTO, @RequestParam("boardId") Integer boardId) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.save(boardId, sessionUser, reqDTO);

        return "redirect:/board/" + reqDTO.getBoardId();
    }

    @PostMapping("/reply/{id}/delete")
    public String delete(@PathVariable Integer id, @RequestParam("boardId") Integer boardId) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.delete(id, sessionUser);

        return "redirect:/board/" + boardId;
    }
}
