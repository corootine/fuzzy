package com.corootine.fuzzy.domain.puzzle

import kotlin.random.Random

const val DEFAULT_RANK = 3

class PuzzleGenerator(private val boardGenerator: BoardGenerator,
                      private val puzzleSolver: PuzzleSolver) {

    fun generate(input: Input) {
        val random = Random(input.seed)

    }

    fun generatePuzzle() {

    }



    data class Input(val rank: Int = DEFAULT_RANK, val seed: Long) {

        val size = rank * rank
        val maxValue = rank * rank
    }
}