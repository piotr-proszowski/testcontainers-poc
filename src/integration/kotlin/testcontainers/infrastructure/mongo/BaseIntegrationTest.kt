package testcontainers.infrastructure.mongo

import io.github.proszkie.testcontainers.TestcontainersApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest(classes = [TestcontainersApplication::class])
@ContextConfiguration(initializers = [MongoInitializer::class])
class BaseIntegrationTest {


}