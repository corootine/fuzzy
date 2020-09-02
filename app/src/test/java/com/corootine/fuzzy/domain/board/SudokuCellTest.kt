package com.corootine.fuzzy.domain.board

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized.Parameters

@RunWith(Enclosed::class)
class SudokuCellTest {

    class NonParameterized {

        @Test(expected = IllegalArgumentException::class)
        fun `should not allow negative row size`() {
            // given
            val builder = SudokuCell.Builder()

            // when
            builder.set(-1, 2, 5)
        }

        @Test(expected = IllegalArgumentException::class)
        fun `should not allow negative column size`() {
            // given
            val builder = SudokuCell.Builder()

            // when
            builder.set(2, -1, 5)
        }

        @Test(expected = IllegalArgumentException::class)
        fun `should not allow row size greater than 2`() {
            // given
            val builder = SudokuCell.Builder()

            // when
            builder.set(3, 2, 5)
        }

        @Test(expected = IllegalArgumentException::class)
        fun `should not allow column size greater than 2`() {
            // given
            val builder = SudokuCell.Builder()

            // when
            builder.set(2, 3, 5)
        }
    }

    @RunWith(org.junit.runners.Parameterized::class)
    class Parameterized(private val input: Array<Array<Int>>, private val expected: Boolean) {

        companion object {

            @JvmStatic
            @Parameters
            fun data() = arrayOf(
                arrayOf(
                    arrayOf(
                        arrayOf(1, 2, 4),
                        arrayOf(5, 8, 9),
                        arrayOf(6, 7, 3)
                    ),
                    true
                ),
                arrayOf(
                    arrayOf(
                        arrayOf(1, 2, 4),
                        arrayOf(5, 4, 9),
                        arrayOf(6, 7, 3)
                    ),
                    false
                ),
                arrayOf(
                    arrayOf(
                        arrayOf(9, 2, 4),
                        arrayOf(5, 8, 1),
                        arrayOf(6, 7, 9)
                    ),
                    false
                ),
                arrayOf(
                    arrayOf(
                        arrayOf(1, 1, 4),
                        arrayOf(5, 8, 9),
                        arrayOf(6, 7, 3)
                    ),
                    false
                ),
                arrayOf(
                    arrayOf(
                        arrayOf(1, 2, 9),
                        arrayOf(8, 5, 4),
                        arrayOf(7, 6, 3)
                    ),
                    true
                ),
            )
        }

        @Test
        fun `should not allow duplicate numbers`() {
            // given
            val builder = SudokuCell.Builder()

            // when
            for (row in input.indices) {
                for (column in input[row].indices) {
                    println("Setting value ${input[row][column]} to [$row][$column]")
                    val result = builder.set(row, column, input[row][column])
                    if (!result) {
                        assertEquals(expected, false)
                        return
                    }
                }
            }

            assertEquals(expected, true)
        }
    }
}