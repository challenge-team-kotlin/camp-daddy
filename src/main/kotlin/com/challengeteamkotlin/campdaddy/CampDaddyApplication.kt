package com.challengeteamkotlin.campdaddy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class CampDaddyApplication

fun main(args: Array<String>) {
	runApplication<CampDaddyApplication>(*args)
}
