package org.zerock.board.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author sskim
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long bno;
    private String title;
    private String content;
    private String writerEmail; //작성자 이메일(id)
    private String writerName;  //작성자의 이름
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private int replyCount; //해당 게시물의 댓글 수

}
