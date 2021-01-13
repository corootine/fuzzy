package com.corootine.fuzzy.domain.sudoku

import kotlin.random.Random

class PuzzleGenerator {

    fun generate(input: Input): Puzzle {
        val boardGenerator = BoardGenerator(input)
        val board = boardGenerator.generate()

        return generatePuzzle(input, board)
    }

    private fun generatePuzzle(input: Input, board: Board): Puzzle {
        val random = Random(input.seed)
        var clues = input.boardSize * input.boardSize
        val puzzle = Puzzle.create(input, board)

        while (clues >= input.difficulty.expectedClues) {
            val row = random.nextInt(input.boardSize)
            val column = random.nextInt(input.boardSize)

            if (puzzle.tryRemoveClue(row, column)) {
                clues--
            }
        }

        return puzzle
    }

    data class Input(
        val rank: Int = 3,
        val seed: Long,
        val difficulty: Difficulty = Difficulty.MEDIUM
    ) {

        val boardSize = rank * rank
        val maxValue = rank * rank

        enum class Difficulty(val expectedClues: Int) {
            EASY(36),
            MEDIUM(30),
            HARD(22)
        }
    }
}