package com.bayesdef;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

public class Sounds {
	public static Sound mineSplode;
	public static Sound mineHitUs;
	public static Sound deShield;
	public static Sound capture;
	public static Sound fire;
	public static Sound laser;
	public static Sound shock;
	public static Sound mistakenShock;
	
	public static void load () {
		
		mineSplode = Gdx.audio.newSound(Gdx.files.internal("sfx_scronched/cannon.mp3"));
	    mineHitUs = Gdx.audio.newSound(Gdx.files.internal("sfx_scronched/bang.mp3"));
	    deShield = Gdx.audio.newSound(Gdx.files.internal("sfx_scronched/hurt.wav"));
	    capture=Gdx.audio.newSound(Gdx.files.internal("sfx_scronched/pick.mp3"));
	    fire=Gdx.audio.newSound(Gdx.files.internal("sfx_scronched/gunshot.mp3"));
	    laser=Gdx.audio.newSound(Gdx.files.internal("sfx_scronched/laser.wav"));
	    mistakenShock=Gdx.audio.newSound(Gdx.files.internal("sfx_scronched/wrong.wav"));
	    shock=Gdx.audio.newSound(Gdx.files.internal("sfx_scronched/shock.mp3"));
		
	}
}
