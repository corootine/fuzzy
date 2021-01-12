package com.corootine.fuzzy.domain.sudoku

import org.junit.Test
import kotlin.random.Random

class BoardGeneratorTest {

    @Test
    fun test() {
        val random = Random.Default

        val boardGenerator = BoardGenerator(PuzzleGenerator.Input(3, random.nextLong()))
        val board = boardGenerator.generate()

//        val puzzle = Puzzle.from(PuzzleGenerator.Input(3, random.nextLong()), board)

        println(board.toString())
        println()
//        println(puzzle.toString())
    }
}