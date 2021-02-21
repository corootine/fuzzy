package com.corootine.fuzzy.domain.sudoku.api

import com.corootine.fuzzy.domain.sudoku.api.SudokuGenerator.Metadata.Companion.DEFAULT_COLUMNS
import com.corootine.fuzzy.domain.sudoku.api.SudokuGenerator.Metadata.Companion.DEFAULT_ROWS
import kotlin.random.Random

interface SudokuGenerator {

    /**
     * Generates a new [Sudoku] from the provided input.
     *
     * @param metadata [Metadata] parameters for the generation.
     * @return [Sudoku] that needs to be solved.
     */
    fun generate(metadata: Metadata): Sudoku

    /**
     * Parameters for the puzzle generation. This class should not be reused between generations,
     * as it contains state!
     *
     * @param rowsPerBox number of rows in a single sudoku box. Default is [DEFAULT_ROWS]
     * @param columnsPerBox number of columns in a single sudoku box. Default is [DEFAULT_COLUMNS]
     * @param seed random value that will be used when generating the puzzle to ensure uniqueness
     * @param difficulty difficulty of the generated puzzle
     */
    data class Metadata(
        val rowsPerBox: Int = DEFAULT_ROWS,
        val columnsPerBox: Int = DEFAULT_COLUMNS,
        val seed: Long,
        val difficulty: Difficulty = Difficulty.EASY
    ) {

        companion object {
            const val DEFAULT_ROWS = 3
            const val DEFAULT_COLUMNS = 3
        }

        val rowsInGrid = rowsPerBox * columnsPerBox
        val columnsInGrid = rowsPerBox * columnsPerBox
        val highestValue = rowsPerBox * columnsPerBox
        val cellsInGrid = rowsInGrid * columnsInGrid
        val possibleValues = 1..highestValue

        // TODO: 2/14/21 Look into thread safety if it ends up being used in multiple threads.
        val random = Random(seed)

        /**
         * Difficulty of the generated puzzle.
         */
        enum class Difficulty {
            EASY,
            MEDIUM,
            HARD
        }
    }
}