package com.example.myblog.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    // 게시글 단건 조회
    @Query("""
                select b from Board b join fetch b.user where b.id = :id
            """)
    Optional<Board> findByIdJoinUser(@Param("id") Integer id);

    // 게시글 전체 조회
    @Query("""
                select b from Board b join fetch b.user order by b.id desc
            """)
    List<Board> findAllJoinUser();
}
