package com.corootine.fuzzy.domain.sudokuV1

class CluesCalculator {

    fun numberOfClues(params: PuzzleParams): Int {
        // TODO: Use rows/columns to determine the number of clues
        return when(params.difficulty) {
            Difficulty.EASY -> 33
            Difficulty.MEDIUM -> 24
            Difficulty.HARD -> 20
        }
    }
}