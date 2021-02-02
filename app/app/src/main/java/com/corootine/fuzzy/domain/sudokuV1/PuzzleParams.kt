package com.corootine.fuzzy.domain.sudokuV1

const val DEFAULT_ROWS = 3
const val DEFAULT_COLUMNS = 3

/**
 * Parameters for the puzzle generation.
 *
 * @param rowsPerCell number of rows in a single sudoku cell. Default is [DEFAULT_ROWS]
 * @param columnsPerCell number of columns in a single sudoku cell. Default is [DEFAULT_COLUMNS]
 * @param seed random value that will be used when generating the puzzle to ensure uniqueness..
 * @param difficulty difficulty of the generated puzzle
 */
data class PuzzleParams(
    val rowsPerCell: Int = DEFAULT_ROWS,
    val columnsPerCell: Int = DEFAULT_COLUMNS,
    val seed: Long,
    val difficulty: Difficulty
) {

    val rowsInGrid = rowsPerCell * columnsPerCell
    val columnsInGrid = rowsPerCell * columnsPerCell
    val highestNumber = rowsPerCell * columnsPerCell
    val fieldsInPuzzle = rowsInGrid * columnsInGrid
}

/**
 * Difficulty of the generated puzzle.
 */
enum class Difficulty {
    EASY,
    MEDIUM,
    HARD
}