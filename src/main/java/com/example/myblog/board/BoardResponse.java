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
        private List<ListDTO> boards;
        private Integer prevPage;
        private Integer nextPage;
        private boolean first;
        private boolean last;
        private List<PageNumber> pageNumbers; // 숫자 리스트 대신 객체 리스트 사용

        public PageDTO(Page<Board> boardPage) {
            this.boards = boardPage.getContent().stream()
                    .map(ListDTO::new)
                    .collect(Collectors.toList());
            this.first = boardPage.isFirst();
            this.last = boardPage.isLast();
            this.prevPage = boardPage.getNumber() - 1;
            this.nextPage = boardPage.getNumber() + 1;

            // 0부터 시작하는 인덱스를 1부터 시작하는 번호와 매핑
            this.pageNumbers = IntStream.range(0, boardPage.getTotalPages())
                    .mapToObj(i -> new PageNumber(i, i + 1))
                    .collect(Collectors.toList());
        }

        @Data
        public static class PageNumber {
            private Integer index;  // 서버 전송용 (0, 1, 2...)
            private Integer number; // 화면 표시용 (1, 2, 3...)

            public PageNumber(Integer index, Integer number) {
                this.index = index;
                this.number = number;
            }
        }
    }

    @Data
    public static class PageListDTO {
        private Integer id;
        private String title;
        private String nickname;

        public PageListDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.nickname = board.getUser().getNickname();
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
