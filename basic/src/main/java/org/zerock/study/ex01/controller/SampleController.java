package org.zerock.study.ex01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sskim on 2021/04/20
 * Github : http://github.com/sskim91
 */
@RestController
public class SampleController {

    @GetMapping("/hello")
    public String[] hello() {
        return new String[]{"Hello", "World"};
    }
}
