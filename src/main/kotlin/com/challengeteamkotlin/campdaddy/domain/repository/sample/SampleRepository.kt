package com.challengeteamkotlin.campdaddy.domain.repository.sample

import com.challengeteamkotlin.campdaddy.domain.model.sample.SampleEntity

interface SampleRepository {
    fun save():SampleEntity

}