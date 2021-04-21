package com.corootine.fuzzy.domain.sudoku.implementation

import com.corootine.fuzzy.domain.sudoku.SudokuGenerator
import com.corootine.fuzzy.domain.sudoku.SudokuGenerator.Metadata.Difficulty
import com.corootine.fuzzy.domain.sudoku.SudokuGenerator.Metadata.Difficulty.*
import org.junit.Assert.assertTrue
import org.junit.Test

class ClueCalculatorTest {

    @Test
    fun `calculate - with increasing difficulty - expect fewer clues`() {
        with(ClueCalculator()) {
            val cluesOnEasy = calculate(metadata(EASY))
            val cluesOnMedium = calculate(metadata(MEDIUM))
            val cluesOnHard = calculate(metadata(HARD))

            assertTrue(cluesOnEasy > cluesOnMedium && cluesOnMedium > cluesOnHard)
        }
    }

    private fun metadata(difficulty: Difficulty) = SudokuGenerator.Metadata(
        seed = 12346,
        difficulty = difficulty
    )
}