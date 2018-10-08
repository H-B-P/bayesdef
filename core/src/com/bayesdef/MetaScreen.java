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

import com.bayesdef.BayesDef;
import com.bayesdef.Textures;

import com.badlogic.gdx.math.MathUtils;


public class MetaScreen implements Screen {
	
	OrthographicCamera camera;
	public BayesDef game;
	public SpriteBatch batch;
	
	public float tp_x; //These two lines between them give the True Position of the mouse on the screen.
	public float tp_y; //(This is as opposed to the position in whatever grid we're using in a level)
					   //(Measured in (unscaled) pixels from the bottom left corner of the screen)
	
	//public MetaScreen(BayesDef bayesdef) {
	public MetaScreen(BayesDef bd) {
		//this.game=bayesdef;
		
		game = bd;
		
		batch = new SpriteBatch();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 320, 480);
		Gdx.graphics.setWindowedMode(320, 480);
		
		//this.setScreen(new TitleScreen(this, false)); //Hand off to title screen.
		
	}

	public void render(float delta) {
		
		meta_render();
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		batch.draw(Textures.badlog, 0, 0);
		batch.end();
	}
	
	public void meta_render() {
		
		//check_for_optionchanges();
		
		camera.update();
		
		Vector3 scr_vec= new Vector3(Gdx.input.getX(), Gdx.input.getY(),0); //Get the position of the player's touch.
		Vector3 irl_vec=camera.unproject(scr_vec); // 'Unproject' the position (scale, translate, etc) to get the mouse position in the game world.
		tp_x=irl_vec.x; //extract the x component of mouse position (in pixels)
		tp_y=irl_vec.y; //extract the y component of mouse position (in pixels)
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	/*The point of resize is to handle the possibility of a variable screensize.
	 * 
	 *Scaling up to fill an entire screen leads to games looking stretched and messed up.
	 *And scaling by non-integer factors mucks with the artwork.
	 *
	 *So we work out the largest integer scale factor we can use, then place an appropriately-sized screen at the centre of the device.
	 */
	@Override
	public void resize(int width, int height) {
		float scale=0f; //Harsh but fair: if their screen is less than 160px wide or less than 240px tall, give up.
		if (width>=160 && height>=240){
			scale=0.5f; //Special indulgence for scaling down by a factor of two. It actually doesn't look awful, and better impossibly small than literally invisible.
		}
		if (width>=320 && height>=480){
			scale=1f; //"If the screen is screen-sized, size the screen to be the size of the screen."
		}
		if (width>=480 && height>=720){
			scale=1.5f; //Another special indulgence for scaling by a factor of 1.5.
		}
		if (width>=640 && height>=960){
			scale=2f; //Gotta get the scale to an integer value before the next bit.
		}
		while (width>=(320*(scale+1)) && height>=(480*(scale+1))){
			scale+=1f; //For all n, we see if we could scale up by a factor of n and still fit on the device's screen.
		}
		
		System.out.println("Target scale is: "+ scale); //Useful for debugging.
		
		camera.setToOrtho(false, (float)width/(float)scale, (float)height/(float)scale); //Set the screen to the right size.
		camera.translate(-((float)width/(float)scale-320)/2f, -((float)height/(float)scale-480)/2f); //Center the screen.
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
	
	public void dispose() {
		
	}
	
}