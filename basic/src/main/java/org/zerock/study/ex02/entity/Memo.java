package org.zerock.study.ex02.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Created by sskim on 2021/04/20
 * Github : http://github.com/sskim91
 */
@Entity
@Table(name = "tbl_memo")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;
}
