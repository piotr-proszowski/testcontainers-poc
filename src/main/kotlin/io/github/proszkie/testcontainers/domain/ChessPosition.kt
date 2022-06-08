package io.github.proszkie.testcontainers.domain

data class ChessPosition(
    val id: ChessPositionId
) {
    fun analyze(): ChessPositionRating {
        // some advance logic goes there
        return ChessPositionRating(
            whiteRating = 42.toBigDecimal(),
            blackRating = 58.toBigDecimal()
        )
    }
}
