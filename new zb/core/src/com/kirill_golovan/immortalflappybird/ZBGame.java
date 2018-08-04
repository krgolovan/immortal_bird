package com.kirill_golovan.immortalflappybird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;


public class ZBGame extends Game {

	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new SplashScreen(this));
	}



	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

}
