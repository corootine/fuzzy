package com.corootine.fuzzy.domain.sudokuV1

class Puzzle(private val grid: Grid) {

    operator fun get(row: Int, column: Int): Cell {
        return grid.get(row, column)
    }

    fun trySet(row: Int, column: Int, value: Int): Boolean {
        val cell = grid.get(row, column)
        return cell.assertNotClue { grid.trySet(row, column, value) }
    }

    fun reset(row: Int, column: Int) {
        val cell = grid.get(row, column)
        cell.assertNotClue { grid.reset(row, column) }
    }

    fun isComplete(): Boolean = grid.isComplete()

    private fun <T> Cell.assertNotClue(function: () -> T): T {
        if (!clue) {
            return function.invoke()
        } else {
            throw IllegalArgumentException("Clue fields are immutable.")
        }
    }
}
