package com.corootine.fuzzy.domain.sudoku.implementation

import com.corootine.fuzzy.domain.sudoku.api.Sudoku
import com.corootine.fuzzy.domain.sudoku.api.Sudoku.Cell
import com.corootine.fuzzy.domain.sudoku.api.SudokuGenerator

/**
 * An empty grid that is used to build a Sudoku puzzle from scratch.
 * It has loosened restrictions on what fields can be edited e.g. you are allowed to change any
 * field in the grid. Once completed, this one is fed into [SudokuToSolve] which builds a Sudoku
 * puzzle that has more restrictions and is ready to be solved.
 */
class SudokuBuilder(override val metadata: SudokuGenerator.Metadata) : Sudoku {

    private val grid = Array(metadata.rowsInGrid) { row ->
        Array(metadata.columnsInGrid) { column -> Cell.empty(row, column) }
    }

    override fun get(row: Int, column: Int): Cell {
        require(row in 0 until metadata.rowsInGrid)
        require(column in 0 until metadata.columnsInGrid)

        return grid[row][column]
    }

    override fun trySet(row: Int, column: Int, value: Int): Boolean {
        require(row in 0 until metadata.rowsInGrid)
        require(column in 0 until metadata.columnsInGrid)
        require(value in 1..metadata.highestNumber)

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

    override fun reset(row: Int, column: Int) {
        require(row in 0 until metadata.rowsInGrid)
        require(column in 0 until metadata.columnsInGrid)

        grid[row][column] = Cell.empty(row, column)
    }

    override fun isComplete(): Boolean {
        for (row in 0 until metadata.rowsInGrid) {
            for (column in 0 until metadata.columnsInGrid) {
                if (grid[row][column].isEmpty()) {
                    return false
                }
            }
        }

        return true
    }

    override fun iterator(): Iterator<Cell> {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        val builder = StringBuilder()

        for (row in 0 until metadata.rowsInGrid) {
            for (column in 0 until metadata.columnsInGrid) {
                if (!get(row, column).isEmpty()) {
                    builder.append("${get(row, column).value} ")
                } else {
                    builder.append("  ")
                }

                if ((column + 1) % metadata.columnsPerBox == 0) {
                    builder.append("  ")
                }
            }

            if ((row + 1) % metadata.rowsPerBox == 0) {
                builder.append("\n")
            }
            builder.append("\n")
        }

        return builder.toString()
    }

    private fun uniqueInBox(row: Int, column: Int, value: Int): Boolean {
        val minRowInCell = (row / metadata.rowsPerBox) * metadata.rowsPerBox
        val maxRowInCell = minRowInCell + (metadata.rowsPerBox - 1)
        val minColumnInCell = (column / metadata.columnsPerBox) * metadata.columnsPerBox
        val maxColumnInCell = minColumnInCell + (metadata.columnsPerBox - 1)

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