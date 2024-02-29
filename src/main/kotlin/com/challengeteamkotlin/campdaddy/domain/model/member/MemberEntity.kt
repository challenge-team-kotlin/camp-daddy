package com.challengeteamkotlin.campdaddy.domain.model.member

import com.challengeteamkotlin.campdaddy.common.entity.BaseEntity
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
    var phoneNumber: String

) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long? = null

    @OneToMany(mappedBy = "memberId", cascade = [CascadeType.ALL] , orphanRemoval = true)
    private val regionEntity: List<RegionEntity> = mutableListOf()

}
