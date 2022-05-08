package ru.aniby.felmon.discord;

public class RichPresenceConfig {
    public static final String CLIENT_ID = "795331486332616754";
    public static final String STATE_IDLE = "Idle";
    public static final String STATE_IN_SERVER = "Playing in $address";
    public static final String DETAILS_IDLE = "In title screen";
    public static final String DETAILS_IN_SERVER = "$playerCount players online";
    public static final String DETAILS_IN_SINGLEPLAYER = "--";
    public static class LargeImage {
        public static final String MAIN = "1";
        public static final String MC = "mclogo";
        public static final String MAIN_TEXT = "Разрабатывает Felmon RPG";
    }
    public static class SmallImage {
        public static final String ACTIVE = "alex_shield";
        public static final String ACTIVE_TEXT = "Бегает по миру";
        public static final String PASSIVE = "iron_golem";
        public static final String PASSIVE_TEXT = "Афкшит в меню";
    }
}
