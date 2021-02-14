package com.corootine.fuzzy.domain.sudoku.implementation

import com.corootine.fuzzy.domain.sudoku.api.SudokuGenerator
import kotlin.random.Random

class GridFiller {

    companion object {
        private const val NO_ROW = -1
        private const val NO_COLUMN = -1
    }

    /**
     * Fills in a grid with random values, thus creating a solved sudoku puzzle.
     * The solved sudoku puzzle can be used to derive a puzzle with clues, that needs to be solved.
     */
    fun fill(sudokuBuilder: SudokuBuilder) {
        val random = sudokuBuilder.metadata.random
        fillRecursively(sudokuBuilder.metadata, sudokuBuilder, random)
    }

    private fun fillRecursively(
        metadata: SudokuGenerator.Metadata,
        sudokuBuilder: SudokuBuilder,
        random: Random
    ): Boolean {
        val (row, column) = findNextRowAndColumn(metadata, sudokuBuilder)

        return if (row == NO_ROW && column == NO_COLUMN) {
            true
        } else {
            val alreadyVisitedNumbers = arrayListOf<Int>()

            metadata.possibleNumbers.forEach { _ ->
                val number = metadata.possibleNumbers
                    .minus(alreadyVisitedNumbers)
                    .random(random)

                if (sudokuBuilder.trySet(row, column, number)) {
                    if (fillRecursively(metadata, sudokuBuilder, random)) {
                        return true
                    } else {
                        sudokuBuilder.reset(row, column)
                        alreadyVisitedNumbers.add(number)
                    }
                }
            }

            false
        }
    }

    private fun findNextRowAndColumn(
        metadata: SudokuGenerator.Metadata,
        sudokuBuilder: SudokuBuilder
    ): Pair<Int, Int> {
        // TODO: We can improve the implementation by already predicting the next row/column based
        //  on the previous visited one. This will save some iterations.
        for (row in 0 until metadata.rowsInGrid) {
            for (column in 0 until metadata.columnsInGrid) {
                if (sudokuBuilder.get(row, column).value == 0) {
                    return Pair(row, column)
                }
            }
        }

        return Pair(NO_ROW, NO_COLUMN)
    }
}