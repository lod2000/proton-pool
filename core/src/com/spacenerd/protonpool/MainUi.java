package com.spacenerd.protonpool;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;

public class MainUi {
    public Stage stage;

    private CheckBox showVelocityBox;
    private CheckBox showAccelerationBox;
    private CheckBox.CheckBoxStyle checkBoxStyle;

    public MainUi(BitmapFont font) {
        stage = new Stage();

        checkBoxStyle = new CheckBox.CheckBoxStyle();
//        checkBoxStyle.font = assets.get("fonts/Roboto/Roboto-Regular.ttf");
//        checkBoxStyle.fontColor = new Color(Color.WHITE);
//        checkBoxStyle.checkboxOff = check_skin.getDrawable("checkbox");
//        checkBoxStyle.checkboxOn = check_skin.getDrawable("checkbox2");
//        checkBoxStyle.checked = check_skin.getDrawable("checkbox2");

//        showVelocityBox = new CheckBox()
    }

    public void draw() {

    }
}
