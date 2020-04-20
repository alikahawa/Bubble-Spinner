# Sprint retrospective, Iteration #3

| **Requirement/user story** | **Tasks** | **Assigned to** | **Expected duration (hours)** | **Actual Duration (hours)** | **Done?**** yes/no **|** Notes** |
| --- | --- | --- | --- | --- | --- | --- |
| The game shall have a bubble at the center that can not be removed by playing, it will have a special color (i.e. gray). | Create a non removable bubble in the center of the grid | ~~Rosalie~~ Marije | .5 | 1 | yes |   |
| The player shall win the round, when only the center bubble is left. | Implement a check for bubbles remaining and reset the level. | ~~Rosalie~~, Marije | 1 | 3.5 | no | There is a problem with not showing the end screen, that we do not know how to fix yet. |
| The game shall be over when a bubble on the grid touches a wall.     | Implement detection for when a bubble on the grid touches a wall, and then end the game. | ~~Rosalie~~, Marije | 1 | 3 | no | There is a problem with not showing the end screen, that we do not know how to fix yet. |
| Generate a random selection of bubbles at the start of the game around the center bubble. | Implement an algorithm that puts bubbles at the start of the game. | ~~Rosalie, Ali~~ | 3 | 1 | no | There was no time left.   |
| There shall be a scoring system. | Calculate player score during game.Send player score to the database at end of match.Show scoreboard at end of match. | Marije, Jordy, Ali | 1 | 3,5 | yes | Underestimate linking of several parts. |
| The game shall have a sound when a bubble is shot | Choose sound for when the bubbles are shot and implement it in the GUI. | Toma, Jordy | 2 | 2 | yes |   |
| The game shall apply physics when collision happens, i.e. grid will rotate. | Implement the physics of the grid to let the grid rotate when a collision happen. | Jordy, Toma, Marije | 6 | 6 | yes |   |
| The player shall have a profile page, where more information could be found. | Create a profile page.Retrieve data from the database and show the data on the profile page. | Ali | 4 | 2 | yes |   |
| The game shall have a scoreboard where the score of each play will be saved next to his/her nickname. | Create a scoreboard.Retrieve the scores from the database.Display the scores. | Ali, Jordy | 2 | 2 | yes |   |
| The start-up screen shall have at least three options. | The start-up screen will offer the player to log-in, start a game, see the scoreboard and ... | Ali | 2.5 | 1 | yes |   |
| The game shall show the colors of the next two bubbles. | Randomize bubble color before shooting.Update the gui to have the next two bubbles showed next to the bubbles on the canon. | Marije, Toma | 3 | 3 | yes |   |
| The game shall have multiple difficulty levels | Implement level and depending on it change the initialized number of bubbles ~~and increase the number of colors.~~ | Marije, ~~Rosalie?~~ | 4 | 4 | yes |   |
| The line which shows the direction of the ball should be shorter. | Using coordinates, make the line which points to where the ball is shot of fixed length. | Toma | 2 | 1 | yes |   |
| The game shall have visual effects when a collision happens. | Bubble that disappear will have a &quot;pop&quot; animation. | Jordy, Toma | 2 | 2 | yes |   |



##Main Problems Encountered:

###Problem 1: One of our groupmates left.

Reaction:
We think we handled this OK by redistributing the tasks and scrapping/moving some other tasks, such as the random bubble generation.

###Problem 2:  A lot of other tasks for the course.

During the last sprint we had bigger tasks than previous sprints next to the sprint, such as preparing the final presentation, and spending more time on using design patterns.

Reaction: These things happen towards the end of the period, although we had a higher workload we are still content with the level of completion of the tasks this sprint.