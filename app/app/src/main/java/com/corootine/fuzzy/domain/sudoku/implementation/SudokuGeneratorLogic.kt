package com.corootine.fuzzy.domain.sudoku.implementation

import com.corootine.fuzzy.domain.sudoku.api.Sudoku
import com.corootine.fuzzy.domain.sudoku.api.SudokuGenerator

class SudokuGeneratorLogic(
    private val gridFiller: GridFiller,
    private val clueRemoval: ClueRemoval
) : SudokuGenerator {

    override fun generate(metadata: SudokuGenerator.Metadata): Sudoku {
        val sudokuBuilder = SudokuBuilder(metadata)

        gridFiller.fill(sudokuBuilder)
        clueRemoval.remove(sudokuBuilder)

        return SudokuToSolve(sudokuBuilder)
    }
}