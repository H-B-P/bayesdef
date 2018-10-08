package com.bayesdef;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
//import com.hbp.probdef.RT_Kaboom;
//import com.hbp.probdef.Mine;
//import com.hbp.probdef.Turret;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Input.Keys;

public class ProbScreen extends GameScreen{
	
    int seconds = 0;
    
    boolean bayesian = false;
    
    String currentStatus = "waiting";
    
    Turret currentlyActiveTurret = null;
    
    float volleyEndingTime=0;
    
	public ProbScreen(final BayesDef bd) {
		
		super(bd);
		shieldsRemaining=5;
		
		level_specific_turret_setup();
	}
	
	@Override
	
	public void render(float delta){
		game_render(delta);
		
		check_for_shield_mine_collisions();
		check_for_ship_mine_collisions();
		check_for_shot_mine_collisions();
		
		do_status_relevant_things();
		
		for (Turret turret: turrets){
			turret.rect.y = theShip.y + theShip.height - turret.rect.height - 20;
		}
	}
	
	// ===Implications of Status===
	
	void do_status_relevant_things(){
		if (currentStatus.equals("waiting")){
			TIMESPEED=1;
			do_waiting_things();
		}
		//else here?
		if (currentStatus.equals("targeting")){
			TIMESPEED=0;
			do_targeting_things();
		}
		if (currentStatus.equals("firing")){
			TIMESPEED=0.1f;
			do_firing_things();
		}
	}
	
	void do_waiting_things(){
		if ((seconds+1)<totalTime){
			seconds+=1;
			System.out.println(seconds);
			if (any_targetable_mines()){
				currentStatus="targeting";
			}
			level_specific_events();
		}
	}
	
	void do_targeting_things(){
		
		//Draw targeting
		
		draw_targeting();
		
		//Change to tapped turrets
		
		if (Gdx.input.justTouched()){
			for (Turret turret: turrets){
				if (turret.rect.contains(tp_x, tp_y)){
					turret.targeted = false;
					currentlyActiveTurret = turret;
				}
			}
		}
		
		//Target turrets
		
		if (currentlyActiveTurret!=null){
			if (Gdx.input.isKeyJustPressed(Keys.SPACE)){
				currentlyActiveTurret.targeted = true;
			}
			else if (Gdx.input.justTouched()){
				for (Mine mine: mines){
					if (mine.rect.contains(tp_x,tp_y)){
						currentlyActiveTurret.targeted = true;
						currentlyActiveTurret.targetMine = mine;
					}
				}
			}
		}		
		
		//Pick new turrets
		
		if (currentlyActiveTurret==null){
			cycle_through_turrets();
		}
		else if (currentlyActiveTurret.targeted){
			cycle_through_turrets();
		}
		
		//Handle handover
		
		if (currentlyActiveTurret==null && (!Options.waitForFiringButton || Gdx.input.isKeyJustPressed(Keys.SPACE))){
			set_up_firing_times();
			currentStatus="firing";
		}
		if (Gdx.input.justTouched() && fireButtonRect.contains(tp_x,tp_y)){
			set_up_firing_times();
			currentStatus="firing";
		}
		
	}
	
	void do_firing_things(){
		
		draw_targeting();
		//draw_miss_statements(); KILL THIS
		
		for (Turret turret: turrets){
			if (turret.targetMine!=null){
				if (turret.targeted && turret.firingTime<totalTime){
					if (!turret.targetMine.beingDetained && turret.targetMine.actuallyExists){
						Sounds.fire.play(Options.SFXVolume*0.3f);
						Shot shot = new Shot(turret.rect, turret.targetMine, 3000, turret.determine_output());
						shots.add(shot);
						turret.currentT=turret.firingT;
						turret.targeted=false;
						turret.targetMine=null;
					}
					else{
						turret.targeted=false;
					}
				}
			}
			
			if ((turret.firingTime+0.05f)<totalTime){
				turret.currentT=turret.normalT;
		   }
		}
		
		if (totalTime>volleyEndingTime){
			
			for (Turret turret: turrets){
				turret.targeted=false;
				turret.targetMine=null;
			}
			
			currentStatus = "waiting";
		}
	}
	
	// ===Level-Specific Functions===
	
	void level_specific_turret_setup(){
		
		Turret turretOne = new Turret("triangle");
		Turret turretTwo = new Turret("triangle");
		
		turretOne.rect.x = 50;
		turretTwo.rect.x = 230;
		
		turretOne.rect.y = 60;
		turretTwo.rect.y = 60;
		
		
		turretOne.targetingXOffset=-19;
		turretOne.targetingYOffset=0;
		
		turretTwo.targetingXOffset=0;
		turretTwo.targetingYOffset=-19;
		
		
		turrets.add(turretOne);
		turrets.add(turretTwo);
	}
	
	void level_specific_events(){
		
		if (seconds==1){
			spawnMine(-1);
		}
		
		if (seconds==3){
			spawnMine(-2);
			spawnMine(0);
			spawnMine(2);
		}
		
		if (seconds==4){
			spawnMine(1);
		}
		
		if (seconds==5){
			spawnMine(3);
		}
		
		if (seconds==6){
			spawnMine(2);
		}
		
		if (seconds==7){
			spawnMine(1);
		}
	}
	
	// ===Check Functions===
	
