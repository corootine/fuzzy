package com.corootine.fuzzy.domain.puzzle

import kotlin.random.Random

/**
 * Generates a valid fully solved sudoku puzzle.
 * This puzzle is used to derive a puzzle with clues, that needs to be solved.
 */
class SolvedPuzzleGenerator {

    companion object {

        const val NO_ROW = -1
        const val NO_COLUMN = -1
    }

    fun generate(params: PuzzleParams): PuzzleBuilder {
        val random = Random(params.seed)
        val puzzleBuilder = PuzzleBuilder.Builder(params)
        generateRecursively(params, puzzleBuilder, random)
        return puzzleBuilder.build()
    }

    private fun generateRecursively(
        params: PuzzleParams,
        builder: PuzzleBuilder.Builder,
        random: Random
    ): Boolean {

        val (row, column) = findNextRowAndColumn(params, builder)

        return if (row == NO_ROW && column == NO_COLUMN) {
            true
        } else {
            val excludes = arrayListOf<Int>()

            (1..params.highestNumber).forEach { _ ->
                val number = getNextRandomNumber(params, random, excludes)

                if (builder.trySet(row, column, number)) {
                    if (generateRecursively(params, builder, random)) {
                        return true
                    } else {
                        builder.reset(row, column)
                        excludes.add(number)
                    }
                }
            }

            false
        }
    }

    private fun findNextRowAndColumn(
        params: PuzzleParams,
        puzzleBuilderBuilder: PuzzleBuilder.Builder
    ): Pair<Int, Int> {
        for (row in 0 until params.rowsInGrid) {
            for (column in 0 until params.columnsInGrid) {
                if (puzzleBuilderBuilder.get(row, column) == 0) {
                    return Pair(row, column)
                }
            }
        }

        return Pair(NO_ROW, NO_COLUMN)
    }

    private fun getNextRandomNumber(
        params: PuzzleParams,
        random: Random,
        excludes: List<Int>
    ): Int {
        var number = 1 + random.nextInt(params.highestNumber - 1 + 1 - excludes.size)
        for (exclude in excludes) {
            if (number < exclude) {
                break
            }

            number++
        }

        return number
    }
}