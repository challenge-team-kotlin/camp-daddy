package com.challengeteamkotlin.campdaddy.domain.model.member

import jakarta.persistence.*

@Entity
@Table(name = "regions")
class RegionEntity(
    @Column(name = "member_id")
    val memberId:Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sigg_area_id")
    val siggArea: SiggAreaEntity,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    var id:Long?=null
}