package com.corootine.fuzzy.domain.sudoku

import com.corootine.fuzzy.domain.sudokuV1.PuzzleParams

abstract class Puzzle(protected val params: PuzzleParams) {

    protected var board = Array(params.rowsInGrid) { IntArray(params.columnsInGrid) { 0 } }

    operator fun get(row: Int, column: Int): Int {
        require(row in 0 until params.rowsInGrid)
        require(column in 0 until params.columnsInGrid)

        return board[row][column]
    }

    fun trySet(row: Int, column: Int, number: Int): Boolean {
        require(row in 0 until params.rowsInGrid)
        require(column in 0 until params.columnsInGrid)
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

    fun reset(row: Int, column: Int) {
        require(row in 0 until params.rowsInGrid)
        require(column in 0 until params.columnsInGrid)

        board[row][column] = 0
    }

    protected fun isComplete(): Boolean {
        for (row in 0 until params.rowsInGrid) {
            for (column in 0 until params.columnsInGrid) {
                if (board[row][column] == 0) {
                    return false
                }
            }
        }

        return true
    }

    protected fun uniqueInCell(row: Int, column: Int, value: Int): Boolean {
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

    protected fun uniqueInRow(row: Int, value: Int) =
        board[row].all { it != value }

    protected fun uniqueInColumn(column: Int, value: Int) =
        board.indices.all { row -> board[row][column] != value }
}

sealed class SolvedPuzzle: Puzzle() {



}

sealed class PuzzleToSolve: Puzzle() {

}
