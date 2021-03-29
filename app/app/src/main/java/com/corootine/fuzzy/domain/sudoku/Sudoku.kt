package com.corootine.fuzzy.domain.sudoku

/**
 * Represents a sudoku puzzle. It provides a set of methods to read the puzzle, set and reset all
 * non-clue cells, as well as checking if the puzzle is completed. All changes to the sudoku must
 * conform to the sudoku rules. Implementations of this interface maintain following invariants:
 * </br>
 * * No repetitions of values in a single row.
 * * No repetitions of values in a single cell.
 * * No repetitions within a single box.
 * * No changes are allowed to fields which are already given (clues). See [Cell.isClue].
 *
 * @see <a href="https://en.wikipedia.org/wiki/Glossary_of_Sudoku">Sudoku Glossary</a>
 */
interface Sudoku : Iterable<Sudoku.Cell> {

    /**
     * Metadata for this puzzle. It contains info like max rows, columns etc.
     *
     * @return [SudokuGenerator.Metadata]
     */
    val metadata: SudokuGenerator.Metadata

    /**
     * Gets the [Cell] at [row] and [column].
     *
     * @param row row inside the puzzle
     * @param column column inside the puzzle
     * @throws IllegalArgumentException if row or column are out of range.
     * ```
     * row >= 0 && row < PuzzleGenerator.Metadata.rowsInGrid
     * column >= 0 && column < PuzzleGenerator.Metadata.columnsInGrid
     * ```
     */
    fun get(row: Int, column: Int): Cell

    /**
     * Attempts to set [value] in the puzzle at [row] and [column].
     * In order for the value to be set successfully, it must comply with following requirements:
     * * Value must be unique in the row
     * * Value must be unique in the column
     * * Value must be unique in the cell
     * * Cell must not be a clue
     *
     * If all of these conditions are met, the value will be set and this method will return true.
     * Otherwise it will not be set and the method will return false.
     *
     * @param row row inside the puzzle
     * @param column column inside the puzzle
     * @param value value to set to the puzzle
     * @throws IllegalArgumentException if value, row or column are out of range.
     * ```
     * row >= 0 && row < PuzzleGenerator.Metadata.rowsInGrid
     * column >= 0 && column < PuzzleGenerator.Metadata.columnsInGrid
     * value >= 1 && value <= PuzzleGenerator.Metadata.highestNumber
     * ```
     * @throws IllegalArgumentException if row and column point to a clue [Cell].
     * */
    fun trySet(row: Int, column: Int, value: Int): Boolean

    /**
     * Resets the cell at [row] and [column].
     *
     * @param row row inside the puzzle
     * @param column column inside the puzzle
     * @throws IllegalArgumentException if row or column are out of range.
     * ```
     * row >= 0 && row < PuzzleGenerator.Metadata.rowsInGrid
     * column >= 0 && column < PuzzleGenerator.Metadata.columnsInGrid
     * ```
     * @throws IllegalArgumentException if row and column point to a clue [Cell].
     */
    fun reset(row: Int, column: Int)

    /**
     * Returns true if the puzzle is complete. A puzzle is considered complete if it does not
     * contain any empty cells.
     *
     * @return true if the puzzle is complete, otherwise false
     */
    fun isComplete(): Boolean

    /**
     * Describes a single sudoku cell, which is represented by a number and a flag that indicates
     * whether the cell is a clue and cannot be edited.
     */
    data class Cell(val row: Int, val column: Int, val value: Int, var isClue: Boolean) {

        companion object {

            /**
             * Creates an empty cell for the given row and column.
             */
            fun empty(row: Int, column: Int) = Cell(row, column, 0, false)
        }

        /**
         * Returns the current state of the cell. It's considered empty if the value is 0.
         */
        val isEmpty: Boolean get() = value == 0
    }
}