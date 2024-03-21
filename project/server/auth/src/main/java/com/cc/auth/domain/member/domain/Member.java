package com.cc.auth.domain.member.domain;

import com.cc.auth.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@Entity(name= "member")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE member SET activated = false WHERE member_id = ?")
@Where(clause = "activated = true")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private int id;

    @Column
    private String nickname;

    @Enumerated(EnumType.STRING)
    private OauthType oauthType;

    @Column
    private String oauthIdentifier;

    @Column
    private String refreshToken;

    @Column
    private boolean activated;

    public void setRefreshToken(String refreshToken){
        this.refreshToken=refreshToken;
    }

    public void deleteRefreshToken() {
        this.refreshToken = null;
    }

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }
}