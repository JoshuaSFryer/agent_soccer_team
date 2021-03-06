// Agent soccer_agent_striker in project agent_soccer_team

/* Initial beliefs and rules */

/* Initial goals */

// Start with the seeking goal where our objective is to find the ball OR hear from another player
// that they are going for the ball
!seeking.

/* Plans */
-itIsSelf <- -playerGoingForBall; -done.
+playerGoingForBall : not itIsSelf <- .print("Another player is getting the ball, I will assist them").
+done[source(A)] <- .abolish(B) !seeking.

+!seeking : playerGoingForBall & itIsSelf <- !score.
+!seeking : playerGoingForBall & not itIsSelf <- !assist.
+!seeking : not playerGoingForBall & found(ball) <- +itIsSelf; .send([soccer_agent_striker1,soccer_agent_striker2,soccer_agent_striker3],tell,playerGoingForBall); !seeking.
+!seeking : not playerGoingForBall & not found(ball) <- find(ball); !seeking.

+!score : beside(ball) & found(goal_r) <- kick(goal_r) .send([soccer_agent_striker1,soccer_agent_striker2,soccer_agent_striker3],tell,done).
+!score : beside(ball) & not found(goal_r) <- find(goal_r); !score.
+!score : found(ball) & not beside(ball) <- moveto(ball); !score.
+!score : true <- find(ball); !score.

+!assist : not done & close(goal_r) <- find(ball); !assist.
+!assist : not done & found(goal_r) & not close(goal_r) <- moveto(goal_r); !assist.
+!assist : not done <- find(goal_r); !assist.
