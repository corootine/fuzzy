package com.corootine.fuzzy.domain.sudoku

class WellFormityChecker {

    var solutions = 0

    fun isWellFormed(params: GenerationParams, puzzleBuilder: PuzzleBuilder): Boolean {
        solutions = 0
        return isWellFormedRecursive(params, puzzleBuilder)
    }

    private fun isWellFormedRecursive(
        params: GenerationParams,
        puzzleBuilder: PuzzleBuilder
    ): Boolean {
        if (puzzleBuilder.isComplete()) {
            solutions++
            return solutions == 1
        }

        for (row in 0 until params.rowsInPuzzle) {
            for (column in 0 until params.columnsInPuzzle) {
                if (puzzleBuilder[row, column] == 0) {
                    for (value in 1..params.highestNumber) {
                        if (puzzleBuilder.trySet(row, column, value)) {
                            isWellFormedRecursive(params, puzzleBuilder)
                            puzzleBuilder.reset(row, column)
                        }
                    }

                    return solutions <= 1
                }
            }
        }

        return solutions == 1
    }
}