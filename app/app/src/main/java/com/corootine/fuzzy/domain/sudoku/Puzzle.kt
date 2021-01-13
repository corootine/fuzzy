package com.corootine.fuzzy.domain.sudoku

// TODO: 1/13/21 introduce puzzleBuilder, Puzzle should be immutable
class Puzzle private constructor(
    private val input: PuzzleGenerator.Input,
    private val solvedBoard: Board,
    private var puzzleBoard: Board
) {

    companion object {

        fun create(input: PuzzleGenerator.Input, solvedBoard: Board): Puzzle {
            return Puzzle(
                input,
                solvedBoard,
                solvedBoard.clone()
            )
        }
    }

    fun tryRemoveClue(row: Int, column: Int): Boolean {
        puzzleBoard.reset(row, column)

        return if (isPuzzleWellFormed(puzzleBoard.clone())) {
            true
        } else {
            puzzleBoard.trySet(row, column, solvedBoard[row, column])
            false
        }
    }

    private fun isPuzzleWellFormed(board: Board): Boolean {
        val (row, column) = findNextRowAndColumn(board)

        return if (row == BoardGenerator.NO_ROW && column == BoardGenerator.NO_COLUMN) {
            true
        } else {
            for (attempt in 1..input.maxValue) {
                if (board.trySet(row, column, attempt)) {
                    if (isPuzzleWellFormed(board)) {
                        return true
                    } else {
                        board.reset(row, column)
                    }
                }
            }

            false
        }
    }

    private fun findNextRowAndColumn(board: Board): Pair<Int, Int> {
        for (i in 0 until input.boardSize) {
            for (j in 0 until input.boardSize) {
                if (board[i, j] == 0) {
                    return Pair(i, j)
                }
            }
        }

        return Pair(BoardGenerator.NO_ROW, BoardGenerator.NO_COLUMN)
    }

    override fun toString(): String {
        return puzzleBoard.toString()
    }
}