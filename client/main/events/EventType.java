package events;

import screens.Screens;

public class EventType {

    public enum Client {
        CREATE,
        INIT,
        UPDATE
    }

    public enum Game {
        LOG_IN,
        LOG_OUT
    }

    public enum Loading {
        STARTED,
        FINISHED,
    }

    public static class ChangeScreen {
        private final Screens screen;

        public ChangeScreen(Screens screen) {
            this.screen = screen;
        }

        public Screens get() {
            return screen;
        }
    }

    enum Connection {
        CONNECTING,
        CONNECTED,
        DISCONNECTED
    }
}
