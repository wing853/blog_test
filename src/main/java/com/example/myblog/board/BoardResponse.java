package com.example.myblog.board;

import com.example.myblog.util.MyDateUtil;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardResponse {

    @Data
    public static class PageDTO {
        private List<ListDTO> boards; // 게시글 목록
        private Integer prevPage;      // 이전 페이지 번호
        private Integer nextPage;      // 다음 페이지 번호
        private boolean first;         // 첫 페이지 여부
        private boolean last;          // 마지막 페이지 여부
        private List<Integer> pageNumbers; // 페이지 번호 리스트 (1, 2, 3...)

        public PageDTO(Page<Board> boardPage) {
            this.boards = boardPage.getContent().stream().map(ListDTO::new).collect(Collectors.toList());
            this.first = boardPage.isFirst();
            this.last = boardPage.isLast();
            this.prevPage = boardPage.getNumber() - 1;
            this.nextPage = boardPage.getNumber() + 1;
            // 페이지 번호 리스트 로직 (예: 0~4)
            this.pageNumbers = IntStream.range(0, boardPage.getTotalPages())
                    .boxed().collect(Collectors.toList());
        }
    }

    @Data
    public static class ListDTO {
        private Integer id;
        private String title;
        private String nickname;
        private String createdAt;

        public ListDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();

            if (board.getUser() != null) {
                this.nickname = board.getUser().getNickname();
            }

            if (board.getTime() != null) {
                this.createdAt = MyDateUtil.timestampFormat(board.getCreatedAt());
            }
        }
    }

    @Data
    public static class DetailDTO {
        private Integer id;
        private String title;
        private String nickname;
        private String content;
        private Integer userId;

        public DetailDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();

            if(board.getUser() != null) {
                this.nickname = board.getUser().getNickname();
                this.userId = board.getUser().getId();
            }

        }

    }
}
