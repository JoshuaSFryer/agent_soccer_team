// Agent soccer_agent_striker in project agent_soccer_team

/* Initial beliefs and rules */

/* Initial goals */

// Start with the seeking goal where our objective is to find the ball OR hear from another player
// that they are going for the ball
!seeking.

/* Plans */
-itIsSelf <- -playerGoingForBall; -done.
+playerGoingForBall[source(A)] : not itIsSelf <- .print("Another player is getting the ball, I will assist them").
+done[source(A)] <- .print("Other agent has kicked the ball. I will go back to seeking state.").

+!seeking : playerGoingForBall & itIsSelf <- !score.
+!seeking : playerGoingForBall & not itIsSelf <- !assist.
+!seeking : not playerGoingForBall & found(ball) <- +itIsSelf; .send([soccer_agent_striker1,soccer_agent_striker2],tell,playerGoingForBall); !seeking.
+!seeking : not playerGoingForBall & not found(ball) <- find(ball); !seeking.
+!seeking : not playerGoingForBall <- !seeking.

+!score : beside(ball) & found(goal_r) <- kick(goal_r) .send([sample_agent1,sample_agent2],tell,done); -itIsSelf; !seeking.
+!score : beside(ball) & not found(goal_r) <- find(goal_r); !score.
+!score : found(ball) & not beside(ball) <- moveto(ball); !score.
+!score : true <- find(ball); !score.

+!assist : not done & beside(goal_r) <- find(ball); !assist.
+!assist : not done & found(goal_r) & not beside(goal_r) <- moveto(goal_r); !assist.
+!assist : not done <- find(goal_r); !assist.
+!assist : done <- -done; -playerGoingForBall; !seeking.
