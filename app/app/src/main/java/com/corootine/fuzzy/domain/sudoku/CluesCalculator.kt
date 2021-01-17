package com.corootine.fuzzy.domain.sudoku

class CluesCalculator {

    fun numberOfClues(params: GenerationParams): Int {
        return when(params.difficulty) {
            Difficulty.EASY -> 33
            Difficulty.MEDIUM -> 24
            Difficulty.HARD -> 20
        }
    }
}