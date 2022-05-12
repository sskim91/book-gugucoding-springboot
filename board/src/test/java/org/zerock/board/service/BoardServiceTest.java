package org.zerock.board.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sskim
 */
@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    void testRegister() throws Exception {
        BoardDTO dto = BoardDTO.builder()
                .title("Test.")
                .content("Test...")
                .writerEmail("user55@aaa.com")
                .build();

        Long bno = boardService.register(dto);
    }

    @Test
    void testList() throws Exception {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);
        for (BoardDTO boardDTO : result.getDtoList()) {
            System.out.println("boardDTO = " + boardDTO);
        }
    }

    @Test
    @DisplayName("게시글 조회 테스트")
    void testGet() throws Exception {
        Long bno = 100L;
        BoardDTO boardDTO = boardService.get(bno);
        System.out.println("boardDTO = " + boardDTO);
    }

    @Test
    @DisplayName("삭제 테스트")
    void testRemove() throws Exception {
        Long bno = 2L;
        boardService.removeWithReplies(bno);
    }

    @Test
    @DisplayName("수정 테스트")
    void testModify() throws Exception {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(3L)
                .title("제목 변경")
                .content("내용 변경")
                .build();
        boardService.modify(boardDTO);
    }
}