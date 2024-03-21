package com.cc.auth.domain.member.controller;


import com.cc.auth.domain.auth.dto.PrincipalDetails;
import com.cc.auth.domain.member.dto.DeleteMemberResponseDto;
import com.cc.auth.domain.member.dto.UpdateNicknameRequestDto;
import com.cc.auth.domain.member.dto.UpdateNicknameResponseDto;
import com.cc.auth.domain.member.service.MemberService;
import com.cc.auth.global.common.response.EnvelopeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @DeleteMapping("/member")
    public ResponseEntity<EnvelopeResponse<DeleteMemberResponseDto>> delete(@AuthenticationPrincipal PrincipalDetails principalDetails){

        return new  ResponseEntity<>(new EnvelopeResponse<>(HttpStatus.OK.value(), "회원 탈퇴 완료", memberService.deleteMember(principalDetails.getMember())), HttpStatus.CREATED);
    }

    @PutMapping("/member/nickname")
    public ResponseEntity<EnvelopeResponse<UpdateNicknameResponseDto>> updateNickname(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody UpdateNicknameRequestDto updateNicknameRequestDto){

        return new ResponseEntity<>(new EnvelopeResponse<UpdateNicknameResponseDto>(HttpStatus.OK.value(), "닉네임 수정 성공",memberService.updateNickname(principalDetails.getMember(), updateNicknameRequestDto.getNickname())), HttpStatus.OK);
    }
}