package com.spacenerd.protonpool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Proton {
    public static Texture texture;
    public int mass;
    public Vector2 position;
    public Vector2 velocity;
    public Vector2 acceleration;
    private Sprite sprite;
    public static int radius;

    private static final String TAG = "Proton";

    public static int sizeRatio = 30;

    public Proton(int mass, Vector2 position, Vector2 velocity, Vector2 acceleration){
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        sprite = new Sprite(texture, 2 * radius, 2 * radius);
    }

    public void draw(SpriteBatch batch){
        sprite.setPosition(position.x - radius, position.y - radius);
        sprite.setSize(2 * radius, 2 * radius);
        sprite.draw(batch);
    }

    // Calculates new velocities and positions for proton
    public void step(){
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
}
