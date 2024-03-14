package com.challengeteamkotlin.campdaddy.presentation.member.context.repository

import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.member.MemberJpaRepository
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.member.MemberRepositoryImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MemberConfig {

    @Bean
    fun memberRepository(memberJpaRepository: MemberJpaRepository): MemberRepository {
        return MemberRepositoryImpl(memberJpaRepository)
    }
}