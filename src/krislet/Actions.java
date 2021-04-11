package krislet;

public class Actions
{
    /**
     * An action that makes the Krislet agent look for a target object
     * @param target The String name of the object to look for
     */
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

    /**
     * An action that makes the Krislet agent kick the target object
     * @param target The String name of the object to kick
     */
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


    /**
     * An action that makes the Krislet agent move towards a target object
     * @param target The String name of the object to move towards
     */
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
}
