package io.github.proszkie.testcontainers.domain

import io.github.proszkie.testcontainers.infrastructure.mongo.MongoChessPosition

interface ChessPositionRepository {
    fun find(id: ChessPositionId): ChessPosition?
    fun save(position: ChessPosition): MongoChessPosition
}