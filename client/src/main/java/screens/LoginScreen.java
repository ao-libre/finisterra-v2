package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import events.AppEventBus;
import game.ui.WidgetFactory;
import game.utils.Resources;

public class LoginScreen extends AOScreen {

    // Game Settings
    private final Preferences preferences = Gdx.app.getPreferences("Finisterra");

    private TextField passwordField;
    private CheckBox disableMusic;
    private CheckBox disableSound;

    // UI - dependencies
    private final Texture backgroundImage = new Texture(Gdx.files.internal(Resources.GAME_IMAGES_PATH + "background.jpg"));
    private final SpriteDrawable backgroundSprite = new SpriteDrawable(new Sprite(backgroundImage));

    public LoginScreen(AppEventBus appEventBus, AssetManager assetManager) {
        this.assetManager = assetManager;
        this.appEventBus = appEventBus;
    }

    @Override
    public void generateUserInterface() {
        /* Tabla Principal */
        super.generateUserInterface();
        mainTable.setBackground(backgroundSprite);

        /* Tabla de login */
        Window loginWindow = WidgetFactory.createWindow(); //@todo window es una ventana arrastrable

        // Label + Input - Email
        Label emailLabel = WidgetFactory.createLabel("Email: ");
        // UI - Components
        TextField emailField = WidgetFactory.createTextField("");

        // Label + Input - Password
        Label passwordLabel = WidgetFactory.createLabel("Password");
        passwordField = WidgetFactory.createTextField("");
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);

        // Checkbox - Remember Me
        //@todo implementar remember me
        CheckBox rememberMe = WidgetFactory.createCheckBox("Remember me");
        if (preferences.getBoolean("rememberMe")) {
            rememberMe.setChecked(true);
            if (emailField.getText().isBlank()) {
                if (!preferences.getString("userEmail").isBlank()) {
                    emailField.setText(preferences.getString("userEmail"));
                }
            }
        }

        // Checkbox - See Password
        CheckBox seePassword = WidgetFactory.createCheckBox("See Password");
        seePassword.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                passwordField.setPasswordMode(!passwordField.isPasswordMode());
            }
        });

        // Button - Login
        TextButton loginButton = WidgetFactory.createTextButton("Login");
        // loginButton.addListener(new LoginButtonListener());

        TextButton newAccountButton = WidgetFactory.createTextButton("New account");
        newAccountButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // screenManager.to(ScreenEnum.SIGN_UP);
            }
        });

        // Add components set-up before to the Window.
        loginWindow.getColor().a = 0.8f;
        loginWindow.add(emailLabel).padRight(5);
        loginWindow.add(emailField).width(250).row();
        loginWindow.add(passwordLabel).padTop(5).padRight(5);
        loginWindow.add(passwordField).padTop(5).width(250).row();
        loginWindow.add(rememberMe).padTop(20);
        loginWindow.add(loginButton).padTop(20).row();
        loginWindow.add(seePassword).padLeft(-10).padTop(30);
        loginWindow.add(newAccountButton).padTop(30).row();

        /* FIN - Tabla de login */

        /* Tabla de servidores */
        // Table connectionTable = new Table((getSkin()));
        // serverList = WidgetFactory.createList();
        // serverList.setItems(clientConfiguration.getNetwork().getServers());
        // connectionTable.add(serverList).width(400).height(300); //@todo Nota: setear el size acá es redundante, pero si no se hace no se ve bien la lista. Ver (*) más abajo.

        /* Botones para desactivar el sonido y la musica*/

        /* Musica */
        disableMusic = WidgetFactory.createCheckBox("Desabilitar Musica");
        if (preferences.getBoolean("MusicOff")) {
            disableMusic.setChecked(true);
            // musicSystem.stopMusic();
            // musicSystem.setDisableMusic(true);
        }

        disableMusic.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // musicSystem.setDisableMusic(!musicSystem.isDisableMusic());
                preferences.putBoolean("MusicOff", disableMusic.isChecked());
                preferences.flush();

                /*
                if (!musicSystem.isDisableMusic()) {
                    musicSystem.playMusic(101, true);
                } else {
                    musicSystem.stopMusic();
                }
                */

            }
        });

        /* Sonido */
        disableSound = WidgetFactory.createCheckBox("Desabilitar sonido");
        if (preferences.getBoolean("SoundOff")) {
            disableSound.setChecked(true);
            // soundsSystem.setDisableSounds(true);
        }
        disableSound.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                preferences.putBoolean("SoundOff", disableSound.isChecked());
                preferences.flush();
                //soundsSystem.setDisableSounds(!soundsSystem.isDisableSounds());
            }
        });

        /* Agrega la imagen del logo */
        // Cell<Image> logoCell = getMainTable().add(WidgetFactory.createImage(new Texture(Gdx.files.local("data/ui/images/logo-big.png")))).center();
        // logoCell.row();

        /* Tabla botones */
        Window buttonsTable = WidgetFactory.createWindow();
        buttonsTable.setMovable(false);
        // buttonsTable.background(WidgetFactory.createDrawable(Drawables.SLOT.name));
        buttonsTable.getTitleLabel().setColor(Color.GOLD);
        buttonsTable.getTitleLabel().setAlignment(2);
        buttonsTable.setHeight(100);
        buttonsTable.add(disableMusic).width(500).pad(10);
        buttonsTable.add(disableSound).width(400).pad(10);

        /* Tabla para login y servers */
        Table login_server = new Table();
        login_server.add(loginWindow).width(500).height(300).padLeft(10).padRight(10).padTop(10);
        // login_server.add(connectionTable).width(400).height(300).padLeft(10).padRight(10).padTop(10); //(*) Seteando acá el size, recursivamente tendría que resizear list.

        /* Tabla principal */
        this.mainTable.add(login_server).row();
        this.mainTable.add(buttonsTable).height(100).width(920).pad(3);

        this.stage.setKeyboardFocus(emailField);
    }

    @Override
    public void show() {
        generateUserInterface();
    }

    @Override
    public void dispose() {
        super.dispose();
        this.backgroundImage.dispose();
        this.backgroundSprite.getSprite().getTexture().dispose();
    }
}