package io.github.proszkie.testcontainers.domain

import java.math.BigDecimal

data class ChessPositionRating (
    val whiteRating: BigDecimal,
    val blackRating: BigDecimal
)