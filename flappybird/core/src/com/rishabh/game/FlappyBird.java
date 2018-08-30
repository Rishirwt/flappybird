package com.rishabh.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture upper;
	Texture lower;
	Texture bird;
	Texture bird2;
	int flapstate=0;
	float birdy=0;
	int gamestate=0;
    int numberoftubes=4;

    int velocity=0;
	int gravity=1;
	int gap=400;
	float maxtubeoffset;
	float tubeoffset[]=new float[numberoftubes];
	Random randomgenerate;
	int score=0;
	int scoringtube=0;
	BitmapFont font;

	float distancebetweentubes;
	Texture gameover;
	float tubevel=4;
	float tubex[]=new float[numberoftubes];
	Circle birdcircle;
	ShapeRenderer renderer;
	Rectangle[] upperpipes;
	Rectangle[] lowerpipes;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gameover=new Texture("gameover.png");
			background = new Texture("bg.png");
		bird =new Texture("bird.png");
	    bird2=new Texture("bird2.png");
	    upper=new Texture("toptube.png");
	    lower=new Texture("bottomtube.png");

	maxtubeoffset=Gdx.graphics.getHeight()/2-100-gap/2;
	randomgenerate=new Random();
	distancebetweentubes=Gdx.graphics.getWidth()* (3/4f);
        renderer=new ShapeRenderer();
        birdcircle=new Circle();
        upperpipes=new Rectangle[numberoftubes];
        lowerpipes=new Rectangle[numberoftubes];
        font=new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(8);
        gamestart();
	}

	@Override
	public void render () {

        batch.begin();
        batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


        if(gamestate==1)
        {
            if(tubex[scoringtube] < Gdx.graphics.getWidth()/2)
            {

                score++;
                if(scoringtube<numberoftubes-1) {
                    scoringtube++;
                }
                else
                {
                    scoringtube=0;
                }
                }
             if(Gdx.input.justTouched())
            {

                velocity=-20;

            }
            for (int i = 0; i <numberoftubes ; i++) {

                if(tubex[i]< -upper.getWidth())
                {
                    tubex[i]=numberoftubes*distancebetweentubes;
                }

                    tubex[i] = tubex[i] - tubevel;

                batch.draw(upper, tubex[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeoffset[i]);
                batch.draw(lower, tubex[i], Gdx.graphics.getHeight() / 2 - gap / 2 - lower.getHeight() + tubeoffset[i]);

                upperpipes[i].set(tubex[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeoffset[i],upper.getWidth(),upper.getHeight());
                lowerpipes[i].set(tubex[i],Gdx.graphics.getHeight() / 2 - gap / 2 - lower.getHeight() + tubeoffset[i],lower.getWidth(),lower.getHeight());
            }

        if(birdy>0) {
            velocity = velocity + gravity;
            birdy -= velocity;
        }
        else
        {
            gamestate=2;
        }
            }
        else if(gamestate==0)
        {
            if(Gdx.input.justTouched())
            {
            gamestate=1;
            }
        }


        else{
            batch.draw(gameover,Gdx.graphics.getWidth()/2-gameover.getWidth()/2,Gdx.graphics.getHeight()/2-gameover.getHeight()/2);

            if(Gdx.input.justTouched())
            {
                gamestate=1;
                gamestart();
                scoringtube=0;
                score=0;
                velocity=0;
            }
        }

        if(flapstate==0) {
            batch.draw(bird, Gdx.graphics.getWidth() / 2 - bird.getWidth() / 2, birdy);
            flapstate=1;
        }
        else {
            batch.draw(bird2, Gdx.graphics.getWidth() / 2 - bird.getWidth() / 2,birdy);
            flapstate=0;
        }
        font.draw(batch,score+" ",50,100);
        batch.end();


        birdcircle.set(Gdx.graphics.getWidth()/2,birdy+bird.getHeight()/2,bird.getWidth()/2);
//        renderer.begin(ShapeRenderer.ShapeType.Filled);
//        renderer.setColor(Color.BLUE);
//        renderer.circle(birdcircle.x,birdcircle.y,birdcircle.radius);
        for (int i = 0; i < numberoftubes; i++) {
//            renderer.rect(tubex[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeoffset[i],upper.getWidth(),upper.getHeight());
//            renderer.rect(tubex[i],Gdx.graphics.getHeight() / 2 - gap / 2 - lower.getHeight() + tubeoffset[i],lower.getWidth(),lower.getHeight());
        if(Intersector.overlaps(birdcircle,upperpipes[i])||Intersector.overlaps(birdcircle,lowerpipes[i]))
        {
            gamestate=2;
        }
        }
       // renderer.end();

	}

    private void gamestart() {
        birdy= Gdx.graphics.getHeight() / 2 - bird.getHeight() / 2;
        for (int i = 0; i < numberoftubes; i++) {
            tubeoffset[i]=(randomgenerate.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-gap-200);
            tubex[i]=Gdx.graphics.getWidth()/2-upper.getWidth()/2 + Gdx.graphics.getWidth()+i *distancebetweentubes;
            upperpipes[i]=new Rectangle();
            lowerpipes[i]=new Rectangle();
        }
	}


}
