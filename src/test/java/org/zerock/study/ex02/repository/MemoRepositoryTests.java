package org.zerock.study.ex02.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.study.ex02.entity.Memo;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

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
}