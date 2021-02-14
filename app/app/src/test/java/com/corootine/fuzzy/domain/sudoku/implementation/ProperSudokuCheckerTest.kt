package com.corootine.fuzzy.domain.sudoku.implementation

import com.corootine.fuzzy.domain.sudoku.api.SudokuGenerator
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ProperSudokuCheckerTest(private val puzzle: Array<Array<Int>>) {

    private val properSudokuChecker = ProperSudokuChecker()

    companion object {

        @JvmStatic
        @Parameterized.Parameters
        fun puzzles() = arrayOf(
            arrayOf(
                arrayOf(
                    arrayOf(7, 0, 0, 9, 4, 0, 0, 3, 0),
                    arrayOf(9, 6, 0, 1, 7, 0, 0, 8, 0),
                    arrayOf(5, 0, 4, 2, 3, 0, 1, 0, 0),

                    arrayOf(0, 9, 0, 6, 1, 7, 0, 0, 0),
                    arrayOf(0, 0, 1, 5, 0, 0, 0, 0, 9),
                    arrayOf(0, 7, 6, 0, 0, 0, 4, 1, 0),

                    arrayOf(2, 0, 7, 0, 6, 9, 0, 5, 0),
                    arrayOf(0, 0, 0, 4, 2, 3, 6, 0, 8),
                    arrayOf(6, 3, 0, 0, 0, 0, 0, 0, 0),
                ),
            ),
            arrayOf(
                arrayOf(
                    arrayOf(9, 0, 0, 2, 3, 0, 5, 8, 7),
                    arrayOf(0, 8, 0, 4, 5, 0, 0, 0, 0),
                    arrayOf(5, 6, 0, 0, 0, 0, 0, 9, 3),

                    arrayOf(0, 0, 1, 0, 9, 0, 0, 5, 0),
                    arrayOf(0, 9, 3, 0, 2, 0, 7, 1, 0),
                    arrayOf(0, 0, 0, 0, 7, 0, 3, 0, 0),

                    arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
                    arrayOf(0, 2, 0, 7, 8, 0, 0, 0, 5),
                    arrayOf(0, 0, 0, 5, 0, 1, 0, 0, 6),
                ),
            ),
            arrayOf(
                arrayOf(
                    arrayOf(3, 0, 0, 0, 0, 5, 0, 1, 4),
                    arrayOf(0, 0, 1, 0, 4, 0, 0, 0, 0),
                    arrayOf(2, 0, 0, 0, 3, 0, 0, 0, 9),

                    arrayOf(0, 0, 6, 4, 0, 7, 5, 0, 0),
                    arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 3),
                    arrayOf(0, 8, 0, 0, 0, 9, 1, 0, 0),

                    arrayOf(7, 0, 0, 0, 1, 0, 8, 0, 5),
                    arrayOf(0, 0, 0, 0, 0, 0, 0, 7, 0),
                    arrayOf(9, 0, 0, 6, 0, 0, 0, 0, 0),
                ),
            ),
            arrayOf(
                arrayOf(
                    arrayOf(0, 4, 0, 0, 0, 0, 0, 8, 3),
                    arrayOf(6, 0, 0, 9, 0, 0, 0, 0, 0),
                    arrayOf(0, 0, 0, 0, 5, 1, 0, 2, 0),

                    arrayOf(9, 7, 0, 1, 0, 0, 0, 0, 4),
                    arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
                    arrayOf(0, 0, 0, 6, 0, 0, 1, 3, 0),

                    arrayOf(0, 0, 2, 0, 7, 0, 0, 0, 0),
                    arrayOf(5, 0, 0, 0, 0, 8, 7, 0, 0),
                    arrayOf(4, 0, 0, 0, 0, 3, 0, 1, 0),
                ),
            ),
        )
    }

    @Test
    fun `puzzle is proper`() {
        val sudokuBuilder = createSudoku(puzzle)
        println(sudokuBuilder)
        assertTrue(properSudokuChecker.isProper(sudokuBuilder))
    }

    private fun createSudoku(puzzle: Array<Array<Int>>): SudokuBuilder {
        val sudokuBuilder = SudokuBuilder(SudokuGenerator.Metadata(seed = 0L))

        for (row in puzzle.indices) {
            for (column in puzzle[row].indices) {
                if (puzzle[row][column] != 0) {
                    sudokuBuilder.trySet(row, column, puzzle[row][column])
                }
            }
        }

        return sudokuBuilder
    }
}