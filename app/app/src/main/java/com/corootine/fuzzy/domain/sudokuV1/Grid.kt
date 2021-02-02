package com.corootine.fuzzy.domain.sudokuV1

class Grid(private val params: PuzzleParams) {

    private val grid = Array(params.rowsInGrid) { row ->
        Array(params.columnsInGrid) { column -> Cell.empty(row, column) }
    }

    /**
     * Gets the [Cell] at [row] and [column].
     *
     * Preconditions:
     * * [row] must be between 0 and [PuzzleParams.rowsInGrid] (exclusive)
     * * [column] must be between 0 and [PuzzleParams.columnsInGrid] (exclusive)
     *
     * @param row row inside the board
     * @param column column inside the board
     * @throws IllegalArgumentException in case any of the preconditions are not met
     */
    fun get(row: Int, column: Int): Cell {
        require(row in 0 until params.rowsInGrid)
        require(column in 0 until params.columnsInGrid)

        return grid[row][column]
    }

    /**
     * Attempts to set [value] in the board at [row] and [column].
     * In order for the value to be set successfully, it must comply with following requirements:
     * * Value must be unique for the row it should resides in
     * * Value must be unique for the column it should resides in
     * * Value must be unique for the cell it should resides in
     *
     * If all of these conditions are met, the value will be set and this method will return true.
     * If any of the conditions are violated, the value will not be set and the method will return false.
     *
     * Preconditions:
     * * [row] must be between 0 and [PuzzleParams.rowsInGrid] (exclusive)
     * * [column] must be between 0 and [PuzzleParams.columnsInGrid] (exclusive)
     * * [value] must be between 1 and [PuzzleParams.highestNumber] (inclusive)
     *
     * @param row row inside the board
     * @param column column inside the board
     * @param value value to set to the board
     * @throws IllegalArgumentException in case any of the preconditions are not met
     */
    fun trySet(row: Int, column: Int, value: Int): Boolean {
        require(row in 0 until params.rowsInGrid)
        require(column in 0 until params.columnsInGrid)
        require(value in 1..params.highestNumber)

        return if (uniqueInRow(row, value)
            && uniqueInColumn(column, value)
            && uniqueInBox(row, column, value)
        ) {
            grid[row][column] = Cell(row, column, value, true)
            true
        } else {
            false
        }
    }

    /**
     * Resets the cell at [row] and [column].
     *
     * Preconditions:
     * * [row] must be between 0 and [PuzzleParams.rowsInGrid] (exclusive)
     * * [column] must be between 0 and [PuzzleParams.columnsInGrid] (exclusive)
     *
     * @param row row inside the board
     * @param column column inside the board
     * @throws IllegalArgumentException in case any of the preconditions are not met
     */
    fun reset(row: Int, column: Int) {
        require(row in 0 until params.rowsInGrid)
        require(column in 0 until params.columnsInGrid)

        grid[row][column] = Cell.empty(row, column)
    }

    /**
     * Checks if the puzzle is complete. A puzzle is considered complete if it does not
     * contain any empty cells.
     *
     * @return {@code true} if the puzzle is complete, otherwise {@code false}
     */
    fun isComplete(): Boolean {
        for (row in 0 until params.rowsInGrid) {
            for (column in 0 until params.columnsInGrid) {
                if (grid[row][column].isEmpty()) {
                    return false
                }
            }
        }

        return true
    }

    private fun uniqueInBox(row: Int, column: Int, value: Int): Boolean {
        val minRowInCell = (row / params.rowsPerCell) * params.rowsPerCell
        val maxRowInCell = minRowInCell + (params.rowsPerCell - 1)
        val minColumnInCell = (column / params.columnsPerCell) * params.columnsPerCell
        val maxColumnInCell = minColumnInCell + (params.columnsPerCell - 1)

        for (rowIndex in minRowInCell..maxRowInCell) {
            for (columnIndex in minColumnInCell..maxColumnInCell) {
                if (grid[rowIndex][columnIndex].value == value) {
                    return false
                }
            }
        }

        return true
    }

    private fun uniqueInRow(row: Int, value: Int) =
        grid[row].all { it.value != value }

    private fun uniqueInColumn(column: Int, value: Int) =
        grid.indices.all { row -> grid[row][column].value != value }
}

/**
 * Describes a single sudoku cell, which is represented by a number and a flag that indicates
 * whether the cell is a given clue and cannot be edited.
 */
data class Cell(val row: Int, val column: Int, val value: Int, var clue: Boolean) {

    fun isEmpty() = value == 0

    companion object {
        fun empty(row: Int, column: Int) = Cell(row, column, 0, false)
    }
}
