package com.corootine.fuzzy.sudoku.domain.board

import com.corootine.fuzzy.sudoku.domain.board.SudokuPuzzle.Builder
import org.slf4j.LoggerFactory

/**
 * Immutable representation of a solved sudoku puzzle.
 * Use [Builder] to construct an instance of this class.
 */
class SudokuPuzzle private constructor(
    private val board: Array<IntArray>,
    private val cellSize: Int
) {

    private val boardSize = cellSize * cellSize

    /**
     * Gets the value associated to the given [row] and [column]. Index of both [row] and [column] must be between 0 and [cellSize]^2, exclusive.
     * If the index is not within these constraints, this method will throw an [IllegalArgumentException].
     *
     * @param row row inside the board, must be between 0 and [cellSize]^2, exclusive
     * @param column column inside the board, must be between 0 and [cellSize]^2, exclusive
     * @return value at the given position
     * @throws IllegalArgumentException in case row or column index is out of range
     */
    operator fun get(row: Int, column: Int): Int {
        require(row in 0 until boardSize)
        require(column in 0 until boardSize)

        return board[row][column]
    }

    /**
     * Builder for the [SudokuPuzzle].
     */
    class Builder(private val cellSize: Int) {

        private val logger = LoggerFactory.getLogger(this::class.java)

        private val boardSize = cellSize * cellSize
        private val board: Array<IntArray> = Array(boardSize) { IntArray(boardSize) { 0 } }

        /**
         * Attempts to set [value] in the board at [row] and [column].
         * In order for the value to be set successfully, it must comply with following requirements:
         * * Value must be unique for the cell it resides in
         * * Value must be unique for the row it resides in
         * * Value must be unique for the column it resides in
         *
         * If all of these conditions are met, the value will be set and this method will return true.
         * If any of the conditions are violated, the value will not be set and the method will return false.
         *
         * This method also has preconditions on passed arguments.
         * Both [row], [column] must be between 0 and [cellSize]^2, exclusive, while [value] has to be between 0 and [cellSize]^2, inclusive.
         * If any of the preconditions are not met, this method will fail with an [IllegalArgumentException].
         *
         * @param row row inside the board, must be between 0 and [cellSize]^2, exclusive
         * @param column column inside the board, must be between 0 and [cellSize]^2, exclusive
         * @param value value to set to the board, must be between 1 and [cellSize]^2, inclusive
         * @return true if value was set successfully, otherwise false
         * @throws IllegalArgumentException in case any of the preconditions are not met
         */
        fun set(row: Int, column: Int, value: Int): Boolean {
            require(row in 0 until boardSize)
            require(column in 0 until boardSize)
            require(value in 1..boardSize)

            return if (valueUniqueInCell(row, column, value)
                && valueUniqueInRow(row, value)
                && valueUniqueInColumn(column, value)
            ) {
                board[row][column] = value
                true
            } else {
                false
            }
        }

        /**
         * Builds the [SudokuPuzzle] from the [board].
         *
         * @return [SudokuPuzzle]
         */
        fun build(): SudokuPuzzle = SudokuPuzzle(board, cellSize)

        private fun valueUniqueInCell(row: Int, column: Int, value: Int): Boolean {
            val minRow = (row / cellSize) * cellSize
            val maxRow = minRow + 2
            val minColumn = (column / cellSize) * cellSize
            val maxColumn = minColumn + 2

            for (rowIndex in minRow..maxRow) {
                for (columnIndex in minColumn..maxColumn) {
                    if (board[rowIndex][columnIndex] == value) {
                        logger.debug("Value '$value' is not unique in cell at '[$row][$column]'.")
                        return false
                    }
                }
            }

            return true
        }

        private fun valueUniqueInRow(row: Int, value: Int): Boolean {
            for (columnValue in board[row]) {
                if (columnValue == value) {
                    logger.debug("Value '$value' is not unique in row '$row'.")
                    return false
                }
            }

            return true
        }

        private fun valueUniqueInColumn(column: Int, value: Int): Boolean {
            for (row in board.indices) {
                if (board[row][column] == value) {
                    logger.debug("Value '$value' is not unique in column '$column'.")
                    return false
                }
            }

            return true
        }
    }
}