	void check_for_shield_mine_collisions(){
		for (Mine mine: mines){
		   if(mine.rect.y<(theShield.y+theShield.height/2) && shieldsRemaining>0) {
			     	spawn_explosion(mine.rect.x,mine.rect.y);
			        shieldsRemaining-=1;
			        Sounds.mineHitUs.play(Options.SFXVolume*0.4f);
			        Sounds.mineSplode.play(Options.SFXVolume);
			        //if (option_flicker){
			        //	shipshield_t=shipshield_flicker_t;
			        //
			        mines.removeValue(mine,true);
			}  
		 }
	}
	
	void check_for_ship_mine_collisions(){
		
		for (Mine mine: mines){
		   if(mine.rect.y<(theShip.y+theShip.height) && shieldsRemaining<1) {
				//minecount-=1;
		     	spawn_explosion(mine.rect.x,mine.rect.y);
		        Sounds.mineHitUs.play(Options.SFXVolume*0.8f);
		        Sounds.mineSplode.play(Options.SFXVolume);
		        mines.removeValue(mine,true);
		        initiate_failure();
			}  
		 }
	}
	
	void check_for_shot_mine_collisions(){
		for (Shot shot: shots){
			if ((shot.rect.y-2)>shot.targetMine.rect.y && !shot.doomedToMiss){
				if (shot.type.equals("capture")){
					Sounds.capture.play(Options.SFXVolume*0.8f);
					shot.targetMine.beingDetained=true;
					exit_stage_whatever(shot.targetMine);
					shots.removeValue(shot, true);
				}
				if (shot.type.equals("destroy")){
					Sounds.mineSplode.play(Options.SFXVolume*0.8f);
					spawn_explosion(shot.targetMine.rect.x+shot.targetMine.rect.width/2, shot.targetMine.rect.y+shot.targetMine.rect.height/2);
					shot.targetMine.actuallyExists=false;
					mines.removeValue(shot.targetMine, true);
					shots.removeValue(shot, true);
				}
			}
		}
	}
	
	// ===Turret functions===
	
	void cycle_through_turrets(){
		boolean gotTurret=false;
		
		for (Turret turret: turrets){
			if (!gotTurret){
				if(!turret.targeted){
					currentlyActiveTurret = turret;
					gotTurret=true;
				}
			}
		}
		if (gotTurret==false){
			currentlyActiveTurret = null;
		}
	}
	
	void set_up_firing_times(){
		float firingTime=totalTime+0.05f;
		for (Turret turret: turrets){
			if (turret.targeted && turret.targetMine!=null){
				firingTime+=0.1f;
				turret.firingTime=firingTime;
			}
		}
		volleyEndingTime=firingTime+0.15f;
	}
	
	// ===Drawing Functions===
	
	void draw_targeting(){
		batch.begin();
		if (currentlyActiveTurret!=null){
			batch.draw(currentlyActiveTurret.selectedT, currentlyActiveTurret.rect.x, currentlyActiveTurret.rect.y);
			batch.draw(currentlyActiveTurret.targetT, tp_x-30, tp_y-30);
	    	draw_orange_dotted_line(currentlyActiveTurret.rect.x+currentlyActiveTurret.rect.width/2, currentlyActiveTurret.rect.y+currentlyActiveTurret.rect.height*3/4,tp_x,tp_y,10);
		}
		
		for (Turret turret: turrets){
			if (turret.targeted && turret.targetMine!=null){
				if (!turret.targetMine.beingDetained && turret.targetMine.actuallyExists){
					batch.draw(turret.targetT, turret.targetMine.rect.x+turret.targetingXOffset, turret.targetMine.rect.y+turret.targetingYOffset);
					draw_orange_dotted_line(turret.rect.x+turret.rect.width/2f, turret.rect.y+turret.rect.height*3f/4f, turret.targetMine.rect.x+turret.targetMine.rect.width/2+turret.targetingXOffset, turret.targetMine.rect.y+turret.targetMine.rect.height/2-turret.targetingYOffset, 10);
				}
			}
		}
		
		batch.draw(Textures.statusBar, 0, 400);
		batch.end();
	}
	
	void draw_miss_statements(){
		batch.begin();
		for (Shot shot: shots){
			if (shot.rect.y>shot.targetMine.rect.y){
				if (shot.doomedToMiss){
					batch.draw(Textures.missStatement, shot.rect.x-10, shot.rect.y-20);
				}
			}
		}
		batch.end();
	}
	
	void draw_orange_dotted_line(float start_x, float start_y, float finish_x, float finish_y, int number_of_divs){
		   for (int q=1; q<number_of_divs; q++){
			   float centre_x=start_x+((float)q/(float)number_of_divs)*(finish_x-start_x);
			   float centre_y=start_y+((float)q/(float)number_of_divs)*(finish_y-start_y);
			   batch.draw(Textures.Targets.lineDot, centre_x-1, centre_y-1);
		   }
	}
	
	// ===Spawning Functions===
	
	void spawnMine(int xposn) {
		   
		   Mine mine = new Mine(xposn);
		   mines.add(mine);
		         
	}
	
	// ===Miscellaneous Useful Functions===
	
	boolean any_targetable_mines(){
		   for (Mine mine:mines){
			   if (mine.rect.overlaps(screenProper)){
				   return true;
			   }
		   }
		   return false;
	}
	
}
