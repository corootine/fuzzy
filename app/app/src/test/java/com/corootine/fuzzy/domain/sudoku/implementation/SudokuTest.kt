package com.corootine.fuzzy.domain.sudoku.implementation

import com.corootine.fuzzy.domain.sudoku.Sudoku
import com.corootine.fuzzy.domain.sudoku.SudokuGenerator
import org.junit.Assert.*
import org.junit.Test

abstract class SudokuTest {

    private val globalMetadata = SudokuGenerator.Metadata(seed = 123456)
    private val grid = arrayOf(
        arrayOf(2, 4, 6, 8, 3, 5, 1, 9, 7),
        arrayOf(0, 8, 9, 2, 1, 6, 4, 3, 5),
        arrayOf(0, 0, 1, 7, 4, 0, 2, 6, 8),

        arrayOf(0, 0, 5, 6, 8, 1, 9, 7, 3),
        arrayOf(8, 6, 3, 9, 7, 4, 5, 2, 1),
        arrayOf(1, 9, 7, 5, 2, 3, 8, 4, 6),

        arrayOf(3, 1, 2, 4, 6, 8, 7, 5, 9),
        arrayOf(0, 7, 8, 3, 5, 2, 6, 1, 4),
        arrayOf(6, 5, 4, 1, 9, 7, 3, 8, 2),
    )

    @Test
    fun `metadata - when created with metadata - expect same metadata back`() {
        val sudoku = createSudoku()

        assertEquals(globalMetadata, sudoku.metadata)
    }

    @Test
    fun `isComplete - when sudoku has empty cells - expect false`() {
        val sudoku = createSudoku(grid)

        assertFalse(sudoku.isComplete())
    }

    @Test
    fun `isComplete - when sudoku has no empty cells - expect true`() {
        val sudoku = createSudoku(grid)

        with(sudoku) {
            trySet(1, 0, 7)
            trySet(2, 0, 5)
            trySet(2, 1, 3)
            trySet(2, 5, 9)
            trySet(3, 0, 4)
            trySet(3, 1, 2)
            trySet(7, 0, 9)
        }

        assertTrue(sudoku.isComplete())
    }

    @Test
    fun `get - when row and column are in bounds - return cell`() {
        val sudoku = createSudoku(grid)

        val cell = sudoku.get(globalMetadata.rowsInGrid - 1, 0)

        assertTrue(cell.value == 6)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `get - when row is maxRow + 1 - expect exception`() {
        val sudoku = createSudoku(grid)

        sudoku.get(globalMetadata.rowsInGrid + 1, 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `get - when row is below 0 - expect exception`() {
        val sudoku = createSudoku(grid)

        sudoku.get(-1, 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `get - when column is maxColumn + 1 - expect exception`() {
        val sudoku = createSudoku(grid)

        sudoku.get(0, globalMetadata.columnsInGrid + 1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `get - when column is below 0 - expect exception`() {
        val sudoku = createSudoku(grid)

        sudoku.get(0, -1)
    }

    @Test
    fun `trySet - when cell is empty and value is unique - expect to succeed`() {
        val sudoku = createSudoku(grid)

        assertTrue(sudoku.trySet(2, 0, 5))
    }

    @Test
    fun `trySet - when column already contains the same value - expect to fail`() {
        val sudoku = createSudoku(grid)

        assertFalse(sudoku.trySet(2, 0, 3))
    }

    @Test
    fun `trySet - when row already contains the same value - expect to fail`() {
        val sudoku = createSudoku(grid)

        assertFalse(sudoku.trySet(2, 0, 7))
    }

    @Test
    fun `trySet - when box already contains the same value - expect to fail`() {
        val sudoku = createSudoku(grid)

        assertFalse(sudoku.trySet(2, 0, 9))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `trySet - when cell is a clue - expect to fail`() {
        val sudoku = createSudoku(grid)

        sudoku.trySet(0, 0, 1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `trySet - when row is maxRow + 1 - expect exception`() {
        val sudoku = createSudoku(grid)

        sudoku.trySet(globalMetadata.rowsInGrid + 1, 0, 1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `trySet - when row is below 0 - expect exception`() {
        val sudoku = createSudoku(grid)

        sudoku.trySet(-1, 0, 1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `trySet - when column is maxColumn + 1 - expect exception`() {
        val sudoku = createSudoku(grid)

        sudoku.trySet(0, globalMetadata.columnsInGrid + 1, 1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `trySet - when column is below 0 - expect exception`() {
        val sudoku = createSudoku(grid)

        sudoku.trySet(0, -1, 1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `trySet - when value is 0 - expect to fail`() {
        val sudoku = createSudoku(grid)

        sudoku.trySet(2, 0, 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `trySet - when value is maxValue + 1 - expect to fail`() {
        val sudoku = createSudoku(grid)

        sudoku.trySet(2, 0, globalMetadata.highestValue + 1)
    }

    @Test
    fun `reset - when cell is not a clue - expect to succeed`() {
        val sudoku = createSudoku(grid)

        sudoku.trySet(2, 0, 5)
        assertFalse(sudoku.get(2, 0).isEmpty)
        assertTrue(sudoku.get(2, 0).value == 5)

        sudoku.reset(2, 0)
        assertTrue(sudoku.get(2, 0).isEmpty)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `reset - when cell is a clue - expect to fail`() {
        val sudoku = createSudoku(grid)

        sudoku.reset(0, 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `reset - when row is maxRow + 1 - expect exception`() {
        val sudoku = createSudoku(grid)

        sudoku.reset(globalMetadata.rowsInGrid + 1, 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `reset - when row is below 0 - expect exception`() {
        val sudoku = createSudoku(grid)

        sudoku.reset(-1, 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `reset - when column is maxColumn + 1 - expect exception`() {
        val sudoku = createSudoku(grid)

        sudoku.reset(0, globalMetadata.columnsInGrid + 1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `reset - when column is below 0 - expect exception`() {
        val sudoku = createSudoku(grid)

        sudoku.reset(0, -1)
    }

    abstract fun createSudoku(
        grid: Array<Array<Int>> = this.grid,
        metadata: SudokuGenerator.Metadata = globalMetadata
    ): Sudoku
}
