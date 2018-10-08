package com.bayesdef;

public class Options {
	public static float SFXVolume;
	public static float MusicVolume;
	
	public static boolean waitForFiringButton;
	
	public static void load () {
		SFXVolume=1f;
		MusicVolume=1f;
		
		waitForFiringButton=true;
	}
}
