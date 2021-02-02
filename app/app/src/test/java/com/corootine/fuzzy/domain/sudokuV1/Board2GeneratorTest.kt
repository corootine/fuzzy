package com.corootine.fuzzy.domain.sudokuV1

import org.junit.Test
import kotlin.random.Random

class Board2GeneratorTest {

    @Test
    fun test() {
        val random = Random.Default

        val input = PuzzleGenerator.Input(3, random.nextLong(), difficulty = PuzzleGenerator.Input.Difficulty.EASY)

//        val board = Board(input)
//        board.trySet(0, 0, 2)
//        board.trySet(0, 1, 3)
//        board.trySet(0, 2, 4)
//        board.trySet(0, 3, 1)
//
//        board.trySet(1, 0, 1)
//        board.trySet(1, 1, 4)
//        board.trySet(1, 2, 2)
//        board.trySet(1, 3, 3)
//
//        board.trySet(2, 0, 4)
//        board.trySet(2, 1, 1)
//        board.trySet(2, 2, 3)
//        board.trySet(2, 3, 2)
//
//        board.trySet(3, 0, 3)
//        board.trySet(3, 1, 2)
//        board.trySet(3, 2, 1)
//        board.trySet(3, 3, 4)

//        val puzzle = Puzzle.create(input, board)
//        puzzle.tryRemoveClue(0, 3)
//        puzzle.tryRemoveClue(0, 2)
//        puzzle.tryRemoveClue(3, 2)

        val puzzleGenerator = PuzzleGenerator()
        val puzzle = puzzleGenerator.generate(input)

        println(puzzle.toString())

        println(puzzle.puzzleBoard.cluesCount())
    }
}