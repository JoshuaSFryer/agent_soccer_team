// Agent soccer_agent_defender in project agent_soccer_team

/* Initial beliefs and rules */

/* Initial goals */

!defend.

/* Plans */

// How to defend
// Go to friendly net
// Look for ball
// Ball is closer than a threshold, go to ball and kick away

+!defend : beside(ball) & found(targetnet) <- kick(targetnet); !stayback.
+!defend : beside(ball) & not found(targetnet) <- find(targetnet); !defend.
+!defend : found(ball) & not beside(ball) <- moveto(ball); !defend.
+!defend : true <- find(ball); !defend.

+!stayback : beside(friendlynet) & found(ball) & close(ball) <- find(ball); !defend.
+!stayback : beside(friendlynet) <- find(ball); !stayback.
+!stayback : found(friendlynet) & not beside(friendlynet) <- moveto(friendlynet); !stayback.
+!stayback : true <- find(friendlynet); !stayback.
