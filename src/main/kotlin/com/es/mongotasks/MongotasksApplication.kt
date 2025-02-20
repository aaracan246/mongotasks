package com.es.mongotasks

import com.es.mongotasks.security.RSAKeysProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RSAKeysProperties::class)
class MongotasksApplication

fun main(args: Array<String>) {
	runApplication<MongotasksApplication>(*args)
}
