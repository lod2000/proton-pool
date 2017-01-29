package com.spacenerd.protonpool;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class ProtonPool extends ApplicationAdapter implements InputProcessor {
	private ArrayList<Proton> protons; // List of protons
    private ArrayList<Proton> toRemove; // List of protons to remove after collisions

    private MainUi ui;

    private static final float forceConstant = 500000;

    private ShapeRenderer shapeRenderer;
    public static Preferences prefs; // User preferences

    private static final String TAG = "ProtonPool";
	
	@Override
	public void create () {
        // Load preferences
        prefs = Gdx.app.getPreferences("Preferences");
        prefs.putBoolean("showVelocity", true);
        prefs.putBoolean("showAcceleration", true);
        prefs.flush();

//        AssetManager manager = new AssetManager();
//        FileHandleResolver resolver = new InternalFileHandleResolver();
//        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
//        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
//
//        //load the label font
//        FreeTypeFontLoaderParameter labelFontParams = new FreeTypeFontLoaderParameter();
//        labelFontParams.fontFileName = "fonts/Roboto/Roboto-Regular.ttf";
//        labelFontParams.fontParameters.size = Math.round(20.0f * Gdx.graphics.getDensity());
//        manager.load("fonts/Roboto/Roboto-Regular.ttf", BitmapFont.class, labelFontParams);
//
//        manager.finishLoading();
//
//        BitmapFont font = manager.get("fonts/Roboto/Roboto-Regular.ttf");

        Gdx.input.setInputProcessor(this);

        protons = new ArrayList<Proton>();
        toRemove = new ArrayList<Proton>();

        shapeRenderer = new ShapeRenderer();

//        ui = new MainUi();
//        Gdx.input.setInputProcessor(ui.stage);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw protons
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

        ui.draw();
	}
	
	@Override
	public void dispose () {
        shapeRenderer.dispose();
	}

    private void force(){
        toRemove = new ArrayList<Proton>(); // List of protons to remove after collisions
        // Check each proton
        for(Proton main: protons){
            Vector2 force = new Vector2(); // Net force on main proton
            // Calculate interactions with every other proton
            for(Proton other: protons){
                if(other != main){
                    // Calculate distance vector between proton centers
                    Vector2 distance = main.position.cpy().sub(other.position.cpy());
                    // Calculate force between main and other proton
                    Vector2 partialForce = new Vector2(1, 1)
                            .setLength((float) (forceConstant * other.mass * main.mass /
                                    Math.pow(distance.len(), 2)))
                            .setAngle(distance.angle());
                    force.add(partialForce); // Add partial force to net force

                    // If two protons come too close together, they merge
                    if(distance.len() < Math.max(main.radius, other.radius)
                            && !toRemove.contains(main)
                            && main.mass >= other.mass){
                        // Conserve momentum during merge
                        main.velocity.scl(main.mass)
                                .add(other.velocity.scl(other.mass))
                                .scl(1 / (other.mass + main.mass));
                        // Combine masses
                        main.mass += other.mass;
                        // Remove smaller proton from simulation
                        toRemove.add(other);

                        Gdx.app.log(TAG, "Merge event at " + main.position);
                        Gdx.app.log(TAG, "Merge mass: " + main.mass);
                    }
                }
            }
            // Divide force by mass to get acceleration
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
        // Add a new proton at the specified position
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
