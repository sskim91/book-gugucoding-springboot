package org.zerock.board.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.ReplyDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sskim
 */
@SpringBootTest
@Slf4j
class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;

    @Test
    void testGetList() throws Exception {
        Long bno = 100L;
        List<ReplyDTO> replyDTOList = replyService.getList(bno);
        for (ReplyDTO replyDTO : replyDTOList) {
            System.out.println("replyDTO = " + replyDTO);
        }
    }
}