package com.corootine.fuzzy.domain.puzzle

data class Board(private val input: PuzzleGenerator.Input) {

    private val board: Array<IntArray> = Array(input.size) { IntArray(input.size) { 0 } }

    fun trySet(row: Int, column: Int, value: Int): Boolean {
        require(row in 0 until input.size)
        require(column in 0 until input.size)
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
        require(row in 0 until input.size)
        require(column in 0 until input.size)

        board[row][column] = 0
    }

    override fun toString(): String {
        val builder = StringBuilder()

        for (row in board.indices) {
            for (column in board[row].indices) {
                builder.append("${board[row][column]} ")
            }

            builder.appendLine()
        }

        return builder.toString()
    }

    private fun valueUniqueInCell(row: Int, column: Int, value: Int): Boolean {
        val minRowInCell = (row / input.rank) * input.rank
        val maxRowInCell = minRowInCell + (input.rank - 1)
        val minColumnInCell = (column / input.rank) * input.rank
        val maxColumnInCell = minColumnInCell + (input.rank - 1)

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
}