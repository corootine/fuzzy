package com.corootine.fuzzy.domain.sudoku

import kotlin.random.Random

class PuzzleGenerator(
    private val solvedPuzzleGenerator: SolvedPuzzleGenerator,
    private val wellFormityChecker: WellFormityChecker,
    private val cluesCalculator: CluesCalculator
) {

    fun generate(params: GenerationParams): PuzzleBuilder {
        val random = Random(params.seed)
        val puzzle = solvedPuzzleGenerator.generate(params)

        var clues = params.fieldsInPuzzle
        val expectedNumberOfClues = cluesCalculator.numberOfClues(params)

        while (clues >= expectedNumberOfClues) {
            val row = random.nextInt(params.rowsInPuzzle)
            val column = random.nextInt(params.columnsInPuzzle)

            if (tryRemoveClue(params, puzzle, row, column)) {
                clues--
            }
        }

        return puzzle
    }

    private fun tryRemoveClue(
        params: GenerationParams,
        puzzleBuilder: PuzzleBuilder,
        row: Int,
        column: Int
    ): Boolean {
        if (puzzleBuilder[row, column] == 0) {
            return false
        }

        val previousValue = puzzleBuilder[row, column]
        puzzleBuilder.reset(row, column)

        return if (wellFormityChecker.isWellFormed(params, puzzleBuilder)) {
            true
        } else {
            puzzleBuilder.trySet(row, column, previousValue)
            false
        }
    }
}