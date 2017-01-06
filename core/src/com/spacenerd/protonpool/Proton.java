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
    private Sprite sprite;

    public Proton(String name, Vector2 position, Vector2 velocity){
        this.name = name;
        this.position = position;
        this.velocity = velocity;
        sprite = new Sprite(texture);
    }

    public void draw(SpriteBatch batch){
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }

    public void step(){
        if(position.x + sprite.getWidth() >= Gdx.graphics.getWidth() || position.x <= 0){
            velocity.x *= -1;
        }
        if(position.y + sprite.getHeight() >= Gdx.graphics.getHeight() || position.y <= 0){
            velocity.y *= -1;
        }
        position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime()));
    }
}
