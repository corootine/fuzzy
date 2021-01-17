package com.corootine.fuzzy.domain.sudoku

const val DEFAULT_ROWS = 3
const val DEFAULT_COLUMNS = 3

/**
 * Parameters for the puzzle generation.
 *
 * @param rowsPerCell number of rows in a single sudoku cell. Default is [DEFAULT_ROWS]
 * @param columnsPerCell number of columns in a single sudoku cell. Default is [DEFAULT_COLUMNS]
 * @param seed random seed that will be used when generating the puzzle. This ensure a unique puzzle.
 * @param difficulty difficulty of the generated puzzle
 */
data class GenerationParams(
    val rowsPerCell: Int = DEFAULT_ROWS,
    val columnsPerCell: Int = DEFAULT_COLUMNS,
    val seed: Long,
    val difficulty: Difficulty
) {

    /**
     * Total number of rows in the puzzle.
     */
    val rowsInPuzzle = rowsPerCell * columnsPerCell

    /**
     * Total number of columns in the puzzle.
     */
    val columnsInPuzzle = rowsPerCell * columnsPerCell

    /**
     * Highest number possible in the puzzle.
     */
    val highestNumber = rowsPerCell * columnsPerCell

    /**
     * Total count of fields in the puzzle.
     */
    val fieldsInPuzzle = rowsInPuzzle * columnsInPuzzle
}

/**
 * Difficulty of the generated puzzle.
 */
enum class Difficulty {
    EASY,
    MEDIUM,
    HARD
}