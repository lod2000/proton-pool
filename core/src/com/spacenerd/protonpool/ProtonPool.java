package com.spacenerd.protonpool;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class ProtonPool extends ApplicationAdapter implements InputProcessor {
	private ArrayList<Proton> protons;
    private static final float forceConstant = 500000;
    private ShapeRenderer shapeRenderer;
    private Preferences prefs;
    private ArrayList<Proton> toRemove;

    private static final String TAG = "ProtonPool";
	
	@Override
	public void create () {
        prefs = Gdx.app.getPreferences("Preferences");
        prefs.putBoolean("showVelocity", true);
        prefs.putBoolean("showAcceleration", true);
        prefs.flush();

        Gdx.input.setInputProcessor(this);

        protons = new ArrayList<Proton>();

        shapeRenderer = new ShapeRenderer();

        toRemove = new ArrayList<Proton>();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            force(); // Calculate forces between protons, find collisions
            for(int i = 0; i < toRemove.size(); i++){
                protons.remove(toRemove.get(i)); // Remove protons that have collided and merged
            }
            for(Proton proton: protons){
                proton.step(); // Calculate new velocities, positions for protons
                proton.draw(shapeRenderer); // Draw protons
            }
        shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
        shapeRenderer.dispose();
	}

    private void force(){
        toRemove = new ArrayList<Proton>();
        for(Proton main: protons){
            Vector2 force = new Vector2();
            for(Proton other: protons){
                if(other != main){
                    Vector2 distance = main.position.cpy().sub(other.position.cpy());
                    Vector2 partialForce = new Vector2(1, 1)
                            .setLength((float) (forceConstant * other.mass * main.mass /
                                    Math.pow(distance.len(), 2)))
                            .setAngle(distance.angle());
                    force.add(partialForce);
                    if(distance.len() < (main.radius + other.radius) / 2
                            && !toRemove.contains(main)
                            && main.mass >= other.mass){
                        main.velocity = other.velocity
                                .scl(other.mass)
                                .add(main.velocity.scl(main.mass))
                                .scl(1 / (other.mass + main.mass));
                        main.mass += other.mass;
                        toRemove.add(other);
                        Gdx.app.log(TAG, "Merge event at " + main.position);
                        Gdx.app.log(TAG, "Merge mass: " + main.mass);
                    }
                }
            }
            main.acceleration = force.scl(1 / (float) main.mass);
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
        protons.add(new Proton(
                1, // Mass
                new Vector2(screenX, Gdx.graphics.getHeight() - screenY), // Position
                new Vector2(0, 0), // Velocity
                new Vector2(0, 0) // Acceleration
        ));
        Gdx.app.log(TAG, "Added proton at (" + screenX + ", " + screenY + ")");
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
