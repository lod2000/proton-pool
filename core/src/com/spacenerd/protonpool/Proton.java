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
    public Circle collisionCircle;

    private static final String TAG = "Proton";

    public static int sizeRatio = 30;

    public Proton(int mass, Vector2 position, Vector2 velocity, Vector2 acceleration){
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        sprite = new Sprite(texture, 2 * getProtonRadius(), 2 * getProtonRadius());
        collisionCircle = new Circle(position, sizeRatio);
    }

    public void draw(SpriteBatch batch){
        collisionCircle.setPosition(position);
        sprite.setPosition(position.x - getProtonRadius(), position.y - getProtonRadius());
        sprite.setSize(2 * getProtonRadius(), 2 * getProtonRadius());
        sprite.draw(batch);
    }

    public void step(){
        velocity.add(acceleration.cpy().scl(Gdx.graphics.getDeltaTime()));
        if(position.x + getProtonRadius() >= Gdx.graphics.getWidth() || position.x - getProtonRadius() <= 0){
            velocity.x *= -1;
        }
        if(position.y + getProtonRadius() >= Gdx.graphics.getHeight() || position.y - getProtonRadius() <= 0){
            velocity.y *= -1;
        }
        position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime()));
    }

    public int getProtonRadius(){
        return Math.round(sizeRatio * (float) Math.sqrt(mass));
    }
}
