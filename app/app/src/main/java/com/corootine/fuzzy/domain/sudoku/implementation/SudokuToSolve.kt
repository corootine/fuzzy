package com.corootine.fuzzy.domain.sudoku.implementation

import com.corootine.fuzzy.domain.sudoku.api.Sudoku
import com.corootine.fuzzy.domain.sudoku.api.Sudoku.Cell
import com.corootine.fuzzy.domain.sudoku.api.SudokuGenerator

/**
 * Represents a Sudoku puzzle which is ready to be solved. It is derived from the [SudokuBuilder]
 * and has restrictions on fields which can be edited. Only non-clue fields can be edited.
 */
class SudokuToSolve(private val sudokuBuilder: SudokuBuilder) : Sudoku {

    override fun get(row: Int, column: Int): Cell = sudokuBuilder.get(row, column)

    override fun trySet(row: Int, column: Int, value: Int): Boolean {
        val cell = sudokuBuilder.get(row, column)
        return cell.ifNotClue { sudokuBuilder.trySet(row, column, value) }
    }

    override fun reset(row: Int, column: Int) {
        val cell = sudokuBuilder.get(row, column)
        cell.ifNotClue { sudokuBuilder.reset(row, column) }
    }

    override fun isComplete(): Boolean = sudokuBuilder.isComplete()

    override val metadata: SudokuGenerator.Metadata = sudokuBuilder.metadata

    override fun iterator(): Iterator<Cell> {
        TODO("Not yet implemented")
    }

    override fun toString(): String = sudokuBuilder.toString()

    private fun <T> Cell.ifNotClue(function: () -> T): T {
        if (!clue) {
            return function.invoke()
        } else {
            throw IllegalArgumentException("Clue fields are immutable.")
        }
    }
}
