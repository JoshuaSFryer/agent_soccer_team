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
            krislet.turn(40);
        }
    }

    public static class KickAction implements Krislet.Action
    {
        private String target;

        public KickAction(String target) {
            this.target = target;
        }

        public void execute(Krislet krislet) {
            krislet.kick(100, -1); //object.m_direction); // TODO: replace -1 with object.m_direction, probs should be an argument to this action
        }
    }

    public static class MoveToAction implements Krislet.Action
    {
        private String target;

        public MoveToAction(String target) {
            this.target = target;
        }

        public void execute(Krislet krislet) {
            krislet.wait(1);
        }
    }
}
