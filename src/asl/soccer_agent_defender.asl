// Agent soccer_agent_defender in project agent_soccer_team

/* Initial beliefs and rules */

/* Initial goals */

!stayback.

/* Plans */

// How to defend
// Go to friendly net
// Look for ball
// Ball is closer than a threshold, go to ball and kick away

+!defend : beside(ball) & found(goal_r) <- kick(goal_r); -amBesideGoal; !stayback.
+!defend : found(ball) & beside(ball) & not found(goal_r) <- find(goal_r); -amBesideGoal; !defend.
+!defend : found(ball) & not beside(ball) <- moveto(ball); -amBesideGoal; !defend.
+!defend : true <- find(ball); !defend.

+!stayback : found(ball) & close(ball) & amBesideGoal <- find(ball); !defend.
+!stayback : found(goal_l) & close(goal_l) <- find(ball); +amBesideGoal; !stayback.
+!stayback : found(goal_l) & not close(goal_l) <- moveto(goal_l); !stayback.
+!stayback : true <- find(goal_l); !stayback.
