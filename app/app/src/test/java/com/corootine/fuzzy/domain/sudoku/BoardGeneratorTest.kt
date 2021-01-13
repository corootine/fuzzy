package com.corootine.fuzzy.domain.sudoku

import org.junit.Test
import kotlin.random.Random

class BoardGeneratorTest {

    @Test
    fun test() {
        val random = Random.Default

        val input = PuzzleGenerator.Input(3, random.nextLong())
        val boardGenerator = BoardGenerator(input)
        val board = boardGenerator.generate()

        val puzzleGenerator = PuzzleGenerator()
        val puzzle = puzzleGenerator.generate(input)

        println(board.toString())
        println()
        println(puzzle.toString())
    }
}