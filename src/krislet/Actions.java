package krislet;

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
        private static final int TARGET_DISTANCE = 10;

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
            		krislet.dash(10*object.m_distance);
            	}
            }            
        }
    }
}
