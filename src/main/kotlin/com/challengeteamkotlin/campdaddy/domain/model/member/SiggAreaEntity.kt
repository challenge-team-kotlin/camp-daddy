package com.challengeteamkotlin.campdaddy.domain.model.member

import jakarta.persistence.*


@Entity
@Table(name = "sigg_areas")
class SiggAreaEntity(
    @Column(name = "name")
    val name: String,

    @Column(name = "adm_code")
    val admCode: String,

    @ManyToOne
    @JoinColumn(name = "sido_area_id")
    val sidoArea: SidoAreaEntity
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sigg_area_id")
    var id:Long?=null

}