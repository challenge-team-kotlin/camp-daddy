package com.challengeteamkotlin.campdaddy.domain.model.member

import jakarta.persistence.*

@Entity
@Table(name = "sido_areas")
class SidoAreaEntity(
    @Column(name = "name")
    val name: String,

    @Column(name = "adm_code")
    val admCode: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sido_area_id")
    var id: Long? = null

}