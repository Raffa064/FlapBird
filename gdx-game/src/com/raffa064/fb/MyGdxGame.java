package com.raffa064.fb;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.raffa064.fb.screens.GameScreen;

/*
	Esta classe não tem muita coisa, ela é usada apenas como base para as telas do jogo
*/

public class MyGdxGame extends Game {
	
	private AndroidOperations aOperations; //ignore isso, nesse projeto isso é inutil...
	
	public MyGdxGame(AndroidOperations androidOperations) {
		aOperations = androidOperations;
	}
	
	@Override
	public void create() {
		setScreen(new GameScreen(this)); //inicia a tela do jogo
	}

	@Override
	public void render() {        
		//limpar a tela da frame anterior:
	    Gdx.gl.glClearColor(1, 1, 1, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//desenhar a no frame da tela:
		super.render();
	}


}
