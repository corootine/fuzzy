package com.corootine.fuzzy.domain.sudoku

class Puzzle(
    private val input: PuzzleGenerator.Input,
    board: Board
) {

    private val puzzle = Array(input.boardSize) { IntArray(input.boardSize) { 0 } }

    init {
        for (i in 0 until input.boardSize) {
            for (j in 0 until input.boardSize) {
                puzzle[i][j] = board[i, j]
            }
        }
    }

    fun tryRemoveClue(row: Int, column: Int): Boolean {
        return true
    }

    override fun toString(): String {
        val builder = StringBuilder()

        for (row in puzzle.indices) {
            if (row % input.rank == 0) {
                builder.appendLine()
            }

            for (column in puzzle[row].indices) {
                if (column % input.rank == 0) {
                    builder.append(" ")
                }

                builder.append("${puzzle[row][column]} ")
            }

            builder.appendLine()
        }

        return builder.toString()
    }
}