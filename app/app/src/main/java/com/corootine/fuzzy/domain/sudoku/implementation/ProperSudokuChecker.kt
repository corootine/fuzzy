package com.corootine.fuzzy.domain.sudoku.implementation

class ProperSudokuChecker {

    /**
     * Checks if the puzzle is proper. A puzzle is proper if it contains only one solution.
     */
    fun isProper(sudokuBuilder: SudokuBuilder): Boolean {
        val solutions = isWellFormedRecursive(sudokuBuilder)
        return solutions == 1
    }

    private fun isWellFormedRecursive(
        sudokuBuilder: SudokuBuilder,
        solutions: Int = 0,
    ): Int {
        val metadata = sudokuBuilder.metadata
        var currentSolutions = solutions

        if (sudokuBuilder.isComplete()) {
            return currentSolutions + 1
        }

        for (row in 0 until metadata.rowsInGrid) {
            for (column in 0 until metadata.columnsInGrid) {
                if (sudokuBuilder.get(row, column).isEmpty()) {
                    for (value in 1..metadata.highestNumber) {
                        if (sudokuBuilder.trySet(row, column, value)) {
                            val newSolutions = isWellFormedRecursive(sudokuBuilder, currentSolutions)
                            if (newSolutions > currentSolutions) {
                                currentSolutions = newSolutions
                            }
                            sudokuBuilder.reset(row, column)
                        }
                    }

                    return currentSolutions
                }
            }
        }

        return currentSolutions
    }
}