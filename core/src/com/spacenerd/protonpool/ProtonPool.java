package com.spacenerd.protonpool;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class ProtonPool extends ApplicationAdapter implements InputProcessor {
	private SpriteBatch batch;
	private ArrayList<Proton> protons;
    private static final float forceConstant = 100000;
    private ShapeRenderer shapeRenderer; //debugging only
    private OrthographicCamera camera;
	
	@Override
	public void create () {
        Gdx.input.setInputProcessor(this);

        Proton.texture = new Texture(Gdx.files.internal("proton.png"));

		batch = new SpriteBatch();

        protons = new ArrayList<Proton>();
        protons.add(new Proton("Thing1",
                new Vector2(200, 200),
                new Vector2(100, 10),
                new Vector2(0,0)
        ));
        protons.add(new Proton("Thing2",
                new Vector2(800, 50),
                new Vector2(-20, -18),
                new Vector2(0,0)
        ));

        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        shapeRenderer.setProjectionMatrix(camera);

		batch.begin();
        for(Proton proton: protons){
            sumForce();
            proton.step();
            proton.draw(batch);
        }
		batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for(Proton proton: protons){
            shapeRenderer.setColor(0, 0, 1, 1);
            shapeRenderer.line(proton.position, proton.position.cpy().add(proton.acceleration.scl(100)));
        }
        shapeRenderer.end();

//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        for(Proton proton: protons){
//            shapeRenderer.setColor(0, 1, 0, 1);
//            shapeRenderer.line(proton.position, proton.position.cpy().add(proton.velocity.scl(10)));
//        }
//        shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

    public void sumForce(){
        for(Proton target: protons){
            Vector2 acceleration = new Vector2();
            for(Proton other: protons){
                if(other != target){
                    Vector2 distance = target.position.cpy().sub(other.position.cpy());
                    Vector2 partialAcceleration = new Vector2(1, 1)
                            .setLength((float) (forceConstant / Math.pow(distance.len(), 2)))
                            .setAngle(distance.angle());
//                            (float) (forceConstant / Math.pow(distance.len2(), 2)),
//                            (float) (forceConstant / Math.pow(distance.len, 2))
//                    );
//                    Gdx.app.log("ProtonPool", "" + partialAcceleration);
                    acceleration.add(partialAcceleration);
//                    if(target.collisionCircle.overlaps(other.collisionCircle)){
//                        Vector2 distance = target.position.cpy().sub(other.position.cpy());
//                        distance.rotate(90);
//                    }
                }
            }
            target.acceleration = acceleration;
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
        if(screenX < Proton.radius){
            screenX = Proton.radius;
        }
        if(Gdx.graphics.getWidth() - screenX < Proton.radius){
            screenX = Gdx.graphics.getWidth() - Proton.radius;
        }
        if(screenY < Proton.radius){
            screenY = Proton.radius;
        }
        if(Gdx.graphics.getHeight() - screenY < Proton.radius){
            screenY = Gdx.graphics.getHeight() - Proton.radius;
        }
        protons.add(new Proton("Thing" + protons.size(),
                new Vector2(screenX, Gdx.graphics.getHeight() - screenY),
                new Vector2(0, 0),
                new Vector2(0, 0)
        ));
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
