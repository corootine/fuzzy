package com.corootine.fuzzy.domain.sudoku.implementation

import com.corootine.fuzzy.domain.sudoku.SudokuGenerator

class ClueRemoval(
    private val properSudokuChecker: ProperSudokuChecker,
    private val clueCalculator: ClueCalculator,
) {

    /**
     * Removes clues from the solved sudoku until we get a puzzle with valid difficulty. The difficulty
     * is derived from [SudokuGenerator.Metadata.difficulty]. The fewer clues the puzzle should have,
     * the longer this operations will take, because it asserts that with each removed clue, the puzzle is
     * still proper. Using same inputs will produce the same result if called on identical puzzles.
     *
     * (see [ProperSudokuChecker]).
     */
    fun remove(sudokuBuilder: SudokuBuilder) {
        val metadata = sudokuBuilder.metadata
        val random = metadata.random
        var clues = metadata.cellsInGrid
        val expectedNumberOfClues = clueCalculator.calculate(metadata)

        while (clues > expectedNumberOfClues) {
            val row = random.nextInt(metadata.rowsInGrid)
            val column = random.nextInt(metadata.columnsInGrid)

            if (tryToRemoveClue(sudokuBuilder, row, column)) {
                clues--
            }
        }
    }

    private fun tryToRemoveClue(
        sudokuBuilder: SudokuBuilder,
        row: Int,
        column: Int
    ): Boolean {
        if (sudokuBuilder.get(row, column).isEmpty) {
            return false
        }

        val previousValue = sudokuBuilder.get(row, column)
        sudokuBuilder.reset(row, column)

        return if (properSudokuChecker.isProper(sudokuBuilder)) {
            true
        } else {
            sudokuBuilder.trySet(row, column, previousValue.value)
            false
        }
    }
}