package com.spacenerd.protonpool;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainUi {
    public Stage stage;

    private CheckBox showVelocityBox;
    private CheckBox showAccelerationBox;
    private CheckBox.CheckBoxStyle checkBoxStyle;

    public MainUi(AssetManager assetManager, BitmapFont font) {
        stage = new Stage();

        checkBoxStyle = new CheckBox.CheckBoxStyle();
        checkBoxStyle.font = assetManager.get("fonts/Roboto/Roboto-Regular.ttf");
        checkBoxStyle.fontColor = new Color(Color.WHITE);
//        checkBoxStyle.checkboxOff = check_skin.getDrawable("checkbox");
//        checkBoxStyle.checkboxOn = check_skin.getDrawable("checkbox2");
//        checkBoxStyle.checked = check_skin.getDrawable("checkbox2");

        showVelocityBox = new CheckBox("Show velocities?", checkBoxStyle);
        showVelocityBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
    }

    public void draw() {

    }
}
