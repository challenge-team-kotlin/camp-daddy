package com.challengeteamkotlin.campdaddy.domain.model.member

import com.challengeteamkotlin.campdaddy.common.entity.BaseEntity
import com.challengeteamkotlin.campdaddy.presentation.member.dto.request.UpdateProfileRequest
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete

@Entity
@Table(name = "members")
@SQLDelete(sql = "UPDATE members SET is_deleted = true WHERE id = ?")
class MemberEntity(
    @Column(name = "email", nullable = false, unique = true)
    val email: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Enumerated(EnumType.STRING)
    val provider: OAuth2Provider,

    @Column(name = "provider_id")
    val providerId: String,


    ) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    var id: Long? = null

    @Column(name = "nickname", nullable = true, unique = true)
    var nickname: String? = null

    fun toUpdate(request: UpdateProfileRequest) {
        nickname = request.nickname ?: null
    }

    companion object {
        fun ofKakao(id: String, email: String, name: String): MemberEntity {
            return MemberEntity(
                provider = OAuth2Provider.KAKAO,
                providerId = id,
                email = email,
                name = name
            )
        }

        fun ofNaver(id: String, email: String, name: String): MemberEntity {
            return MemberEntity(
                provider = OAuth2Provider.NAVER,
                providerId = id,
                email = email,
                name = name
            )
        }

        fun ofGoogle(id: String, email: String, name: String): MemberEntity {
            return MemberEntity(
                provider = OAuth2Provider.GOOGLE,
                providerId = id,
                email = email,
                name = name
            )
        }
    }
}


