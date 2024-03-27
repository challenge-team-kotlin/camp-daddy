package com.challengeteamkotlin.campdaddy.common.auth

import com.challengeteamkotlin.campdaddy.application.auth.exception.AuthErrorCode
import com.challengeteamkotlin.campdaddy.common.exception.UnAuthorizationException
import com.challengeteamkotlin.campdaddy.common.security.UserPrincipal

fun <T> isAuthorized(
    targetId: Long,
    userPrincipal: UserPrincipal,
    func: () -> T
): T {
    if (targetId == userPrincipal.id) {
        return func.invoke()
    } else {
        throw UnAuthorizationException(AuthErrorCode.ACCESS_DENIED)
    }
}

fun <T> isAuthorized(
    id: Long,
    sellerId: Long,
    buyerId: Long,
    func: () -> T
): T {
    if (id == sellerId || id == buyerId) {
        return func.invoke()
    } else {
        throw UnAuthorizationException(AuthErrorCode.ACCESS_DENIED)
    }
}