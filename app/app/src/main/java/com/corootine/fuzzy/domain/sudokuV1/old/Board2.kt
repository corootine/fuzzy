package com.corootine.fuzzy.domain.sudokuV1.old

import com.corootine.fuzzy.domain.sudokuV1.PuzzleGenerator

// TODO: 1/13/21 introduce a boardBuilder, Board should be immutable
data class Board2(private val input: PuzzleGenerator.Input) {

    private var board = Array(input.boardSize) { IntArray(input.boardSize) { 0 } }

    fun clone(): Board2 {
        val clone = Board2(input)
        for (i in 0 until input.boardSize) {
            for (j in 0 until input.boardSize) {
                clone.board[i][j] = board[i][j]
            }
        }

        return clone
    }

    fun isFull(): Boolean {
        for (i in 0 until input.boardSize) {
            for (j in 0 until input.boardSize) {
                if (board[i][j] == 0) {
                    return false
                }
            }
        }

        return true
    }

    fun trySet(row: Int, column: Int, value: Int): Boolean {
        require(row in 0 until input.boardSize)
        require(column in 0 until input.boardSize)
        require(value in 1..input.maxValue)

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

    operator fun get(row: Int, column: Int): Int = board[row][column]

    fun reset(row: Int, column: Int) {
        require(row in 0 until input.boardSize)
        require(column in 0 until input.boardSize)

        board[row][column] = 0
    }

    override fun toString(): String {
        val builder = StringBuilder()

        for (row in board.indices) {
            if (row % input.rank == 0) {
                builder.appendLine()
            }

            for (column in board[row].indices) {
                if (column % input.rank == 0) {
                    builder.append(" ")
                }

                if (board[row][column] == 0) {
                    builder.append("  ")
                } else {
                    builder.append("${board[row][column]} ")
                }
            }

            builder.appendLine()
        }

        return builder.toString()
    }

    private fun valueUniqueInCell(row: Int, column: Int, value: Int): Boolean {
        val minRowInCell = (row / input.rank) * input.rank
        val maxRowInCell = minRowInCell + (input.rank - 1) // - 1 due to starting with index 0
        val minColumnInCell = (column / input.rank) * input.rank
        val maxColumnInCell = minColumnInCell + (input.rank - 1) // - 1 due to starting with index 0

        for (rowIndex in minRowInCell..maxRowInCell) {
            for (columnIndex in minColumnInCell..maxColumnInCell) {
                if (board[rowIndex][columnIndex] == value) {
                    return false
                }
            }
        }

        return true
    }

    private fun valueUniqueInRow(row: Int, value: Int) =
        board[row].all { it != value }

    private fun valueUniqueInColumn(column: Int, value: Int) =
        board.indices.all { row -> board[row][column] != value }

    fun cluesCount(): Int {
        var count = 0

        for (i in 0 until input.boardSize) {
            for (j in 0 until input.boardSize) {
                if (board[i][j] != 0) {
                    count++
                }
            }
        }

        return count
    }
}