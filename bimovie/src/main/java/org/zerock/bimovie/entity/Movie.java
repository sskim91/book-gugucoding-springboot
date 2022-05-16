package org.zerock.bimovie.entity;

import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author sskim
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "tbl_movie")
public class Movie extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    private String title;

    @Builder.Default
    @OneToMany(mappedBy = "movie"
            , fetch = FetchType.LAZY
            , cascade = CascadeType.ALL
            , orphanRemoval = true)
    private List<Poster> posterList = new ArrayList<>();

    public void addPoster(Poster poster) {
        poster.setIdx(this.posterList.size());
        poster.setMovie(this);
        posterList.add(poster);
    }

    public void removePoster(Long ino) {
        Optional<Poster> result = posterList.stream().filter(p -> p.getIno() == ino).findFirst();

        result.ifPresent(p -> {
            p.setMovie(null);
            posterList.remove(p);
        });
        //포스터 번호 변경
        changeIdx();
    }

    private void changeIdx() {
        for (int i = 0; i < posterList.size(); i++) {
            posterList.get(i).setIdx(i);
        }
    }
}
