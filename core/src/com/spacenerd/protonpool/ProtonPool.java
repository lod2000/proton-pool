package com.spacenerd.protonpool;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class ProtonPool extends ApplicationAdapter {
	private SpriteBatch batch;
	private ArrayList<Proton> protons;
	
	@Override
	public void create () {
//        AssetManager manager = new AssetManager();
//        manager.load("proton.png", Texture.class);
//        Proton.texture = manager.get("proton.png");
        Proton.texture = new Texture(Gdx.files.internal("proton.png"));

		batch = new SpriteBatch();

        protons = new ArrayList<Proton>();
        protons.add(new Proton("TEST", new Vector2(200, 200), new Vector2(2, 2)));
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
}
