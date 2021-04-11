package krislet;

import jason.asSyntax.Literal;

public class Actions
{
    public static class FindAction implements Krislet.Action
    {
        private String target;

        public FindAction(String target) {
            this.target = target;
        }

        public void execute(Krislet krislet) {
        	// Check if target is in view
        	ObjectInfo object;
        	object = krislet.getMemory().getObject(this.target);
        	// If target is not in view, turn
        	if (object == null)
        	{
        		krislet.turn(40);
        	}
        }
    }

    public static class KickAction implements Krislet.Action
    {
        private String target;

        public KickAction(String target) {
            this.target = target;
        }

        public void execute(Krislet krislet) {
        	ObjectInfo object;
        	object = krislet.getMemory().getObject(target);
            krislet.kick(100, object.m_direction);
        }
    }

    public static class MoveToAction implements Krislet.Action
    {
        private String target;
        private static final float TARGET_DISTANCE = 0.9f;

        public MoveToAction(String target) {
            this.target = target;
        }

        public void execute(Krislet krislet) {
            ObjectInfo object;
            object = krislet.getMemory().getObject(target);
            if (object.m_distance > TARGET_DISTANCE)
            {
            	if (object.m_direction != 0)
            	{
            		krislet.turn(object.m_direction);
            	}
            	else
            	{
            		final int MAX_RUN_DISTANCE = 100;
            		krislet.dash(object.m_distance < MAX_RUN_DISTANCE ? MAX_RUN_DISTANCE : object.m_distance);
            	}
            }            
        }
    }

    // TODO: Can probably delete this
    public static class WaitRandomAction implements Krislet.Action
    {
        public void execute(Krislet krislet) {
        	long stepsToWaitFor = (long) (Math.random() * 4);
        	try {
        		Thread.sleep(2*SoccerParams.simulator_step*stepsToWaitFor);
        	} catch(Exception e){}
        	krislet.getPercepts().add(Literal.parseLiteral("goForBall"));
        }
    }

    public static class CheckIfSelfAction implements Krislet.Action
    {
    	private String sender;
        public CheckIfSelfAction(String sender) {
            this.sender = sender;
        }

        public void execute(Krislet krislet) {
        	if (sender == krislet.getName()) {
        		krislet.getPercepts().add(Literal.parseLiteral("itIsSelf"));
        	} else {
        		krislet.getPercepts().add(Literal.parseLiteral("itIsNotSelf"));
        	}
        }
    }
}
