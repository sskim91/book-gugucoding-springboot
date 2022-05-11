package org.zerock.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author sskim
 */
@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {   //페이지 요청 처리 DTO

    private int page;
    private int size;
    private String type;
    private String keyword;

    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }

    //이 클래스의 진짜 목적은 JPA쪽에서 사용하는 Pageable타입의 객체를 생성하는 것.
    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }
}
