package com.corootine.fuzzy.domain.board

import com.corootine.fuzzy.domain.board.SudokuCell.Builder
import timber.log.Timber

/**
 * Immutable representation of a sudoku cell. Use [Builder] to construct an instance of this class.
 */
class SudokuCell private constructor(private val cell: Array<IntArray>, private val size: Int) {

    /**
     * Gets a value associated to the given row and column. Index of row and column must be between 0 and [size].
     * If the index is not within these constraints, this method will throw an [IllegalArgumentException].
     *
     * @throws IllegalArgumentException in case row or column index is out of range
     */
    operator fun get(row: Int, column: Int): Int {
        require(row in 0 until size)
        require(column in 0 until size)

        return cell[row][column]
    }

    class Builder(private val size: Int) {

        private var cell: Array<IntArray> = Array(size) { IntArray(3) { 0 } }

        /**
         * Attempts to set [value] for the given [row] and [column]. Specified [row] and [column]
         * must be between 0 and [size]. In case this precondition is not met, this method
         * will fail with an [IllegalArgumentException].
         *
         * @param row row inside the cell, must be between 0 and [size]
         * @param column column inside the cell, must be between 0 and [size]
         * @throws IllegalArgumentException in case of invalid row or column size
         */
        fun set(row: Int, column: Int, value: Int) {
            require(row in 0 until size)
            require(column in 0 until size)

            Timber.d("Setting value '$value' to [$row][$column].")
            cell[row][column] = value
        }

        /**
         * Builds the [SudokuCell]. This method will check that all values inside this cell are
         * unique and there are no duplicates. If there are duplicates, this method will fail with
         * an [IllegalStateException]
         *
         * @throws IllegalStateException in case of duplicate values.
         */
        fun build(): SudokuCell {
            val values = HashSet<Int>()
            for (row in cell) {
                for (value in row) {
                    if (values.contains(value)) {
                        val exception = IllegalStateException("Cannot build due to duplicate value '$value'.")
                        Timber.e(exception)
                        throw exception
                    } else {
                        values.add(value)
                    }
                }
            }

            return SudokuCell(cell, size)
        }
    }
}
