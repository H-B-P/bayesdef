package com.bayesdef;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;

public class Turret {
	   Rectangle rect;
	   
	   Texture normalT;
	   Texture firingT;
	   Texture selectedT;
	   Texture silhouetteT;
	   Texture emptytopT;
	   
	   Texture currentT;
	   
	   Texture targetT;
	   Mine targetMine;
	   
	   String ident;
	   
	   String descLineOne;
	   String descLineTwo;
	   String descLineThree;
	   String descLineFour;
	   
	   boolean does_it_work;
	   boolean targeted;
	   
	   float targetingXOffset=0;
	   float targetingYOffset=0;
	   
	   float firingDelta = 0;
	   float firingTime = 0;
	   
	   public Turret(){
		   ident="triangle";
		   
		   rect=new Rectangle();
		   rect.width=40;
		   rect.height=40;
		   
		   does_it_work=true;
		   targeted=false;
		   
		   assign_textures(ident);
	   }
	   
	   public Turret(String id){
		   this();
		   ident=id;
		   
		   assign_textures(ident);
	   }
	   
	   void assign_textures(String id){
		   if (id=="triangle"){
			   normalT = Textures.Turrets.Triangle.normal;
			   firingT = Textures.Turrets.Triangle.firing;
			   selectedT = Textures.Turrets.Triangle.selected;
			   silhouetteT = Textures.Turrets.Triangle.silhouette;
			   emptytopT = Textures.Turrets.Triangle.emptytop;
			   
			   targetT = Textures.Targets.triangle;
		   }
		   currentT = normalT;
	   }
	   
	   String determine_output(){
		   return "destroy";
	   }
	   
}
