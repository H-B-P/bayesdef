package com.bayesdef;

/*
 * ~SUMMARY~
 * 
 * This is the actual game.
 * It's pretty empty: all the fun happens in the screens, which we hand off to as soon as possible.
 * 
 */

import com.badlogic.gdx.Game;

public class BayesDef extends Game {
	
	
	public void create() {
		
		Textures.load();
		Sounds.load();
		
		Options.load();
		
		this.setScreen(new ProbScreen(this)); //Hand off to title screen.
		
	}

	public void render() {
		super.render(); // I deleted this once and I deeply regretted it.
	}

}