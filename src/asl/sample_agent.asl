// Agent sample_agent in project agent_soccer_team

/* Initial beliefs and rules */

/* Initial goals */

// Start with the seeking goal where our objective is to find the ball OR hear from another player
// that they are going for the ball
!seeking.

/* Plans */
+!seeking : beside(ball) & found(goal_r) <- kick(goal_r); !assist.
+!seeking : beside(ball) & not found(goal_r) <- find(goal_r); !score.
// We found the ball, so tell all players that we want to go for it
+!seeking : found(ball) & not beside(ball) <- .send([sample_agent1,sample_agent2],tell,playerGoingForBall); !score.
// Check if the message came from ourself or another player
+playerGoingForBall[source(A)] <- checkifself(A).
// It came from another player, so focus on the assist goal
+itIsNotSelf <- .print("Another player is getting the ball, I will assist them"); !assist.
// It came from ourself, so focus on the score goal
+itIsSelf <- true; !score.
// While seeking, our default action is to find the ball
+!seeking : true <- find(ball); !score.

+!score : beside(ball) & found(goal_r) <- kick(goal_r); !assist.
+!score : beside(ball) & not found(goal_r) <- find(goal_r); !score.
+!score : found(ball) & not beside(ball) <- moveto(ball); !score.

// TODO: clean up these dead comments lmao
//+!score : dist(ball,X) <- waitrandom(); !score.
//+!score : dist(ball,X) <- .send([sample_agent1,sample_agent2],tell,test(X)).
+!score : true <- find(ball); !score.
//+kick[source(A)] <- compareDistance(A).

//+dist(X) : dist(ball,X).
//+test(X)[source(A)] <- .print(X); !assist.
//+goForBall <- .send([sample_agent1,sample_agent2],tell,playerGoingForBall); !score.
//+playerGoingForBall <- .print("Another player is going for the ball"); !assist.

+!assist : beside(goal_l) <- find(ball); !score.
//+!assist : beside(goal_l) <- send(agentsToSendTo, "kickedball"); !score.
+!assist : found(goal_l) & not beside(goal_l) <- moveto(goal_l); !assist.
+!assist : true <- find(goal_l); !assist.