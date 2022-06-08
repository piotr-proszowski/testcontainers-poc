package io.github.proszkie.testcontainers.application

import io.github.proszkie.testcontainers.domain.ChessPositionId
import io.github.proszkie.testcontainers.domain.ChessPositionRating
import io.github.proszkie.testcontainers.domain.ChessPositionRepository
import org.springframework.stereotype.Component

@Component
class ChessPositionAnalysisApi(private val repository: ChessPositionRepository) {

    fun analyze(id: ChessPositionId): ChessPositionRating? =
        repository.find(id)?.analyze() ?: throw IllegalStateException("There is no chess position for id $id")

}