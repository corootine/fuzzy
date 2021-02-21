package com.corootine.fuzzy.domain.sudoku.implementation

import com.corootine.fuzzy.domain.sudoku.api.Sudoku
import com.corootine.fuzzy.domain.sudoku.api.SudokuGenerator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SudokuToSolveTest : SudokuTest() {

    private val metadata = SudokuGenerator.Metadata(rowsPerBox = 2, columnsPerBox = 2, seed = 1234)

    override fun createSudoku(grid: Array<Array<Int>>, metadata: SudokuGenerator.Metadata): Sudoku {
        val sudokuBuilder = SudokuBuilder(metadata).apply {
            applyGrid(grid)
        }

        return SudokuToSolve(sudokuBuilder)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `init - when sudokuBuilder is complete - expect exception`() {
        with(SudokuBuilder(metadata)) {
            applyGrid(
                arrayOf(
                    arrayOf(2, 3, 4, 1),
                    arrayOf(4, 1, 2, 3),
                    arrayOf(1, 4, 3, 2),
                    arrayOf(3, 2, 1, 4),
                )
            )

            SudokuToSolve(this)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `init - when sudokuBuilder is empty - expect exception`() {
        with(SudokuBuilder(metadata)) {
            applyGrid(
                arrayOf(
                    arrayOf(0, 0, 0, 0),
                    arrayOf(0, 0, 0, 0),
                    arrayOf(0, 0, 0, 0),
                    arrayOf(0, 0, 0, 0),
                )
            )

            SudokuToSolve(this)
        }
    }

    @Test
    fun `init - when value is not 0 - expect it to be clue`() {
        val sudoku = SudokuBuilder(metadata).run {
            applyGrid(
                arrayOf(
                    arrayOf(2, 3, 4, 1),
                    arrayOf(4, 0, 2, 3),
                    arrayOf(1, 4, 0, 2),
                    arrayOf(3, 2, 0, 4),
                )
            )

            return@run SudokuToSolve(this)
        }

        for (row in 0 until sudoku.metadata.rowsInGrid) {
            for (column in 0 until sudoku.metadata.columnsInGrid) {
                with(sudoku.get(row, column)) {
                    if (isEmpty) {
                        assertFalse(isClue)
                    } else {
                        assertTrue(isClue)
                    }
                }
            }
        }
    }
}