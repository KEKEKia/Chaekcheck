package com.cc.auth.domain.member.repository;


import com.cc.auth.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByOauthIdentifier(String oauthIdentifier);

    @Query("SELECT m FROM member m WHERE m.refreshToken = :refreshToken")
    Optional<Member> findByRefreshToken(@Param("refreshToken") String refreshToken);

    @Query("SELECT m FROM member m WHERE m.nickname = :nickname")
    Optional<Member> findByNickname(@Param("nickname") String nickname);

}
