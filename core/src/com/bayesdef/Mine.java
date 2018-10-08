package com.bayesdef;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Mine {
	Rectangle rect;
   
   String minetype="regular";
   
   boolean captureProof=false; //If a captureshot hits it, will it be captured?
   boolean destroyProof=false; //If a destroyshot hits it, will it be destroyed?
   
   boolean actuallyExists =true;
   boolean beingDetained=false;
   
   float horzVel=0;
   float vertVel=0;
   
   //EnemyShip target_enemy_ship;
   
   public Mine(int xposn){
	   
	   rect = new Rectangle();
	   rect.width = 41;
	   rect.height = 41;
	   rect.x = (xposn * 40f+160f)-20f;
	   rect.y=440;
	   
	   vertVel = -125;
	   
   }
   
   public void update_posn(float delta){
	   
	   rect.x += horzVel*delta;
	   rect.y += vertVel*delta;
	   
   }
}
