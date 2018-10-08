package com.bayesdef;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.audio.Sound;

public class GameScreen extends SpaceScreen{
	
	Array<Mine> mines;
	Array<Turret> turrets;
	Array<Explosion> explosions;
	Array<Shot> shots;
	
	Rectangle menuButtonRect = new Rectangle(230,420,100,40);
    Rectangle fireButtonRect = new Rectangle(10,420,100,40);
    
    boolean bayesian = false;
    
	public GameScreen(final BayesDef bd) {
		
		super(bd);
		
		mines = new Array<Mine>();
		turrets = new Array<Turret>();
		explosions = new Array<Explosion>();
		shots = new Array<Shot>();

	}
	
	@Override
	
	public void render(float delta){
		game_render(delta);
		handle_exits();
	}
	
	public void game_render(float delta){
		space_render(delta);
		process_everything(delta);
		batch.begin();
		draw_everything();
		batch.end();
	}
	
	//Processing
	
	void process_everything(float delta){
		
		collect_captured_mines();
		collect_stray_shots();
		
		for (Mine mine: mines){
			mine.update_posn(delta*TIMESPEED);
		}
		
		for (Shot shot:shots){
			shot.update_posn(delta*TIMESPEED);
		}
		
		time_out_explosions();
		
		
	}
	
	void time_out_explosions(){
		for (Explosion boom: explosions){
			if((totalTime-boom.birthtime)>0.2f){
				explosions.removeValue(boom, true);
			}
		}
	}
	
	void handle_exits(){
		
	}
	
	void collect_captured_mines(){
		   for (Mine mine: mines){
			   if (mine.beingDetained){
				   if (!mine.rect.overlaps(screenProper)){					   
					   //captured+=1;
					   //minecount-=1;
					   mines.removeValue(mine,true);
				   }
			   }
		   }
	   }
	
	void collect_stray_shots(){
		   for (Shot shot: shots){
			   if (!shot.rect.overlaps(screenExtended)){
				   shots.removeValue(shot,true);
			   }
		   }
	   }
	
	//Drawing
	
	public void draw_everything(){
		draw_mines();
		draw_explosions();
		
		draw_turrets();
		draw_shots();
		draw_overlays();
		
		batch.draw(Textures.statusBar, 0, 400);
		batch.draw(Textures.letterboxPoncho, -640, -960);
	}
	
	void draw_mines(){
		for(Mine mine: mines) {
		    batch.draw(Textures.mine, mine.rect.x-20, mine.rect.y-20);
		    if (mine.beingDetained){
		    	batch.draw(Textures.detainingCircle, mine.rect.x-20, mine.rect.y-20);
		    }
		}
	}
	
	void draw_explosions(){
		for(Explosion boom: explosions) {
			if (boom.big){
				batch.draw(Textures.bigExplosion, boom.rect.x, boom.rect.y);
			}
			else{
				batch.draw(Textures.explosion, boom.rect.x, boom.rect.y);
			}
		}
	}
	
	void draw_turrets(){
		for (Turret turret: turrets){
			batch.draw(turret.currentT, turret.rect.x, turret.rect.y);
		}
	}
	
	void draw_shots(){
		for (Shot shot: shots){
			if (shot.type.equals("capture")){
				batch.draw(Textures.Shots.capture, shot.rect.x, shot.rect.y);
			}
			if (shot.type.equals("destroy")){
				//batch.draw(Textures.Shots.destroy, shot.rect.x-10, shot.rect.y-20);
				batch.draw(Textures.Shots.destroy, shot.rect.x-10, shot.rect.y-20, 10,20, (float)Textures.Shots.destroy.getWidth(), (float)Textures.Shots.destroy.getHeight(), 1f, 1f, -MathUtils.radiansToDegrees*MathUtils.atan2(shot.horzVel, shot.vertVel), 0, 0, Textures.Shots.destroy.getWidth(), Textures.Shots.destroy.getHeight(), false, false);
			}
			
		}
	}
	
	void draw_overlays(){
		for (Turret turret: turrets){
			batch.draw(turret.emptytopT, turret.rect.x, turret.rect.y);
		}
	}
	
	// ===Shared Conveniences===
	
	void exit_stage_whatever(Mine exitingMine){
		   if (exitingMine.rect.x>160){
			   exitingMine.horzVel=2000;
		   }
		   else{
			   exitingMine.horzVel=-2000;
		   }
	   }
	
	//---Spawning functions---
	
		void spawn_explosion(float X, float Y){
			   Explosion boom = new Explosion();
			   boom.rect= new Rectangle();
			   boom.birthtime=totalTime;

			   boom.rect.x= X-20;
			   boom.rect.y= Y-20;
			   boom.rect.width=80;
			   boom.rect.height=80;
			   
			   boom.big=false;
			   explosions.add(boom);
		}
		
		void spawn_big_explosion(float X, float Y){
			   Explosion boom = new Explosion();
			   boom.rect= new Rectangle();
			   boom.birthtime=totalTime;
			   
			   boom.rect.x= X-30;
			   boom.rect.y= Y-30;
			   boom.rect.width=100;
			   boom.rect.height=100;
			   
			   boom.big=true;
			   explosions.add(boom);
		}
		
		// ---Death---
		
		void initiate_failure(){
			
		}
}
