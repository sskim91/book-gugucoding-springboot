package org.zerock.bimovie.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.bimovie.entity.Movie;
import org.zerock.bimovie.entity.Poster;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author sskim
 */
@SpringBootTest
@Slf4j
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void testInsert() throws Exception {

        Movie movie = Movie.builder().title("극한직업").build();

        movie.addPoster(Poster.builder().fname("극한직업포스터1.jpg").build());
        movie.addPoster(Poster.builder().fname("극한직업포스터2.jpg").build());

        Movie result = movieRepository.save(movie);
        log.info("movie.getMno() = {}", movie.getMno());

        assertThat(result.getPosterList().size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @Commit
    @DisplayName("Movie에 Poster를 추가하는 경우")
    void testAddPoster() throws Exception {
        Movie movie = movieRepository.getById(1L);

        //새로운 Poster 객체
        movie.addPoster(Poster.builder().fname("극한직업포스터3.jpg").build());

        movieRepository.save(movie);
    }

    @Test
    void testRemovePoster() throws Exception {
        Movie movie = movieRepository.getOne(1L);
        movie.removePoster(2L);
        movieRepository.save(movie);
    }

    @Test
    void insertMovies() throws Exception {
        IntStream.rangeClosed(10, 100).forEach(i -> {
            Movie movie = Movie.builder().title("세계명작" + i).build();

            movie.addPoster(Poster.builder().fname("세계명작" + i + "포스터1.jpg").build());
            movie.addPoster(Poster.builder().fname("세계명작" + i + "포스터2.jpg").build());

            movieRepository.save(movie);
        });
    }

    @Test
    @DisplayName("N+1문제")
    void testPaging1() throws Exception {
        PageRequest pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Movie> result = movieRepository.findAll(pageable);

        result.getContent().forEach(m -> {
            log.info("m.getMno() = {}", m.getMno());
            log.info("m.getTitle() = {}", m.getTitle());
            log.info("m.getPosterList().size() = {}", m.getPosterList().size());
        });
    }

    @Test
    void testPaging2() throws Exception {

        //문제가 발생했다 limit가 없다.
        PageRequest pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Movie> result = movieRepository.findAll2(pageable);

         result.getContent().forEach(m -> {
            log.info("m.getMno() = {}", m.getMno());
            log.info("m.getTitle() = {}", m.getTitle());
            log.info("m.getPosterList().size() = {}", m.getPosterList().size());
        });
    }

    @Transactional
    @Test
    void testPaging3() throws Exception {

        PageRequest pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Object[]> result = movieRepository.findAll3(pageable);

        result.getContent().forEach(arr ->{
            log.info(Arrays.toString(arr));
        });
    }
}