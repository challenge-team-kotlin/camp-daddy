package com.challengeteamkotlin.campdaddy.domain.model.member

import com.challengeteamkotlin.campdaddy.common.entity.BaseEntity
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.member.MemberJpaRepository
import com.challengeteamkotlin.campdaddy.presentation.member.dto.request.UpdateProfileRequest
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.springframework.security.crypto.password.PasswordEncoder

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

    @Column(name = "password", nullable = false)
    var password: String,

    @Enumerated(EnumType.STRING)
    var role: MemberRole

) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long? = null

    fun toUpdate(request: UpdateProfileRequest) {
        nickname = request.nickname
    }
}

fun checkedEmailOrNicknameExists(email: String, nickname: String, memberJpaRepository: MemberJpaRepository) {
    if (memberJpaRepository.existsByEmail(email)) {
        TODO("Exception")
    }

    if (memberJpaRepository.existsByNickname(nickname)) {
        TODO("Exception")
    }
}

fun checkedLoginPassword(password: String, inputPassword: String, passwordEncoder: PasswordEncoder) {
    if (!passwordEncoder.matches(inputPassword, password)) {
        TODO("Exception")
    }
}