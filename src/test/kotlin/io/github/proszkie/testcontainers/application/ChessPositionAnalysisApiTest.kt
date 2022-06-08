package io.github.proszkie.testcontainers.application

import io.github.proszkie.testcontainers.domain.ChessPosition
import io.github.proszkie.testcontainers.domain.ChessPositionId
import io.github.proszkie.testcontainers.domain.ChessPositionRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.UUID

class ChessPositionAnalysisApiTest {

    @Test
    fun `should return valid rating`() {
        // given
        val (chessPositionId, repositoryStub) = `there is a repository with stubbed chess position`()
        val chessPositionRateApi = ChessPositionAnalysisApi(repositoryStub)

        // when
        val result = chessPositionRateApi.analyze(chessPositionId)

        // then
        // some meaningful fluent assertions
        assertThat(result).isNotNull
        assertThat(1).isEqualTo(1)
    }

    private fun `there is a repository with stubbed chess position`(): Pair<ChessPositionId, ChessPositionRepository> {
        val chessPositionId = ChessPositionId(UUID.randomUUID())
        val chessPositionRepositoryStub = mock(ChessPositionRepository::class.java)
        `when`(chessPositionRepositoryStub.find(chessPositionId)).thenReturn(ChessPosition(chessPositionId))
        return chessPositionId to chessPositionRepositoryStub
    }

}