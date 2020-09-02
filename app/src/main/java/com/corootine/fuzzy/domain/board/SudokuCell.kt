package com.corootine.fuzzy.domain.board

import timber.log.Timber

private const val SIZE = 3

/**
 * Immutable representation of a sudoku cell of size [SIZE]x[SIZE].
 */
// TODO: 03/09/20 Convert this array to an immutable collection and provide accessors
class SudokuCell(cells: Array<Array<Int>>) {

    class Builder {

        private val cells = arrayOf(
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0)
        )

        /**
         * Attempts to set [value] for the given [row] and [column]. Specified [row] and [column] must be between 0 and [SIZE].
         * In case this precondition is not met, this method will fail with an [IllegalArgumentException].
         * If the provided value already appears somewhere else in this cell, it will not be set again and this method will return false.
         * If the value appears for the first time, it will be set for the given [row] and [column] and this method will return true.
         */
        fun set(row: Int, column: Int, value: Int): Boolean {
            require(row in 0 until SIZE)
            require(column in 0 until SIZE)

            return if (valueExists(value)) {
                Timber.d("Cell already contains the value '$value'.")
                false
            } else {
                Timber.d("Setting value '$value' to [$row][$column].")
                cells[row][column] = value
                true
            }
        }

        private fun valueExists(value: Int): Boolean {
            for (row in cells) {
                for (currentValue in row) {
                    if (currentValue == value) {
                        return true
                    }
                }
            }

            return false
        }

        fun build() = SudokuCell(cells)
    }
}