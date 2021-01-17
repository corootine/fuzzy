package com.corootine.fuzzy.domain.sudoku

/**
 * Represents an immutable representation of a sudoku puzzle.
 */
class PuzzleBuilder(private val params: GenerationParams) {

    private var board = Array(params.rowsInPuzzle) { IntArray(params.columnsInPuzzle) { 0 } }

    /**
     * Gets the number at [row] and [column].
     *
     * Preconditions:
     * * [row] must be between 0 and [GenerationParams.rowsInPuzzle] (exclusive)
     * * [column] must be between 0 and [GenerationParams.columnsInPuzzle] (exclusive)
     *
     * @param row row inside the board
     * @param column column inside the board
     * @throws IllegalArgumentException in case any of the preconditions are not met
     */
    operator fun get(row: Int, column: Int): Int {
        require(row in 0 until params.rowsInPuzzle)
        require(column in 0 until params.columnsInPuzzle)

        return board[row][column]
    }

    /**
     * Attempts to set [number] in the board at [row] and [column].
     * In order for the value to be set successfully, it must comply with following requirements:
     * * Value must be unique for the row it should resides in
     * * Value must be unique for the column it should resides in
     * * Value must be unique for the cell it should resides in
     *
     * If all of these conditions are met, the value will be set and this method will return true.
     * If any of the conditions are violated, the value will not be set and the method will return false.
     *
     * Preconditions:
     * * [row] must be between 0 and [GenerationParams.rowsInPuzzle] (exclusive)
     * * [column] must be between 0 and [GenerationParams.columnsInPuzzle] (exclusive)
     * * [number] must be between 1 and [GenerationParams.highestNumber] (inclusive)
     *
     * @param row row inside the board
     * @param column column inside the board
     * @param number value to set to the board
     * @throws IllegalArgumentException in case any of the preconditions are not met
     */
    fun trySet(row: Int, column: Int, number: Int): Boolean {
        require(row in 0 until params.rowsInPuzzle)
        require(column in 0 until params.columnsInPuzzle)
        require(number in 1..params.highestNumber)

        return if (uniqueInRow(row, number)
            && uniqueInColumn(column, number)
            && uniqueInCell(row, column, number)
        ) {
            board[row][column] = number
            true
        } else {
            false
        }
    }

    /**
     * Removes the number at [row] and [column].
     *
     * Preconditions:
     * * [row] must be between 0 and [GenerationParams.rowsInPuzzle] (exclusive)
     * * [column] must be between 0 and [GenerationParams.columnsInPuzzle] (exclusive)
     *
     * @param row row inside the board
     * @param column column inside the board
     * @throws IllegalArgumentException in case any of the preconditions are not met
     */
    fun reset(row: Int, column: Int) {
        require(row in 0 until params.rowsInPuzzle)
        require(column in 0 until params.columnsInPuzzle)

        board[row][column] = 0
    }

    fun isComplete(): Boolean {
        for (row in 0 until params.rowsInPuzzle) {
            for (column in 0 until params.columnsInPuzzle) {
                if (board[row][column] == 0) {
                    return false
                }
            }
        }

        return true
    }

    private fun uniqueInCell(row: Int, column: Int, value: Int): Boolean {
        val minRowInCell = (row / params.rowsPerCell) * params.rowsPerCell
        val maxRowInCell = minRowInCell + (params.rowsPerCell - 1)
        val minColumnInCell = (column / params.columnsPerCell) * params.columnsPerCell
        val maxColumnInCell = minColumnInCell + (params.columnsPerCell - 1)

        for (rowIndex in minRowInCell..maxRowInCell) {
            for (columnIndex in minColumnInCell..maxColumnInCell) {
                if (board[rowIndex][columnIndex] == value) {
                    return false
                }
            }
        }

        return true
    }

    private fun uniqueInRow(row: Int, value: Int) =
        board[row].all { it != value }

    private fun uniqueInColumn(column: Int, value: Int) =
        board.indices.all { row -> board[row][column] != value }

}