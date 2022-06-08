package testcontainers.infrastructure.mongo

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import java.io.File
import java.util.concurrent.atomic.AtomicBoolean

class MongoInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    companion object {
        val shouldStartDatabase: AtomicBoolean = AtomicBoolean(true)
    }

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        if (shouldStartDatabase.getAndSet(false)) {
            startShardedMongoDatabase()
        }
    }

    private fun startShardedMongoDatabase() {
        val configFile = File(javaClass.getResource("/mongo/docker-compose.yml").toURI())
        val container = DockerComposeContainer<SELF>(configFile)
            .withExposedService("mongos-router0", 27017, Wait.forHealthcheck())
        container.start()
    }
}

typealias SELF = DockerComposeContainer<Nothing>