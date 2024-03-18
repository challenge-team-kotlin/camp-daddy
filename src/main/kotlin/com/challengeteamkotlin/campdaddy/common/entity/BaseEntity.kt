package com.challengeteamkotlin.campdaddy.common.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.SQLRestriction
import org.springframework.data.jpa.domain.support.AuditingEntityListener
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity() : BaseTimeEntity() {
    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false
        protected set
}