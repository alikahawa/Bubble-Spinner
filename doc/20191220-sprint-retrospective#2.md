

# Sprint retrospective, Iteration #2

|                                                              |                                                              |                  |                           |                   |               |                                                              |      |      |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ---------------- | ------------------------- | ----------------- | ------------- | ------------------------------------------------------------ | ---- | ---- |
| Requirement/user story                                       | Tasks                                                        | Assigned to      | Expected duration (hours) | Actual Time spent | Done (yes/no) | notes                                                        |      |      |
| The player shall be able to login by entering his/her credentials on a log-in screen. | Create a page where user can enter credentials.Check information with database | Ali, Jordy       | 3                         | 3                 | yes           | -                                                            |      |      |
| The database shall be linked to the authentication system.   | Set up a database, and schema (includes nickname)            | Ali, Jordy       | 2                         | 1.5               | yes           | -                                                            |      |      |
| The game shall have a hexagonal grid on which the bubbles are located. | display bubbles on screen                                    | Marije, Rosalie  | 4                         | 4                 | yes           |                                                              |      |      |
| The game shall have a bubble at the center that can not be removed by playing, it will have a special color(i.e. gray). | The game shall have a bubble at the center that can not be removed by playing, it will have a special color(i.e. gray). | Rosalie, Marije  | .5                        | -                 | no            | not enough time left.                                        |      |      |
| Allow the user to shoot multiple bubbles                     | Separate the JavaFX code from the logic and allow the user to shoot multiple bubbles | Toma             | 4                         | 4                 | yes           | -                                                            |      |      |
| The player shall win the round, when only the center bubble is left. | Implement a check for bubbles remaining and reset the level. | Marije, Rosalie  | 1                         | -                 | no            | not enough time left.                                        |      |      |
| The game shall randomly generate the color of a bubble to be shot. | Implement a bubble randomizer for the cannon                 | Toma (Rosalie)   | 1                         | .5                | yes           | maybe more complex later.                                    |      |      |
| The game shall be over when a bubble on the grid touches a wall. | Implement a detection for when a bubble on the grid touches a wall, and then end the game. | Rosalie          | 3                         | -                 | no            | discussed below                                              |      |      |
| The game shall have a random selectionof bubbles at the middle of the grid around the center bubble when it starts. | Implement an algorithm that puts bubbles at the start of the game. | Rosalie          | 3                         | -                 | no            | discussed below                                              |      |      |
| There shall be a scoring system                              | calculate player score during game.<br /><br />Send player score to database at end of match.<br />Show scoreboard at end of match | Marije Jordy Ali | 3                         | 3                 | no            | because we have no end of match, and interfaces needed to be linked with observer pattern. |      |      |
| There is a connection between the parts, working together.   | Create a game class to control the state of the game, help update the UI and keep track of the player input. | Jordy            | 3                         | 6                 | yes           | all the parts were there but linking them (and dealing with different threads) was a bit more work than expected. |      |      |
| The game shall have a pointer in the direction of the canon. | Display a line from the cannon pointing to where the ball is shot, depending on the mouse coordinates. | Toma             | 2                         | 2                 | yes           | -                                                            |      |      |
| The game shall have a sound when a bubble is shot.           | Choose sound for when the bubbles are shot and implement it in the GUI. | Toma             | 1                         | -                 | no            | personal reasons..                                           |      |      |

## Improvements from last sprint:

- The time estimate of most tasks was better, only the estimate for the game class was off.
- The prioritization did go better this sprint as well, we finished the database first, together with the firing of the ball,which were important parts for the (original) deadline and the linked the elements together.
- We improved our communication during the projects, and assigned relevant tasks better among people at the start of the sprint. We had less issues with this.
- We still have some vagueness left in the tasks, and the definition of the tasks on gitlab is not yet consistent.

## Problems this sprint:

Problem 1:

As mentioned before we still have some vagueness and inconsistencies left in the issues.

Improvement:
We have added issue templates to the gitlab repository and will use standardised templates in the next sprint.

Problem 2:
Difference in contribution among group members.

Improvement:
We have mentioned our problems and are looking to communicate better to prevent delays and big differences in contributions from now on.

Problem 3:

Feedback/constructive criticism in the groupchat is sometimes slow or hard to get.

Improvement:
The issue has been brought up with the group, who have indicated that more effort will be put into communication in the group chat.