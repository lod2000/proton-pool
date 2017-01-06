package com.spacenerd.protonpool;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class ProtonPool extends ApplicationAdapter implements InputProcessor {
	private SpriteBatch batch;
	private ArrayList<Proton> protons;
    private int protonRadius = 30;
	
	@Override
	public void create () {
        Gdx.input.setInputProcessor(this);

        Proton.texture = new Texture(Gdx.files.internal("proton.png"));

		batch = new SpriteBatch();

        protons = new ArrayList<Proton>();
        protons.add(new Proton("Thing1", new Vector2(200, 200), new Vector2(179, 96)));
        protons.add(new Proton("Thing2", new Vector2(800, 20), new Vector2(-20, 18)));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
        for(Proton proton: protons){
            proton.step();
            proton.draw(batch);
        }
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        protons.add(new Proton("Thing" + protons.size(),
                new Vector2(
                        screenX - protonRadius,
                        Gdx.graphics.getHeight() - screenY - protonRadius
                ),
                new Vector2(0, 0)
        ));
//        Gdx.app.log("ProtonPool", "" + screenY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
