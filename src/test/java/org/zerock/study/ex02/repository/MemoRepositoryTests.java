package org.zerock.study.ex02.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.study.ex02.entity.Memo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Created by sskim on 2021/04/20
 * Github : http://github.com/sskim91
 */
@SpringBootTest
class MemoRepositoryTests {

    @Autowired
    private MemoRepository memoRepository;

    @Test
    void testClass() throws Exception {
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    @DisplayName("더미데이터 CREATE")
    void testInsertDummies() {
        IntStream.rangeClosed(1, 100)
                .forEach(i -> {
                    Memo memo = Memo.builder()
                            .memoText("Sample..." + i)
                            .build();
                    memoRepository.save(memo);
                });
    }

    @Test
    @DisplayName("테이블 값 조회1")
    void testSelect() throws Exception {

        //데이터베이스에 존재하는 mno
        Long mno = 100L;
        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("====================");
        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println("memo = " + memo);
        }
    }

    @Test
    @Transactional
    @DisplayName("테이블 값 조회2")
    void testSelect2() throws Exception {

        //데이터베이스에 존재하는 mno
        Long mno = 100L;
        Memo memo = memoRepository.getOne(mno);

        System.out.println("====================");
        System.out.println("memo = " + memo);
    }

    @Test
    @DisplayName("수정 작업 테스트")
    void testUpdate() throws Exception {
        Memo memo = Memo.builder()
                        .mno(100L)
                        .memoText("Update Text")
                        .build();
        System.out.println("memoRepository = " + memoRepository.save(memo));
    }

    @Test
    @DisplayName("삭제 작업 테스트")
    void testDelete() throws Exception {
        Long mno = 100L;
        memoRepository.deleteById(mno);
    }

    @Test
    @DisplayName("페이징 처리")
    void testPageDefault() throws Exception {
        //1페이지 10개
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println("result = " + result);

        System.out.println("==================================");

        System.out.println("Total Pages: "+ result.getTotalPages());    //총 몇 페이지
        System.out.println("Total Count: "+ result.getTotalElements()); //전체 개수
        System.out.println("Page Number: " + result.getNumber());   //현재 페이지 번호 0부터 시작
        System.out.println("Page Size: " + result.getSize());   //페이지당 데이터 개수
        System.out.println("has next page?: " + result.hasNext());  //다음 페이지 존재 여부
        System.out.println("first page?: " + result.isFirst()); //시작 페이지(0) 여부
    }

    @Test
    @DisplayName("페이징 정렬 조건")
    void testSort() throws Exception {
        Sort sort1 = Sort.by("mno").descending();
//        PageRequest pageable = PageRequest.of(0, 10, sort1);
        //정렬조건을 여러개 줄 수 있음
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);    //and를 이용한 연결
        PageRequest pageable = PageRequest.of(0, 10, sortAll);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(System.out::println);
    }

    @Test
    @DisplayName("쿼리 메서드 테스트")
    void testQueryMethods() throws Exception {
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        for (Memo memo : list) {
            System.out.println(memo);
        }
    }

    @Test
    @DisplayName("쿼리 메서드와 Pageable의 결합")
    void testQueryMethodWithPageable() throws Exception {
        PageRequest pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);

        result.get().forEach(System.out::println);
    }

    @Test
    @Commit
    @Transactional
    @DisplayName("deleteBy로 시작하는 삭제 처리")
    void testDeleteQueryMethods() throws Exception {
        memoRepository.deleteMemoByMnoLessThan(10L);
    }

}