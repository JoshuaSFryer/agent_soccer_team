// Agent soccer_agent_striker in project agent_soccer_team

/* Initial beliefs and rules */

/* Initial goals */

!score.

/* Plans */

// 3 goals
// 1. Determine if you are closest striker --> add belief ("attacker" or "assist")
// 2. Score (if closest)
// 3. Run to opposing net (if not closest)

// How to score
+!score : beside(ball) & found(targetnet) <- kick(targetnet); !score.
+!score : beside(ball) & not found(targetnet) <- find(targetnet); !score.
+!score : found(ball) & not beside(ball) <- moveto(ball); !score.
+!score : true <- find(ball); !score.

// Determining closest agent
// 1. All strikers need to see ball
// 2. All strikers determine their distances to ball & communicate
// 3. Closest striker to ball is assigned belief "attacker"; all agents delete goal !closest.

+!closest : 
+!closest : true <- find(ball); !closest.
