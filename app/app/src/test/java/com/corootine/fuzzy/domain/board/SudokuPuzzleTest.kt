package com.corootine.fuzzy.domain.board

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith

const val CELL_SIZE = 3
const val BOARD_SIZE = CELL_SIZE * CELL_SIZE

@RunWith(Enclosed::class)
class SudokuPuzzleTest {

    class NonParameterized {

        @Test(expected = IllegalArgumentException::class)
        fun `should fail to set if row is lesser than 0`() {
            // given
            val builder = SudokuPuzzle.Builder(CELL_SIZE)

            // when
            builder.set(-1, 0, 5)
        }

        @Test(expected = IllegalArgumentException::class)
        fun `should fail to set if row is greater than board size`() {
            // given
            val builder = SudokuPuzzle.Builder(CELL_SIZE)

            // when
            builder.set(BOARD_SIZE + 1, 0, 5)
        }

        @Test(expected = IllegalArgumentException::class)
        fun `should fail to set if column is lesser than 0`() {
            // given
            val builder = SudokuPuzzle.Builder(CELL_SIZE)

            // when
            builder.set(0, -1, 5)
        }

        @Test(expected = IllegalArgumentException::class)
        fun `should fail to set if column is greater than board size`() {
            // given
            val builder = SudokuPuzzle.Builder(CELL_SIZE)

            // when
            builder.set(0, BOARD_SIZE + 1, 5)
        }

        @Test(expected = IllegalArgumentException::class)
        fun `should fail to set if value is lesser than 1`() {
            // given
            val builder = SudokuPuzzle.Builder(CELL_SIZE)

            // when
            builder.set(1, 1, 0)
        }

        @Test(expected = IllegalArgumentException::class)
        fun `should fail to set if value is greater than board size`() {
            // given
            val builder = SudokuPuzzle.Builder(CELL_SIZE)

            // when
            builder.set(1, 1, BOARD_SIZE + 1)
        }

        @Test
        fun `should fail to set if value already appears in the row`() {
            // given
            val builder = SudokuPuzzle.Builder(CELL_SIZE)

            // when
            val firstSet = builder.set(0, 1, 5)
            val secondSet = builder.set(0, 8, 5)

            // then
            assertEquals(true, firstSet)
            assertEquals(false, secondSet)
        }

        @Test
        fun `should fail to set if value already appears in the column`() {
            // given
            val builder = SudokuPuzzle.Builder(CELL_SIZE)

            // when
            val firstSet = builder.set(1, 0, 5)
            val secondSet = builder.set(8, 0, 5)

            // then
            assertEquals(true, firstSet)
            assertEquals(false, secondSet)
        }

        @Test
        fun `should fail to set if value already appears in the cell`() {
            // given
            val builder = SudokuPuzzle.Builder(CELL_SIZE)

            // when
            val firstSet = builder.set(0, 4, 5)
            val secondSet = builder.set(2, 5, 5)

            // then
            assertEquals(true, firstSet)
            assertEquals(false, secondSet)
        }
    }

    @RunWith(org.junit.runners.Parameterized::class)
    class Parameterized(private val board: Array<Array<Int>>, private val isBoardValid: Boolean) {

        companion object {

            @JvmStatic
            @org.junit.runners.Parameterized.Parameters
            fun data() = arrayOf(
                arrayOf(
                    arrayOf(
                        arrayOf(1, 5, 2, 4, 8, 9, 3, 7, 6),
                        arrayOf(7, 3, 9, 2, 5, 6, 8, 4, 1),
                        arrayOf(4, 6, 8, 3, 7, 1, 2, 9, 5),
                        arrayOf(3, 8, 7, 1, 2, 4, 6, 5, 9),
                        arrayOf(5, 9, 1, 7, 6, 3, 4, 2, 8),
                        arrayOf(2, 4, 6, 8, 9, 5, 7, 1, 3),
                        arrayOf(9, 1, 4, 6, 3, 7, 5, 8, 2),
                        arrayOf(6, 2, 5, 9, 4, 8, 1, 3, 7),
                        arrayOf(8, 7, 3, 5, 1, 2, 9, 6, 4),
                    ),
                    true
                ),
                arrayOf(
                    arrayOf(
                        arrayOf(1, 5, 2, 4, 8, 9, 3, 7, 6),
                        arrayOf(7, 3, 9, 2, 5, 6, 8, 4, 1),
                        arrayOf(4, 6, 8, 3, 7, 1, 2, 9, 5),
                        arrayOf(3, 8, 7, 1, 2, 4, 6, 5, 9),
                        arrayOf(5, 9, 1, 7, 6, 3, 4, 2, 8),
                        arrayOf(2, 4, 6, 8, 9, 6, 7, 1, 3),
                        arrayOf(9, 1, 4, 6, 3, 7, 4, 8, 2),
                        arrayOf(6, 2, 5, 9, 4, 8, 1, 3, 7),
                        arrayOf(8, 7, 3, 5, 1, 2, 9, 6, 4),
                    ),
                    false
                ),
                arrayOf(
                    arrayOf(
                        arrayOf(1, 5, 2, 4, 8, 9, 3, 7, 6),
                        arrayOf(7, 3, 9, 2, 5, 6, 8, 4, 1),
                        arrayOf(4, 6, 8, 3, 7, 1, 2, 9, 5),
                        arrayOf(3, 8, 7, 1, 2, 4, 6, 5, 9),
                        arrayOf(5, 9, 1, 7, 6, 4, 4, 2, 8),
                        arrayOf(2, 4, 6, 8, 9, 5, 7, 1, 3),
                        arrayOf(9, 1, 4, 6, 3, 7, 4, 8, 2),
                        arrayOf(6, 2, 5, 9, 4, 8, 1, 3, 7),
                        arrayOf(8, 7, 3, 5, 1, 2, 9, 6, 4),
                    ),
                    false
                ),
                arrayOf(
                    arrayOf(
                        arrayOf(5, 8, 1, 6, 7, 2, 4, 3, 9),
                        arrayOf(7, 9, 2, 8, 4, 3, 6, 5, 1),
                        arrayOf(3, 6, 4, 5, 9, 1, 7, 8, 2),
                        arrayOf(4, 3, 8, 9, 5, 7, 2, 1, 6),
                        arrayOf(2, 5, 6, 1, 8, 4, 9, 7, 3),
                        arrayOf(1, 7, 9, 3, 2, 6, 8, 4, 5),
                        arrayOf(8, 4, 5, 2, 1, 9, 3, 6, 7),
                        arrayOf(9, 1, 3, 7, 6, 8, 5, 2, 4),
                        arrayOf(6, 2, 7, 4, 3, 5, 1, 9, 8),
                    ),
                    true
                )
            )
        }

        @Test
        fun shouldCreateBoard() {
            // given
            val builder = SudokuPuzzle.Builder(CELL_SIZE)

            // when
            for (rowIndex in board.indices) {
                for (columnIndex in board[rowIndex].indices) {
                    val result = builder.set(rowIndex, columnIndex, board[rowIndex][columnIndex])
                    if (!result) {
                        println("Failed to set ${board[rowIndex][columnIndex]} at [$rowIndex][$columnIndex]")
                        assertEquals(false, isBoardValid)
                        return
                    }
                }
            }
            val actualBoard = builder.build()

            // then
            for (rowIndex in board.indices) {
                for (columnIndex in board[rowIndex].indices) {
                   assertEquals(board[rowIndex][columnIndex], actualBoard[rowIndex, columnIndex])
                }
            }

            assertEquals(true, isBoardValid)
        }
    }
}