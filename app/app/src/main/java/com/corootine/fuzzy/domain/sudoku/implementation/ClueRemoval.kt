package com.corootine.fuzzy.domain.sudoku.implementation

import com.corootine.fuzzy.domain.sudoku.api.SudokuGenerator

class ClueRemoval(private val properSudokuChecker: ProperSudokuChecker) {

    /**
     * Removes clues from the solved sudoku until we get a puzzle with valid difficulty
     * (expected number of clues). The fewer clues the puzzle should have, the longer this
     * operations takes, because it asserts that with each removed clue, the puzzle is still proper
     * (see [ProperSudokuChecker]).
     */
    fun remove(sudokuBuilder: SudokuBuilder) {
        val metadata = sudokuBuilder.metadata
        val random = metadata.random
        var clues = metadata.cellsInGrid
        val expectedNumberOfClues = numberOfClues(metadata)

        while (clues >= expectedNumberOfClues) {
            val row = random.nextInt(metadata.rowsInGrid)
            val column = random.nextInt(metadata.columnsInGrid)

            if (tryToRemoveClue(sudokuBuilder, row, column)) {
                clues--
            }
        }
    }

    private fun numberOfClues(metadata: SudokuGenerator.Metadata): Int {
        // Just some random constants chosen based on experience in 9x9 puzzles.
        // These might be chosen in a more methodical and proven fashion.
        return when (metadata.difficulty) {
            SudokuGenerator.Metadata.Difficulty.EASY -> metadata.cellsInGrid / 2.4
            SudokuGenerator.Metadata.Difficulty.MEDIUM -> metadata.cellsInGrid / 3.2
            SudokuGenerator.Metadata.Difficulty.HARD -> metadata.cellsInGrid / 4
        }.toInt()
    }

    private fun tryToRemoveClue(
        sudokuBuilder: SudokuBuilder,
        row: Int,
        column: Int
    ): Boolean {
        if (sudokuBuilder.get(row, column).isEmpty()) {
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