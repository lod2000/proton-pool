package com.spacenerd.protonpool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Proton {
    public static Texture texture;
    public int size;
    public Vector2 position;
    public Vector2 velocity;
    public Vector2 acceleration;
    private Sprite sprite;
    public Circle collisionCircle;

    private static final String TAG = "Proton";

    public static int initialRadius = 30;

    public Proton(int size, Vector2 position, Vector2 velocity, Vector2 acceleration){
        this.size = size;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        sprite = new Sprite(texture, 2 * getProtonRadius(), 2 * getProtonRadius());
        collisionCircle = new Circle(position, initialRadius);
    }

    public void draw(SpriteBatch batch){
        collisionCircle.setPosition(position);
        sprite.setPosition(position.x - getProtonRadius(), position.y - getProtonRadius());
        sprite.setSize(2 * getProtonRadius(), 2 * getProtonRadius());
        sprite.draw(batch);
    }

    public void step(){
        velocity.add(acceleration.cpy().scl(Gdx.graphics.getDeltaTime()));
        if(position.x + initialRadius >= Gdx.graphics.getWidth() || position.x - initialRadius <= 0){
            velocity.x *= -1;
        }
        if(position.y + initialRadius >= Gdx.graphics.getHeight() || position.y - initialRadius <= 0){
            velocity.y *= -1;
        }
        position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime()));
    }

    public int getProtonRadius(){
        return initialRadius + 1 * (size - 1);
    }
}
