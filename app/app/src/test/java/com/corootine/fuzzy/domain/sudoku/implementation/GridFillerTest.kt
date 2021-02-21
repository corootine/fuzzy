package com.corootine.fuzzy.domain.sudoku.implementation

import com.corootine.fuzzy.domain.sudoku.api.SudokuGenerator
import com.corootine.fuzzy.domain.sudoku.api.SudokuGenerator.Metadata.Difficulty
import org.junit.Assert.*
import org.junit.Test

class GridFillerTest {

    private val gridFiller = GridFiller()

    @Test
    fun `fill - expect complete puzzle`() {
        val sudokuBuilder = emptySudokuBuilder()

        gridFiller.fill(sudokuBuilder)

        assertTrue(sudokuBuilder.isComplete())
    }

    @Test
    fun `fill - with same seed - expect same puzzle`() {
        val sudokuBuilderOne = emptySudokuBuilder()
        val sudokuBuilderTwo = emptySudokuBuilder()

        with(gridFiller) {
            fill(sudokuBuilderOne)
            fill(sudokuBuilderTwo)
        }

        assertEquals(sudokuBuilderOne, sudokuBuilderTwo)
    }

    @Test
    fun `fill - with different seed - expect different puzzle`() {
        val sudokuBuilderOne = emptySudokuBuilder(seed = 123456)
        val sudokuBuilderTwo = emptySudokuBuilder(seed = 654321)

        with(gridFiller) {
            fill(sudokuBuilderOne)
            fill(sudokuBuilderTwo)
        }

        assertNotEquals(sudokuBuilderOne, sudokuBuilderTwo)
    }

    private fun emptySudokuBuilder(seed: Long = 123456) = SudokuBuilder(
        SudokuGenerator.Metadata(
            seed = seed,
            difficulty = Difficulty.EASY
        )
    )
}