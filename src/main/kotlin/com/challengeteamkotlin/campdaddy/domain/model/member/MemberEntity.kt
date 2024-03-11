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

    @Column(name = "nickname", nullable = false, unique = true)
    var nickname: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "phone_number", nullable = false, unique = true)
    var phoneNumber: String,

    @Enumerated(EnumType.STRING)
    val provider: MemberProvider,

    @Column(name = "provider_id")
    val providerId: String,

) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    var id: Long? = null

    fun toUpdate(request: UpdateProfileRequest) {
        nickname = request.nickname
    }

    companion object {
        fun ofKakao(id: Long, nickname: String, email: String, phoneNumber: String, name: String): MemberEntity {
            return MemberEntity(
                provider = MemberProvider.KAKAO,
                providerId = id.toString(),
                nickname = nickname,
                email = email,
                name = name,
                phoneNumber = phoneNumber
            )
        }
    }
}


