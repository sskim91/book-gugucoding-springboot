package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.board.entity.Member;

/**
 * @author sskim
 */
public interface MemberRepository extends JpaRepository<Member, String> {
}
