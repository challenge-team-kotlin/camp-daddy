package com.challengeteamkotlin.campdaddy.infrastructure.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.*

@Component
class JwtPlugin(
    private val jwtProperties: JwtProperties
) {

    fun validateToken(jwt: String): Result<Jws<Claims>> {
        return kotlin.runCatching {
            val key = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray(StandardCharsets.UTF_8))
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt)

        }
    }

    fun generateAccessToken(subject: String, email: String, role: String, response: HttpServletResponse): String {
        val token = generateToken(subject, email, role, Duration.ofHours(jwtProperties.accessTokenExpirationHour))
        addTokenToCookie(token, response)
        return token
    }

    private fun generateToken(subject: String, email: String, role: String, expirationPeriod: Duration): String {
        val claims: Claims = Jwts.claims()
            .add(mapOf("email" to email, "role" to role))
            .build()

        val key = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray(StandardCharsets.UTF_8))
        val now = Instant.now()

        return Jwts.builder()
            .subject(subject)
            .issuer(jwtProperties.issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()
    }

    private fun addTokenToCookie(token: String, response: HttpServletResponse) {
        val cookie = Cookie("jwt_token", token)
        cookie.isHttpOnly = true // JavaScript 에서 쿠키에 접근하지 못하도록 설정
        cookie.maxAge = (jwtProperties.accessTokenExpirationHour * 60 * 60 * 24 * 7).toInt() // 일주일
        cookie.path = "/"

        response.addCookie(cookie)
    }
}