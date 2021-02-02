package com.corootine.fuzzy.domain.sudokuV1

import com.corootine.fuzzy.domain.sudokuV1.old.Board2
import org.junit.Test

class WellFormityCheckerTest {

    @Test
    fun `should be well formed`() {

    }

    @Test
    fun `should not be well formed`() {
        val board = createBoard(
            PuzzleGenerator.Input(seed = 14142),
            7, 1, 9, 4, 2, 5, 3, 6, 8,
            6, 2, 3, 1, 7, 8, 4, 9, 5,
            4, 5, 8, 9, 6, 3, 2, 7, 1,
            8, 7, 1, 6, 4, 9, 5, 3, 2,
            3, 6, 5, 8, 1, 2, 9, 4, 7,
            9, 4, 2, 3, 5, 7, 1, 8, 6,
            1, 3, 7, 2, 8, 4, 6, 5, 9,
            2, 8, 4, 5, 9, 6, 7, 1, 3,
            5, 9, 6, 7, 3, 1, 8, 2, 4
        )

        board.reset(0, 0)
        board.reset(0, 1)
        board.reset(0, 2)
        board.reset(0, 3)
        board.reset(0, 5)
        board.reset(0, 6)
        board.reset(0, 8)

        board.reset(1, 3)
        board.reset(1, 6)
        board.reset(1, 7)
        board.reset(1, 8)

        board.reset(2, 1)
        board.reset(2, 2)
        board.reset(2, 3)
        board.reset(2, 5)
        board.reset(2, 6)
        board.reset(2, 7)

        board.reset(3, 0)
        board.reset(3, 1)
        board.reset(3, 2)
        board.reset(3, 4)
        board.reset(3, 5)
        board.reset(3, 6)
        board.reset(3, 7)

        board.reset(4, 0)
        board.reset(4, 1)
        board.reset(4, 7)
        board.reset(4, 8)

        board.reset(5, 1)
        board.reset(5, 2)
        board.reset(5, 3)
        board.reset(5, 4)
        board.reset(5, 6)
        board.reset(5, 7)
        board.reset(5, 8)

        board.reset(6, 1)
        board.reset(6, 2)
        board.reset(6, 3)
        board.reset(6, 5)
        board.reset(6, 6)
        board.reset(6, 7)

        board.reset(7, 0)
        board.reset(7, 1)
        board.reset(7, 2)
        board.reset(7, 5)

        board.reset(8, 0)
        board.reset(8, 2)
        board.reset(8, 3)
        board.reset(8, 5)
        board.reset(8, 6)
        board.reset(8, 7)
        board.reset(8, 8)

        println(board)

        val wellFormedChecker = WellFormityChecker()
        println(wellFormedChecker.isWellFormed(board, PuzzleGenerator.Input(seed = 515123)))
    }

    private fun createBoard(input: PuzzleGenerator.Input, vararg values: Int): Board2 {
        require(values.size == input.boardSize * input.boardSize)

        var row: Int
        var column: Int
        val board = Board2(input)

        values.forEachIndexed { index, value ->
            row = index / input.boardSize
            column = index % input.boardSize
            board.trySet(row, column, value)
        }

        return board
    }
}