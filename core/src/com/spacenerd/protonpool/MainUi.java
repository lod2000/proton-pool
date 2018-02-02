package com.spacenerd.protonpool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MainUi {
    public Stage stage;

    private CheckBox showVelocityBox, showAccelerationBox;
    private CheckBox.CheckBoxStyle checkBoxStyle;
    private ImageButton settingsButton;
    private int buttonSize, buttonGap, checkBoxHeight, checkBoxGap;

    private static final String TAG = "MainUi";

    public MainUi(AssetManager assetManager) {
        stage = new Stage();

        buttonSize = (int) (10 * Gdx.graphics.getDensity());
        buttonGap = (int) (2 * Gdx.graphics.getDensity());
        checkBoxHeight = (int) (20 * Gdx.graphics.getDensity());
        checkBoxGap = (int) (5* Gdx.graphics.getDensity());

        Drawable uncheckedDrawable = new TextureRegionDrawable(
                new TextureRegion((Texture) assetManager.get("buttons/unchecked-checkbox.png"))
        );
        Drawable checkedDrawable = new TextureRegionDrawable(
                new TextureRegion((Texture) assetManager.get("buttons/checked-checkbox.png"))
        );
        checkBoxStyle = new CheckBox.CheckBoxStyle();
        checkBoxStyle.font = assetManager.get("fonts/Roboto/Roboto-Regular.ttf");
        checkBoxStyle.fontColor = new Color(Color.WHITE);
        checkBoxStyle.checkboxOff = uncheckedDrawable;
        checkBoxStyle.checkboxOn = checkedDrawable;
//        checkBoxStyle.checkboxOff = check_skin.getDrawable("checkbox");
//        checkBoxStyle.checkboxOn = check_skin.getDrawable("checkbox2");

        showVelocityBox = new CheckBox("Show velocities?", checkBoxStyle);
        showVelocityBox.setPosition(
                Gdx.graphics.getHeight() - checkBoxGap - checkBoxHeight,
                checkBoxGap
        );
        showVelocityBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log(TAG,"Velocity checkbox tapped");
            }
        });
        stage.addActor(showVelocityBox);
        showVelocityBox.setVisible(false);

        showAccelerationBox = new CheckBox("Show accelerations?", checkBoxStyle);
        showAccelerationBox.setPosition(
                Gdx.graphics.getHeight() - 2 * checkBoxGap - 2 * checkBoxHeight,
                checkBoxGap
        );
        showAccelerationBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log(TAG,"Acceleration checkbox tapped");
            }
        });
        stage.addActor(showAccelerationBox);
        showAccelerationBox.setVisible(false);

        // Settings button
        Drawable settingsDrawable = new TextureRegionDrawable(
                new TextureRegion((Texture) assetManager.get("buttons/settings.png"))
        );
        settingsButton = new ImageButton(settingsDrawable);
        settingsButton.setSize(buttonSize, buttonSize);
        settingsButton.setPosition(
                Gdx.graphics.getWidth() - buttonGap,
                Gdx.graphics.getHeight() - buttonGap
        );
        settingsButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                boolean currentState = ProtonPool.getPaused();
                toggleSettings(!currentState);
            }
        });
        stage.addActor(settingsButton);
        settingsButton.setVisible(true);
    }

    private void toggleSettings(boolean isVisible){
        showVelocityBox.setVisible(isVisible);
        showAccelerationBox.setVisible(isVisible);
        ProtonPool.setPaused(isVisible);
    }

    public void draw() {

    }
}
