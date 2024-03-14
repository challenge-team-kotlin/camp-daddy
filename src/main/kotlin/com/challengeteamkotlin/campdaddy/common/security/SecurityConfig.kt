package com.challengeteamkotlin.campdaddy.common.security

import com.challengeteamkotlin.campdaddy.application.auth.OAuth2LoginSuccessHandler
import com.challengeteamkotlin.campdaddy.application.auth.OAuth2UserService
import com.challengeteamkotlin.campdaddy.infrastructure.jwt.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    val oAuth2UserService: OAuth2UserService,
    val oAuth2LoginSuccessHandler: OAuth2LoginSuccessHandler
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .csrf { it.disable() }
            .headers { it.frameOptions { options -> options.sameOrigin() } }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .oauth2Login { oauthConfig ->
                oauthConfig.authorizationEndpoint {
                    it.baseUri("/oauth2/login")  //oauth2/login/
                }.redirectionEndpoint {
                    it.baseUri("/oauth2/callback/*") // /oauth2/callback/
                }.userInfoEndpoint {
                    it.userService(oAuth2UserService)
                }.successHandler(oAuth2LoginSuccessHandler)}
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/login",
                    "/signup",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/h2-console",
                    "/ws/chat/**"
                ).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}