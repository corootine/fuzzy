package com.corootine.fuzzy.domain.sudoku.implementation

fun SudokuBuilder.applyGrid(grid: Array<Array<Int>>) {
    for (row in grid.indices) {
        for (column in grid[row].indices) {
            if (grid[row][column] != 0) {
                trySet(row, column, grid[row][column])
            }
        }
    }
}
