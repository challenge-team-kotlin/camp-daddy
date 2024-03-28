package com.challengeteamkotlin.campdaddy.domain.model.member

import com.challengeteamkotlin.campdaddy.common.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "social_members")
@SQLDelete(sql = "UPDATE social_members SET is_deleted = true WHERE social_member_id = ?")
@SQLRestriction("is_deleted = false")
class SocialMemberEntity(
    @Column(name = "email", nullable = false, unique = true)
    val email: String,

    @Enumerated(EnumType.STRING)
    val provider: OAuth2Provider,

    @Column(name = "provider_id")
    val providerId: String,

) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_member_id")
    var id: Long? = null
}