package com.corootine.fuzzy.domain.sudokuV1.old

import com.corootine.fuzzy.domain.sudokuV1.PuzzleBuilder
import com.corootine.fuzzy.domain.sudokuV1.PuzzleGenerator

// TODO: 1/13/21 introduce puzzleBuilder, Puzzle should be immutable
class Puzzle2 private constructor(
    private val input: PuzzleGenerator.Input,
    private val solvedBoard: Board2,
    var puzzleBoard: Board2
) {

    companion object {

        fun create(input: PuzzleGenerator.Input, solvedBoard: Board2): PuzzleBuilder {
            return PuzzleBuilder(
                input,
                solvedBoard,
                solvedBoard.clone()
            )
        }
    }




    override fun toString(): String {
        return puzzleBoard.toString()
    }
}