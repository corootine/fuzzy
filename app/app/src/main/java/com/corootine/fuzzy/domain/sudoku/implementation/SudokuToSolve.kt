package com.corootine.fuzzy.domain.sudoku.implementation

import com.corootine.fuzzy.domain.sudoku.Sudoku
import com.corootine.fuzzy.domain.sudoku.Sudoku.Cell
import com.corootine.fuzzy.domain.sudoku.SudokuGenerator

/**
 * Represents a Sudoku puzzle which is ready to be solved. It is derived from the [SudokuBuilder]
 * and has restrictions on fields which can be edited. Only non-clue fields can be changed.
 * The passed [SudokuBuilder] must not be a completed sudoku nor an empty one.
 */
class SudokuToSolve(private val sudokuBuilder: SudokuBuilder) : Sudoku {

    init {
        require(!sudokuBuilder.isComplete())
        require(!sudokuBuilder.isEmpty)

        for (row in 0 until sudokuBuilder.metadata.rowsInGrid) {
            for (column in 0 until sudokuBuilder.metadata.columnsInGrid) {
                sudokuBuilder.get(row, column).apply {
                    if (!isEmpty) {
                        isClue = true
                    }
                }
            }
        }
    }

    override val metadata: SudokuGenerator.Metadata = sudokuBuilder.metadata

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

    override fun iterator(): Iterator<Cell> = sudokuBuilder.iterator()

    private fun <T> Cell.ifNotClue(function: () -> T): T {
        if (!isClue) {
            return function.invoke()
        } else {
            throw IllegalArgumentException("Clue fields are immutable.")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SudokuToSolve

        if (sudokuBuilder != other.sudokuBuilder) return false

        return true
    }

    override fun hashCode(): Int {
        return sudokuBuilder.hashCode()
    }

    override fun toString(): String = sudokuBuilder.toString()
}
