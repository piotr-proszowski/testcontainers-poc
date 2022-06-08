package testcontainers.infrastructure.mongo

import com.mongodb.BasicDBObject
import io.github.proszkie.testcontainers.domain.ChessPosition
import io.github.proszkie.testcontainers.domain.ChessPositionId
import io.github.proszkie.testcontainers.domain.ChessPositionRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import java.util.UUID.randomUUID

class MongoChessPositionRepositoryTest : BaseIntegrationTest() {

    @Autowired
    lateinit var mongoShardTemplate: MongoTemplate

    @Autowired
    lateinit var repository: ChessPositionRepository

    @Test
    fun `should have sharded mongo server`() {
        val shards =
            mongoShardTemplate
                .mongoDatabaseFactory
                .getMongoDatabase("admin")
                .runCommand(BasicDBObject("listShards", 1))
                .get("shards") as List<Map<String, Int>>

        assertThat(shards.size).isEqualTo(2)
        assertThat(shards.all { it.get("state") == 1 }).isTrue()
    }

    @Test
    fun `should find previously saved position`() {
        // given
        val chessPosition = ChessPosition(ChessPositionId(randomUUID()))

        // when
        repository.save(chessPosition)

        // then
        assertDoesNotThrow {
            repository.find(chessPosition.id)
        }
    }
}