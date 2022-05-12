package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

import java.util.List;

/**
 * @author sskim
 */
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying  //JPQL을 이용해서 update, delete를 실행하기 위해서는 해당 어노테이션 추가필요
    @Query("delete from Reply r where r.board.bno = :bno")
    void deleteByBno(@Param("bno") Long bno);   //board 삭제시에 댓글들 삭제

    List<Reply> getRepliesByBoardOrderByRno(Board board);   //게시물로 댓글 목록 가져오기
}
