package com.spacenerd.protonpool;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class ProtonPool extends ApplicationAdapter implements InputProcessor {
	private SpriteBatch batch;
	private ArrayList<Proton> protons;
    private static final float forceConstant = 500000;
    private ShapeRenderer shapeRenderer;
    private Preferences prefs;
    private ArrayList<Proton> toRemove;
	
	@Override
	public void create () {
        prefs = Gdx.app.getPreferences("Preferences");
        prefs.putBoolean("velocityLines", true);
        prefs.putBoolean("accelerationLines", true);
        prefs.flush();

        Gdx.input.setInputProcessor(this);

        Proton.texture = new Texture(Gdx.files.internal("proton.png"));

		batch = new SpriteBatch();

        protons = new ArrayList<Proton>();

        shapeRenderer = new ShapeRenderer();

        toRemove = new ArrayList<Proton>();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

        force();
        for(Proton proton: protons){
            proton.step();
            proton.draw(batch);
        }
        for(int i = 0; i < toRemove.size(); i++){
            protons.remove(toRemove.get(i));
        }
		batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for(Proton proton: protons){
            if(prefs.getBoolean("accelerationLines", false)){
                shapeRenderer.setColor(0, 0, 1, 1);
                shapeRenderer.line(proton.position, proton.position.cpy().add(proton.acceleration));
            }
            if(prefs.getBoolean("velocityLines", false)){
                shapeRenderer.setColor(0, 1, 0, 1);
                shapeRenderer.line(proton.position, proton.position.cpy().add(proton.velocity));
            }
        }
        shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

    private void force(){
        for(Proton target: protons){
            Vector2 force = new Vector2();
            for(Proton other: protons){
                if(other != target){
                    Vector2 distance = target.position.cpy().sub(other.position.cpy());
//                    Vector2 distance = target.position.dst(other.position);
                    Vector2 partialForce = new Vector2(1, 1)
                            .setLength((float) (forceConstant * other.size * target.size /
                                    Math.pow(distance.len(), 2)))
                            .setAngle(distance.angle());
                    force.add(partialForce);
                    if(distance.len() < Proton.initialRadius
                            && !toRemove.contains(target)
                            && target.size >= other.size){
                        target.velocity = other.velocity.scl(other.size).add(target.velocity.scl(target.size)).scl(1 / (other.size + target.size));
                        target.size += other.size;
                        toRemove.add(other);
//                        Gdx.app.log("ProtonPool", "" + target.size);
                    }
                }
            }
            target.acceleration = force.scl(1 / (float) target.size);
        }
    }

    private void collision(){
        for(Proton target: protons){
            for(Proton other: protons){
                if(other != target){
                    Vector2 distance = target.position.cpy().sub(other.position.cpy());
                    if(distance.len() < Proton.initialRadius
                            && !toRemove.contains(target)
                            && target.size >= other.size){
                        target.velocity = other.velocity.scl(other.size).add(target.velocity.scl(target.size)).scl(1 / (other.size + target.size));
                        target.size += other.size;
                        toRemove.add(other);
                        Gdx.app.log("ProtonPool", "" + target.size);
                    }
                }
            }
        }
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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(screenX < Proton.initialRadius){
            screenX = Proton.initialRadius;
        }
        if(Gdx.graphics.getWidth() - screenX < Proton.initialRadius){
            screenX = Gdx.graphics.getWidth() - Proton.initialRadius;
        }
        if(screenY < Proton.initialRadius){
            screenY = Proton.initialRadius;
        }
        if(Gdx.graphics.getHeight() - screenY < Proton.initialRadius){
            screenY = Gdx.graphics.getHeight() - Proton.initialRadius;
        }
        protons.add(new Proton(
                1, //size
                new Vector2(screenX, Gdx.graphics.getHeight() - screenY), //position
                new Vector2(0, 0), //velocity
                new Vector2(0, 0) //acceleration
        ));
        return true;
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
