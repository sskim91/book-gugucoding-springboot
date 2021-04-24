package org.zerock.study.ex03.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by sskim on 2021/04/22
 * Github : http://github.com/sskim91
 */
@Data
@Builder(toBuilder = true)
public class SampleDTO {

    private Long sno;
    private String first;
    private String last;
    private LocalDateTime regTime;
}
