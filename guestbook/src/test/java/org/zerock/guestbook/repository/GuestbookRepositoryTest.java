package org.zerock.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.zerock.guestbook.entity.QGuestbook.*;

/**
 * @author sskim
 */
@SpringBootTest
class GuestbookRepositoryTest {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    void insertDummies() throws Exception {
        IntStream.rangeClosed(1, 300).forEach(i -> {

            Guestbook guestbook = Guestbook.builder()
                    .title("Title...." + i)
                    .content("Content...." + i)
                    .writer("user" + (i % 10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    @Test
    void updateTest() throws Exception {
        Optional<Guestbook> result = guestbookRepository.findById(300L);
        if (result.isPresent()) {
            Guestbook guestbook = result.get();

            guestbook.changeTitle("Changed Title....");
            guestbook.changeContent("Changed Content....");

            guestbookRepository.save(guestbook);
        }
    }

    @Test
    @DisplayName("단일 항목 검색 테스트")
    void testQuery1() throws Exception {
        PageRequest pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        String keyword = "1";

        //BooleanBuilder는 where 문에 들어가는 조건들을 넣어주는 컨테이너
        BooleanBuilder builder = new BooleanBuilder();

        //원하는 조건은 필드 값과 같이 결합해서 생성
        BooleanExpression expression = guestbook.title.contains(keyword);

        //만들어진 조건은 where문에 and나 or같은 키워드와 결합
        builder.and(expression);

        //BooleanBuilder는 GuestbookRepository에 추가된 QuerydslPredicateExcutor의 인터페이스의 findAll을 사용할수있다.
        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(System.out::println);
    }

    @Test
    @DisplayName("다중 항목 검색 테스트")
    void testQuery2() throws Exception {
        PageRequest pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        String keyword = "1";

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        BooleanExpression exTitle = guestbook.title.contains(keyword);

        BooleanExpression exContent = guestbook.content.contains(keyword);

        //BooleanExpression을 결합
        BooleanExpression exAll = exTitle.or(exContent);

        // gno가 0보다 크다는 조건 추가
        booleanBuilder.and(exAll)
                .and(guestbook.gno.gt(0L));

        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder, pageable);

        result.stream().forEach(System.out::println);
    }
}