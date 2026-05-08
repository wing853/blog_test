package com.example.myblog.board;

import com.example.myblog.core.errors.Exception403;
import com.example.myblog.core.errors.Exception404;
import com.example.myblog.user.User;
import com.example.myblog.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    // 게시글 목록
    private final BoardRepository br;

    public BoardResponse.PageDTO showBoard(Integer page) {
        // 한 페이지에 5개씩, ID 내림차순 정렬
        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id"));
        Page<Board> boardPage = br.findAll(pageable);

        return new BoardResponse.PageDTO(boardPage);
    }

    // 게시글 상세보기
    public BoardResponse.DetailDTO showDetail(Integer id) {
        Board boardEntity = br.findByIdJoinUser(id).orElseThrow(
                () -> new Exception404("게시글 찾을 수 없음")
        );

        return new BoardResponse.DetailDTO(boardEntity);
    }

    @Transactional
    // 게시글 작성하기
    public void saveBoard(BoardRequest.SaveDTO saveDTO, UserResponse.SessionDTO sessionUser) {

        User user = User.builder().id(sessionUser.getId()).build();
        Board board = saveDTO.toEntity(user);

        br.save(board);
    }

    // 인가처리
    public BoardResponse.DetailDTO authorize (Integer id, UserResponse.SessionDTO sessionUser) {
        BoardResponse.DetailDTO detailDTO = showDetail(id);
        if(!detailDTO.getUserId().equals(sessionUser.getId())){
            throw new Exception403("권한 없음");
        }
        return detailDTO;
    }

    @Transactional
    // 게시글 수정하기
    public void updateBoard(Integer id,BoardRequest.UpdateDTO updateDTO) {
        Board boardEntity = br.findByIdJoinUser(id).orElseThrow(
                () -> new Exception404("게시글 찾을 수 없음")
        );

        boardEntity.update(updateDTO);
    }

    // 게시글 삭제하기
    @Transactional
    public void deleteBoard(Integer id) {
        br.findByIdJoinUser(id).orElseThrow(
                () -> new Exception404("게시글 찾을 수 없음")
        );
        br.deleteById(id);
    }
}
