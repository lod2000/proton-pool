package com.spacenerd.protonpool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Proton {
    public static Texture texture;
    public String name;
    public Vector2 position;
    public Vector2 velocity;
    public Vector2 acceleration;
    private Sprite sprite;
    public Circle collisionCircle;

    public static final int radius = 30;

    public Proton(String name, Vector2 position, Vector2 velocity, Vector2 acceleration){
        this.name = name;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        sprite = new Sprite(texture);
        collisionCircle = new Circle(position, radius);
    }

    public void draw(SpriteBatch batch){
        collisionCircle.setPosition(position);
        sprite.setPosition(position.x - radius, position.y - radius);
        sprite.draw(batch);
    }

    public void step(){
//        Gdx.app.log("Proton", acceleration + "");
        velocity.add(acceleration.cpy().scl(Gdx.graphics.getDeltaTime()));
        if(position.x + radius >= Gdx.graphics.getWidth() || position.x - radius <= 0){
            velocity.x *= -1;
        }
        if(position.y + radius >= Gdx.graphics.getHeight() || position.y - radius <= 0){
            velocity.y *= -1;
        }
        position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime()));
    }
}
