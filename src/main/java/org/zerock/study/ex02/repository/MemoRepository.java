package org.zerock.study.ex02.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.study.ex02.entity.Memo;

/**
 * Created by sskim on 2021/04/20
 * Github : http://github.com/sskim91
 */
public interface MemoRepository extends JpaRepository<Memo, Long> {

}
