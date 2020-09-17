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

## Connect use case
- The user can connect to another user in order to play the game together.
- The user can also receive a connection request from another user.
- Both users must work with the same puzzle once connection has been established.





# Specs
- When the app starts, it will first establish a secure connection with the server and exchange required info with the application.
- The user will be shown a simple screen where they will enter the app id of another user in order to play.
  The app id is present on the login screen.
