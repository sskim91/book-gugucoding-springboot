package org.zerock.club.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.club.security.dto.ClubAuthMemberDTO;

/**
 * @author sskim
 */
@Controller
@Slf4j
@RequestMapping("/sample/")
public class SampleController {

    @GetMapping("/all")
    public void exAll() {
        log.info("exAll..........");
    }

//    @GetMapping("/member")
//    public void exMember(){
//        log.info("exMember..........");
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public void exAdmin() {
        log.info("exAdmin..........");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/member")
    public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember) {

        log.info("exMember..........");

        log.info("-------------------------------");
        log.info("clubAuthMember = {}", clubAuthMember);
    }

    @PreAuthorize("#clubAuthMember != null && #clubAuthMember.username eq \"user95@zerock.org\"")
    @GetMapping("/exOnly")
    public String exMemberOnly(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember) {

        log.info("exMemberOnly.............");
        log.info("clubAuthMember = {}", clubAuthMember);

        return "/sample/admin";
    }
}
