package com.example.myblog.board;

import com.example.myblog.core.errors.Exception404;
import com.example.myblog.user.User;
import com.example.myblog.user.UserResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService bs;

    // 메인화면
    @GetMapping("/")
    public String list(@RequestParam(value = "page", defaultValue = "0") Integer page, Model model) {
        // 사용자가 ?page=1을 쿼리스트링으로 보내면, 서버 인덱스 처리에 따라 데이터 조회
        BoardResponse.PageDTO pageDTO = bs.showBoard(page);
        model.addAttribute("pageDTO", pageDTO);
        return "board/list";
    }

    // 상세 보기
    @GetMapping("/board/{id}")
    public String detailPage(@PathVariable("id") Integer id, Model model) {
        BoardResponse.DetailDTO detailDTO = bs.showDetail(id);
        model.addAttribute("board",detailDTO);

        return "board/detail";
    }

    // 글쓰기 화면 요청
    @GetMapping("/board/save-form")
    public String saveFormPage() {

        return "board/save-form";
    }

    @PostMapping("/board/save")
    // 글쓰기 기능 요청
    public String saveProc(BoardRequest.SaveDTO saveDTO, HttpSession session) {
        UserResponse.SessionDTO sessionUser = (UserResponse.SessionDTO) session.getAttribute("sessionUser");
        saveDTO.validate();
        bs.saveBoard(saveDTO,sessionUser);

        return "redirect:/";
    }

    @GetMapping("/board/{id}/update-form")
    public String updateFormPage(@PathVariable("id") Integer id, HttpSession session, Model model) {

        UserResponse.SessionDTO sessionUser = (UserResponse.SessionDTO) session.getAttribute("sessionUser");
        BoardResponse.DetailDTO detailDTO = bs.authorize(id,sessionUser);
        model.addAttribute("board",detailDTO);
        return "board/update-form";
    }

    @PostMapping("/board/{id}/update")
    public String updateProc(@PathVariable("id")Integer id,
                             BoardRequest.UpdateDTO updateDTO) {
        updateDTO.validate();
        bs.updateBoard(id,updateDTO);

        return "redirect:/board/"+id;
    }

    @PostMapping("board/{id}/delete")
    public String deleteProc(@PathVariable("id") Integer id, HttpSession session) {
        UserResponse.SessionDTO sessionUser = (UserResponse.SessionDTO) session.getAttribute("sessionUser");
        bs.authorize(id, sessionUser);
        bs.deleteBoard(id);

        return "redirect:/";
    }
}
