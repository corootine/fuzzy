package com.corootine.fuzzy.domain.puzzle

import kotlin.random.Random

class BoardGenerator(private var input: PuzzleGenerator.Input) {

    private var board = Board(input)
    private var random = Random(input.seed)

    companion object {

        const val NO_ROW = -1
        const val NO_COLUMN = -1
    }

    fun generate(): Board {
        generateRecursively()
        return board
    }

    private fun generateRecursively(): Boolean {
        val (row, column) = findNextRowAndColumn()

        return if (row == NO_ROW && column == NO_COLUMN) {
            true
        } else {
            val excludes = arrayListOf<Int>()

            for (attempt in 1..input.maxValue) {
                val value = getNextRandom(excludes)

                if (board.trySet(row, column, value)) {
                    if (generateRecursively()) {
                        return true
                    } else {
                        board.reset(row, column)
                        excludes.add(value)
                    }
                }
            }

            false
        }
    }

    private fun findNextRowAndColumn(): Pair<Int, Int> {
        for (i in 0 until input.size) {
            for (j in 0 until input.size) {
                if (board[i, j] == 0) {
                    return Pair(i, j)
                }
            }
        }

        return Pair(NO_ROW, NO_COLUMN)
    }

    private fun getNextRandom(excludes: List<Int>): Int {
        var random = 1 + random.nextInt(input.maxValue - 1 + 1 - excludes.size)
        for (number in excludes) {
            if (random < number) {
                break
            }

            random++
        }

        return random
    }
}