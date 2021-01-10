package com.corootine.fuzzy.domain.puzzle

import org.junit.Test

class BoardGeneratorTest {

    @Test
    fun test() {
        val boardGenerator = BoardGenerator(PuzzleGenerator.Input(3, 114423))
        val board = boardGenerator.generate()

        println(board.toString())
    }
}