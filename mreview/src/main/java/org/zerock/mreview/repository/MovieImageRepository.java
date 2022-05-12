package org.zerock.mreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mreview.entity.MovieImage;

/**
 * @author sskim
 */
public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {
}
