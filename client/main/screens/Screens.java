package screens;

public enum Screens {
    LOADING(new LoadingScreen()),
    LOGIN(new LoginScreen()),
    ACCOUNT(new AccountScreen()),
    SIGNUP(new SignupScreen()),
    GAME(new GameScreen());

    private Screen screen;

    Screens(Screen screen) {
        this.screen = screen;
    }

    public Screen get() {
        return screen;
    }
}
