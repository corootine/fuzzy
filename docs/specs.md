# Requirements
* We're building a sudoku game where two users can work together on solving a puzzle in real-time.

- The user can connect to another user in order to play the game together.
- The user can also receive a connection request from another user.
- Both users must work with the same puzzle once connection has been established.
- The user must be able to enter a value between 1 and 9 into any free field.
- Both users must be able to work on the board simultaneously. 
  They should not be able to interfere with each other e.g. both entering a value into the same field.
- Users must be able to see all changes from each other in real time.
- Users must get notified when the game ends, so they can exit or play again.
- Users must be able to exit the game at any time.

# Use Cases
## Device Binding
- Device binding is initiated every time user wants to start a new game.
- App sends unique app info to the backend.
- Backend generates a temporary unique 6 digit app id used during the gaming session.

- What if the app crashes during the game? - User has no way to continue the previous game. The other user will be notified and the game will end for both.
- What if connection breaks during the game?
  1. Broken by the user - Game ends for both users.
  2. Broken due to technical reasons e.g bad internet connection - Attempt to restore the connection. If not possible, game ends for both users.