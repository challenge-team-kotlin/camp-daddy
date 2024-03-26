package com.challengeteamkotlin.campdaddy.common.auth

import com.challengeteamkotlin.campdaddy.application.auth.exception.AuthErrorCode
import com.challengeteamkotlin.campdaddy.common.exception.UnAuthorizationException
import com.challengeteamkotlin.campdaddy.common.security.UserPrincipal

fun <T> isAuthorized(
    id: Long,
    userPrincipal: UserPrincipal,
    func: () -> T
): T {
    if (id == userPrincipal.id) {
        return func.invoke()
    } else {
        throw UnAuthorizationException(AuthErrorCode.ACCESS_DENIED)
    }
}