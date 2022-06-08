package io.github.proszkie.testcontainers

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class TestcontainersApplication

fun main(args: Array<String>) {
	runApplication<TestcontainersApplication>(*args)
}
