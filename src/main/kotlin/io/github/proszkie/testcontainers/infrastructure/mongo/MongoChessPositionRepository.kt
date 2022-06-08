package io.github.proszkie.testcontainers.infrastructure.mongo

import io.github.proszkie.testcontainers.domain.ChessPosition
import io.github.proszkie.testcontainers.domain.ChessPositionId
import io.github.proszkie.testcontainers.domain.ChessPositionRepository
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.data.mongodb.core.query.where
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class MongoChessPositionRepository(private val mongoOperations: MongoOperations) : ChessPositionRepository {

    override fun find(id: ChessPositionId): ChessPosition? =
        mongoOperations.findOne(
            query(where(MongoChessPosition::id).isEqualTo(id.raw)), MongoChessPosition::class.java
        )?.let(MongoChessPosition::toDomain)

    override fun save(position: ChessPosition) =
        mongoOperations.save(MongoChessPosition.from(position))
}

@Document("chessPositions")
data class MongoChessPosition(
    @Id
    val id: UUID
) {
    fun toDomain(): ChessPosition =
        ChessPosition(
            id = ChessPositionId(id)
        )

    companion object {
        fun from(position: ChessPosition): MongoChessPosition =
            MongoChessPosition(position.id.raw)
    }
}