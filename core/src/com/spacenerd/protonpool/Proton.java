package com.spacenerd.protonpool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Proton {
    public int mass;
    public Vector2 position;
    public Vector2 velocity;
    public Vector2 acceleration;
    public int radius;

    private static final String TAG = "Proton";

    // Proton radius multiplier
    public static int sizeRatio = Math.round(10 * Gdx.graphics.getDensity());

    public Proton(int mass, Vector2 position, Vector2 velocity, Vector2 acceleration){
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        radius = sizeRatio; // Initial radius
    }

    // Calculates new velocities and positions for proton
    public void step(){
        // Calculate proton radius
        radius = Math.round(sizeRatio * (float) Math.sqrt(mass));

        // Shift proton if it overlaps edge
        if(position.x < radius){
            position.x = radius;
        }
        if(Gdx.graphics.getWidth() - position.x < radius){
            position.x = Gdx.graphics.getWidth() - radius;
        }
        if(position.y < radius){
            position.y = radius;
        }
        if(Gdx.graphics.getHeight() - position.y < radius){
            position.y = Gdx.graphics.getHeight() - radius;
        }

        // Reverse velocity if proton hits edge
        if(position.x + radius >= Gdx.graphics.getWidth() || position.x - radius <= 0){
            velocity.x *= -1;
        }
        if(position.y + radius >= Gdx.graphics.getHeight() || position.y - radius <= 0){
            velocity.y *= -1;
        }

        // Change position by velocity
        position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime()));

        // Change velocity by acceleration
        velocity.add(acceleration.cpy().scl(Gdx.graphics.getDeltaTime()));
    }

    public void draw(ShapeRenderer shapeRenderer){
        Preferences prefs = Gdx.app.getPreferences("Preferences");
        shapeRenderer.setColor(0.7f, 0, 0, 1);
        shapeRenderer.circle(position.x, position.y, radius);
        if(prefs.getBoolean("showVelocity", false)){
            shapeRenderer.setColor(0, 1, 0, 1);
            shapeRenderer.line(position, position.cpy().add(velocity));
        }
        if(prefs.getBoolean("showAcceleration", false)){
            shapeRenderer.setColor(0, 0, 1, 1);
            shapeRenderer.line(position, position.cpy().add(acceleration));
        }
    }
}
