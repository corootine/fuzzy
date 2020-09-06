package com.corootine.fuzzy.domain.board

import org.junit.Assert.assertEquals
import org.junit.Test

class SudokuCellTest {
    
    @Test(expected = IllegalArgumentException::class)
    fun `should not allow negative row size`() {
        // given
        val builder = SudokuCell.Builder(3)

        // when
        builder.set(-1, 2, 5)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should not allow negative column size`() {
        // given
        val builder = SudokuCell.Builder(3)

        // when
        builder.set(2, -1, 5)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should not allow row size greater than 2`() {
        // given
        val builder = SudokuCell.Builder(3)

        // when
        builder.set(3, 2, 5)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should not allow column size greater than 2`() {
        // given
        val builder = SudokuCell.Builder(3)

        // when
        builder.set(2, 3, 5)
    }

    @Test
    fun `should create a sudoku cell`() {
        // given
        val builder = SudokuCell.Builder(3)
        val cell = arrayOf(
            arrayOf(1, 2, 4),
            arrayOf(5, 8, 9),
            arrayOf(6, 7, 3)
        )

        // when
        for (row in cell.indices) {
            for (column in cell[row].indices) {
                builder.set(row, column, cell[row][column])
            }
        }
        val sudokuCell = builder.build()

        // then
        for (row in cell.indices) {
            for (column in cell[row].indices) {
                assertEquals(cell[row][column], sudokuCell[row, column])
            }
        }
    }

    @Test(expected = IllegalStateException::class)
    fun `should not allow duplicates`() {
        // given
        val builder = SudokuCell.Builder(3)

        // when
        builder.build()
    }
}