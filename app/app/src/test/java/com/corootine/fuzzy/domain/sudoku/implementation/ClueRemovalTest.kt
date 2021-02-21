package com.corootine.fuzzy.domain.sudoku.implementation

import com.corootine.fuzzy.domain.sudoku.api.SudokuGenerator
import com.corootine.fuzzy.domain.sudoku.api.SudokuGenerator.Metadata.Difficulty
import org.junit.Assert.*
import org.junit.Test

/**
 * We don't test [Difficulty.HARD] with unit tests, because it would take too long to run.
 */
class ClueRemovalTest {

    private val properSudokuChecker = ProperSudokuChecker()
    private val cluesCalculator = ClueCalculator()
    private val clueRemoval = ClueRemoval(properSudokuChecker, cluesCalculator)

    @Test
    fun `remove - with puzzle on easy difficulty - expect proper puzzle`() {
        val sudokuBuilder = sudokuBuilder(difficulty = Difficulty.EASY)

        clueRemoval.remove(sudokuBuilder)

        assertFalse(sudokuBuilder.isComplete())
        assertTrue(sudokuBuilder.numberOfClues() == cluesCalculator.calculate(sudokuBuilder.metadata))
        assertTrue(properSudokuChecker.isProper(sudokuBuilder))
    }

    @Test
    fun `remove - with puzzle on medium difficulty - expect proper puzzle`() {
        val sudokuBuilder = sudokuBuilder(difficulty = Difficulty.MEDIUM)

        clueRemoval.remove(sudokuBuilder)

        assertFalse(sudokuBuilder.isComplete())
        assertTrue(sudokuBuilder.numberOfClues() == cluesCalculator.calculate(sudokuBuilder.metadata))
        assertTrue(properSudokuChecker.isProper(sudokuBuilder))
    }

    @Test
    fun `remove twice - with same seed - expect same puzzle`() {
        val sudokuBuilderOne = sudokuBuilder(difficulty = Difficulty.EASY)
        val sudokuBuilderTwo = sudokuBuilder(difficulty = Difficulty.EASY)

        with(clueRemoval) {
            remove(sudokuBuilderOne)
            remove(sudokuBuilderTwo)
        }

        assertEquals(sudokuBuilderOne, sudokuBuilderTwo)
    }

    @Test
    fun `remove twice - with different seed - expect different puzzle`() {
        val sudokuBuilderOne = sudokuBuilder(123456, Difficulty.EASY)
        val sudokuBuilderTwo = sudokuBuilder(654321, difficulty = Difficulty.EASY)

        with(clueRemoval) {
            remove(sudokuBuilderOne)
            remove(sudokuBuilderTwo)
        }

        assertNotEquals(sudokuBuilderOne, sudokuBuilderTwo)
    }

    private fun sudokuBuilder(seed: Long = 123456, difficulty: Difficulty) = SudokuBuilder(
        SudokuGenerator.Metadata(
            seed = seed,
            difficulty = difficulty
        )
    ).apply {
        val puzzle = arrayOf(
            arrayOf(2, 4, 6, 8, 3, 5, 1, 9, 7),
            arrayOf(7, 8, 9, 2, 1, 6, 4, 3, 5),
            arrayOf(5, 3, 1, 7, 4, 9, 2, 6, 8),

            arrayOf(4, 2, 5, 6, 8, 1, 9, 7, 3),
            arrayOf(8, 6, 3, 9, 7, 4, 5, 2, 1),
            arrayOf(1, 9, 7, 5, 2, 3, 8, 4, 6),

            arrayOf(3, 1, 2, 4, 6, 8, 7, 5, 9),
            arrayOf(9, 7, 8, 3, 5, 2, 6, 1, 4),
            arrayOf(6, 5, 4, 1, 9, 7, 3, 8, 2),
        )

        for (row in puzzle.indices) {
            for (column in puzzle[row].indices) {
                if (puzzle[row][column] != 0) {
                    trySet(row, column, puzzle[row][column])
                }
            }
        }
    }

    private fun SudokuBuilder.numberOfClues() = run {
        var clues = 0

        for (row in 0 until metadata.rowsInGrid) {
            for (column in 0 until metadata.columnsInGrid) {
                if (!get(row, column).isEmpty) {
                    clues++
                }
            }
        }

        return@run clues
    }
}