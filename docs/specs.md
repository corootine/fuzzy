    _0 1 2_____3 4 5____6 7 8__
 0 |        |    4   |        |
 1 |        |        |        |
 2 |        |        |        |
   |--------|--------|--------|
 3 |        |    4   |        |
 4 |        |        |        |
 5 |        |        |        |
   |--------|--------|--------|
 6 |        |        |        |
 7 |        |        |        |
 8 |        |        |        |
    --------------------------

 - setting a duplicate value should fail -> fail early
 - conflict resolution:
   1. try a different number
   2. if not possible, backtrack until we find a compatible number

 - get board for visualization
 - update board in attempts to solve it
 - notify when an attempt is right or wrong
 - notify when a sudoku puzzle is complete

 - input -> compute -> output
 * seed -> generate the puzzle -> solved puzzle -> puzzle to solve

# Requirements
* Sudoku game where two users can work together on solving a puzzle in real-time.

- Users must be able to connect to each other in order to play the game together
- Users must have the same puzzle
- User must select a field in order to enter a value
- Users must be able to see each other's field selection
- Users must be able to work on the board at the same time. They must not be able to interfere
with each other i.e. select a field that the other play has already selected
- Users must be able to see all changes from each other
- Users must get notified when the game ends, so they can exit or play again
- Users must be able to exit the game at any time
- Users should be able to see the current status of the puzzle (correct, incorrect)
