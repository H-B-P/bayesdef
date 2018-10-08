package com.bayesdef;

/*
 * ~SUMMARY~
 * 
 */

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Rectangle;

import com.bayesdef.BayesDef;
import com.bayesdef.Textures;

public class SpaceScreen extends MetaScreen {
	
	float TIMESPEED;
	
	float prettySpeed;
	float prettyBGheight=Textures.prettyBG.getHeight();
	
	//float shipLocn=120;
	
	float totalTime=0;
	float playerTime=0;
	
	int shieldsRemaining=1;
	
	Rectangle screenProper=new Rectangle(0, 0, 320, 400);
	Rectangle screenExtended=new Rectangle(-20, -20, 360, 540);
	
	Rectangle theShip = new Rectangle();
	Rectangle theShield = new Rectangle();
	
	public SpaceScreen(BayesDef bd) {
		super(bd);
		
		
		theShip.width=320;
		theShip.height=480;
		theShip.x=0;
		theShip.y=120-theShip.height;
		
		theShield.width=280;
		theShield.height=5;
		theShield.x=20;
		theShield.y=theShip.y+theShip.height+15;
		
		TIMESPEED=1;
		prettySpeed=20;
	}

	public void render(float delta) {
		
		space_render(delta);
		
	}
	
	public void space_render(float delta) {
		
		meta_render();
		
		totalTime += delta*TIMESPEED; //Increment time in-game: how much time passed for the passengers?
		playerTime += delta; //Increment time out-of-game: how much time passed for the player?
		
		theShield.y=theShip.y+theShip.height+15;
		
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		draw_pretty_background();
		batch.draw(Textures.theShip, theShip.x, theShip.y);
		draw_the_shield();
		batch.draw(Textures.statusBar, 0, 400);
		batch.draw(Textures.letterboxPoncho, -640, -960);
		batch.end();
		
	}
	
	void draw_the_shield(){
		if (shieldsRemaining==1){
			batch.draw(Textures.Shields.one, theShield.x, theShield.y);
		}
		if (shieldsRemaining==2){
			batch.draw(Textures.Shields.two, theShield.x, theShield.y);
		}
		if (shieldsRemaining==3){
			batch.draw(Textures.Shields.three, theShield.x, theShield.y);
		}
		if (shieldsRemaining==4){
			batch.draw(Textures.Shields.four, theShield.x, theShield.y);
		}
		if (shieldsRemaining==5){
			batch.draw(Textures.Shields.five, theShield.x, theShield.y);
		}
	}
	
	void draw_pretty_background(){
		batch.draw(Textures.prettyBG, 0f, prettyBGheight-(float)((totalTime*prettySpeed)%(prettyBGheight*2)));
		batch.draw(Textures.prettyBG, 0f, prettyBGheight-(float)((totalTime*prettySpeed+prettyBGheight)%(prettyBGheight*2)));
		batch.draw(Textures.prettyBG, 0f, prettyBGheight-(float)((totalTime*prettySpeed+prettyBGheight*2)%(prettyBGheight*2)));

	}

}