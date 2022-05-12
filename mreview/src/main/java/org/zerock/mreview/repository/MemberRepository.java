package org.zerock.mreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mreview.entity.Member;

/**
 * @author sskim
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
}
