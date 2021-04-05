// Agent sample_agent in project agent_soccer_team

/* Initial beliefs and rules */

/* Initial goals */

!score.

/* Plans */
+!score : beside(ball) & found(targetnet) <- kick(targetnet); !score.
+!score : beside(ball) & not found(targetnet) <- find(targetnet); !score.
+!score : found(ball) & not beside(ball) <- moveto(ball); !score.
+!score : true <- find(ball); !score.
