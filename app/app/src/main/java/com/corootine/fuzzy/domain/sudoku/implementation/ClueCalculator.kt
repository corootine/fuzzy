package com.corootine.fuzzy.domain.sudoku.implementation

import com.corootine.fuzzy.domain.sudoku.api.SudokuGenerator
import com.corootine.fuzzy.domain.sudoku.api.SudokuGenerator.Metadata.Difficulty

class ClueCalculator {

    /**
     * Calculates the number of clues based on the puzzle metadata.
     * This is derived from the [Difficulty] property.
     */
    fun calculate(metadata: SudokuGenerator.Metadata): Int {
        // Just some random constants chosen based on experience in 9x9 puzzles.
        // These could be selected in a more methodical way in the future.
        return when (metadata.difficulty) {
            Difficulty.EASY -> metadata.cellsInGrid / 2.4
            Difficulty.MEDIUM -> metadata.cellsInGrid / 3.2
            Difficulty.HARD -> metadata.cellsInGrid / 4
        }.toInt()
    }
}