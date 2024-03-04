package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.sample

import com.challengeteamkotlin.campdaddy.domain.model.sample.SampleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SampleJpaRepository : JpaRepository<Long,SampleEntity>{
}