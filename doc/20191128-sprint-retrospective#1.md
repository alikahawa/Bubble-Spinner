## Retrospective Sprint #1

| Requirement                                                  | Tasks                                                        | Assigned to   | Expected duration (hours) | actual time spent | done? yes/no | notes                                                        |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------- | ------------------------- | ----------------- | ------------ | ------------------------------------------------------------ |
| The player shall be able to login by entering his/her credentials on a log-in screen. | Create a page where user can enter credentials, and send to database | Ali, Jordy    | 3                         | 1                 | no           | Moved to higher priority tasks                               |
| The database shall be linked to the authentication system.   | Set up a database, and schema                                | Ali, Jordy    | 3                         | 0                 | no           | Moved to higher priority tasks                               |
| The game shall have be a GUI to display game elements.       | Make a window appear and display images in window            | Ali, Toma     | 3                         | 4                 | yes          | a lot of time spent on finding the right library             |
| The game shall have a cannon that shoots bubbles.            | Implement a cannon that can fire bubbles                     | Ali, Toma     | 2                         | 2                 | yes (dev)    | basic functionality there but can be improved                |
| The game shall have a hexagonal grid on which the bubbles are located. | Implement data structure to store bubbles and display bubbles on screen | Marije, Jordy | 4                         | 4                 | no           | Needs to be linked to UI                                     |
| The game shall have a bubble at the center that can not be removed by playing, it will have a special color(i.e. gray). | The game shall have a bubble at the center that can not be removed by playing, it will have a special color(i.e. gray). | Rosalie       | .5                        | 0                 | no           | Needed to wait on grid implementation                        |
| The player shall be able to shoot a bubble from the cannon by pressing the left mouse button or space. | Assign cannon firing to user input                           | Ali, Toma     | .5                        | .5                | yes          |                                                              |
| The player shall be able to change the direction of the cannon by moving the mouse. | Make the cannon rotate and change direction of projectile using the mouse coordinates. | Ali, Toma     | 1                         | 3                 | yes(dev)     | Problems with finding the screen coordinates. Difficult to find the direction where the ball should go. |
| The game shall randomly generate the color of a bubble to be shot. | Implement a bubble randomizer for the cannon                 | Ali, Toma     | .5                        | 0                 | no           | We planned too  much in one sprint.                          |
| The player shall win the round, when only the center bubble is left. | Implement a check for bubbles remaining and reset the level. | Jordy         | .5                        | 0                 | no           | We planned too much in one sprint.                           |
| The game shall reflect bubbles when they hit walls.          | Implement wall collision and bubble reflection               | Toma          | 1.5                       | .5                | yes          | it's just two if statements.                                 |
| The game shall make the shot bubble stick to the first other bubble it hits. | Implement bubble collision                                   | Marije        | 3                         | 7                 | yes          | Working with the coordinates of the grid was more difficult than thought. |
| The game shall place sticking bubble in the nearest available grid position from the collision point. | Determine closest available grid point, and append bubble to the grid. | Marije        | 1                         | 1                 | yes          |                                                              |
| The game shall remove bubbles from the screen when they have the same color and their connected sum is >= 3. | Check if enough colored bubbles are connected, remove bubbles from grid if so | Jordy, Toma   | 2                         | 2                 | yes          |                                                              |
| The game shall remove all bubbles that have become disconnected from the center. | Implement Algorithm to detect if bubbes are still connected to the center. | Jordy, Toma   | 2                         | 1                 | yes          |                                                              |
| The game shall be over when a bubble on the grid touches a wall. | Implement a detection for when a bubble on the grid touches a wall, and then end the game. | Rosalie       | 3                         | 1                 | no           | Researching/figuring out logic, but had to wait for previous implementation |
| The game shall have a random selection of bubbles at the middle of the grid around the center bubble when it starts. | Implement an algorithm that psuedo randomizes the bubbles at the start of the game. | Rosalie       | 3                         | 0                 | no           | had to wait for previous implementation                      |

**Things that went not great this sprint:**

**Problem 1:**

We took up more tasks  than we should have this week, we didn't have a chance to finish everything.

**improvement:**

When we discuss the  sprint plan, be more critical of the size of the task, by doing a bit of research before assigning to get a better time estimate.

**Problem 2:**

Because the tasks weren't properly ordered on priority we had trouble determining which tasks to work on first.

**Improvement:**

Order the tasks by priority, so that we can better determine what to work on first. And assign an equal amount of same priority tasks to each member.

**Problem 3:**

Interconnectivity  and dependency of tasks wasn't always clear, which causes implementations to mismatch and/or members to have to wait for each-other

**Improvement:**

This is partially to be expected at the start of the project, but during sprint planning by spending more time discussing during the sprint plan meeting, AND by coding in such a way that features can be easily connected. 

**Problem 4:**

We determined the tasks/issues too much directly on the requirements, which caused overlap, and vagueness in the tasks.

**Improvement**

Draw issues from the requirements such that there is no double work, and the tasks to be completed are more specific.

