package com.bayesdef;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Textures {
	public static Texture badlog;
	
	public static Texture letterboxPoncho;
	
	public static Texture prettyBG;
	public static Texture theShip;
	public static Texture statusBar;
	
	public static class Shields{
		public static Texture one;
		public static Texture two;
		public static Texture three;
		public static Texture four;
		public static Texture five;
	}
	
	public static class Turrets{
		public static class Triangle{
			public static Texture normal;
			public static Texture firing;
			public static Texture selected;
			public static Texture silhouette;
			public static Texture emptytop;
		}
		
	}
	
	public static class Targets{
		public static Texture triangle;
		public static Texture lineDot;
		
	}
	
	public static class Shots{
		public static Texture capture;
		public static Texture destroy;
	}
	
	public static Texture detainingCircle;
	public static Texture missStatement;
	
	public static Texture shield1;
	public static Texture shield2;
	public static Texture shield3;
	public static Texture shield4;
	public static Texture shield5;
	
	public static Texture explosion;
	public static Texture bigExplosion;
	
	public static Texture mine;
	
	public static void load () {
		
		badlog = new Texture("badlogic.jpg");
		
		letterboxPoncho = new Texture("textures/letterboxPoncho.png");
		
		prettyBG=new Texture("textures/prettyBG.png");
		theShip=new Texture("textures/theShip.png");
		statusBar=new Texture("textures/statusBar.png");
		
		Shields.one=new Texture("textures/shields/shield1.png");
		Shields.two=new Texture("textures/shields/shield2.png");
		Shields.three=new Texture("textures/shields/shield3.png");
		Shields.four=new Texture("textures/shields/shield4.png");
		Shields.five=new Texture("textures/shields/shield5.png");
		
		Turrets.Triangle.normal=new Texture("textures/turrets/turret_triangle.png");
		Turrets.Triangle.firing=new Texture("textures/turrets/turret_triangle_firing.png");
		Turrets.Triangle.selected=new Texture("textures/turrets/turret_triangle_selected.png");
		Turrets.Triangle.silhouette=new Texture("textures/turrets/turret_triangle_silhouette.png");
		Turrets.Triangle.emptytop=new Texture("textures/turrets/turret_triangle_emptytop.png");

		
		Targets.triangle = new Texture("textures/targets/target_triangle.png");
		
		Targets.lineDot = new Texture("textures/targets/line_dot.png");
		
		Shots.capture = new Texture("textures/shots/shot_capture.png");
		Shots.destroy = new Texture("textures/shots/shot_destroy.png");
		
		detainingCircle = new Texture("textures/detaining_circle.png");
		missStatement = new Texture("textures/shots/shot_miss.png");

		
		explosion = new Texture("textures/explosions/explosion.png");
		bigExplosion = new Texture("textures/explosions/big_explosion.png");
		
		mine = new Texture("textures/mines/mine.png");
		
	}
}
