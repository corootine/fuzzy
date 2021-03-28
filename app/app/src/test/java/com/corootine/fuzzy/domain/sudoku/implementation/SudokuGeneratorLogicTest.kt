package com.corootine.fuzzy.domain.sudoku.implementation

import com.corootine.fuzzy.domain.sudoku.api.SudokuGenerator
import org.junit.Assert.assertEquals
import org.junit.Test

class SudokuGeneratorLogicTest {

    private val sudokuGenerator = SudokuGeneratorLogic(
        GridFiller(),
        ClueRemoval(ProperSudokuChecker(), ClueCalculator())
    )

    @Test
    fun `generate 9x9 puzzle`() {
        val metadata = SudokuGenerator.Metadata(seed = 1234)
        val result = sudokuGenerator.generate(metadata)

        val expected = SudokuBuilder(metadata).run {
            applyGrid(
                arrayOf(
                    arrayOf(0, 1, 6, 0, 9, 0, 7, 0, 3),
                    arrayOf(0, 0, 0, 3, 7, 0, 0, 0, 2),
                    arrayOf(7, 0, 4, 0, 0, 0, 9, 0, 0),

                    arrayOf(1, 7, 0, 0, 0, 0, 0, 3, 0),
                    arrayOf(0, 0, 3, 0, 5, 9, 4, 2, 1),
                    arrayOf(0, 0, 5, 0, 0, 0, 8, 0, 0),

                    arrayOf(0, 0, 0, 8, 0, 2, 5, 9, 0),
                    arrayOf(8, 5, 0, 0, 0, 0, 3, 1, 0),
                    arrayOf(0, 0, 0, 5, 3, 0, 0, 0, 6),
                )
            )

            return@run SudokuToSolve(this)
        }

        assertEquals(expected, result)
    }

    @Test
    fun `generate 4x4 puzzle`() {
        val metadata = SudokuGenerator.Metadata(
            rowsPerBox = 2,
            columnsPerBox = 2,
            seed = 1234
        )
        val result = sudokuGenerator.generate(metadata)

        val expected = SudokuBuilder(metadata).run {
            applyGrid(
                arrayOf(
                    arrayOf(0, 3, 4, 0),
                    arrayOf(0, 1, 2, 0),

                    arrayOf(0, 0, 0, 2),
                    arrayOf(3, 0, 0, 0),
                )
            )

            return@run SudokuToSolve(this)
        }

        assertEquals(expected, result)
    }


    @Test
    fun `generate 2x3 puzzle`() {
        val metadata = SudokuGenerator.Metadata(
            rowsPerBox = 2,
            columnsPerBox = 3,
            seed = 1234
        )
        val result = sudokuGenerator.generate(metadata)

        val expected = SudokuBuilder(metadata).run {
            applyGrid(
                arrayOf(
                    arrayOf(0, 4, 0, 6, 0, 0),
                    arrayOf(0, 0, 0, 0, 4, 0),

                    arrayOf(0, 2, 0, 4, 1, 0),
                    arrayOf(1, 0, 0, 0, 0, 0),

                    arrayOf(3, 6, 2, 0, 0, 0),
                    arrayOf(4, 5, 0, 3, 6, 2),
                )
            )

            return@run SudokuToSolve(this)
        }

        assertEquals(expected, result)
    }

    @Test
    fun `generate - when input is same - expect same output`() {
        with(sudokuGenerator) {
            val sudoku1 = generate(SudokuGenerator.Metadata(seed = 1234))
            val sudoku2 = generate(SudokuGenerator.Metadata(seed = 1234))

            assertEquals(sudoku1, sudoku2)
        }
    }
}