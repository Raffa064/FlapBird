package com.raffa064.fb.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.raffa064.fb.MyGdxGame;

//classe da tela de game over

public class GameOverScreen extends ScreenAdapter {
	//constantes de largura e altura da tela em pixels
	private static final float W = Gdx.graphics.getWidth(); //width
	private static final float H = Gdx.graphics.getHeight(); //height
	
	private SpriteBatch batch; //o batch e usado para rederizar texturas (rederizar e o mesmo q desenhar)
	private Texture backgroundTexture; //textura/imagem do plano de fundo (aquelas montanhas com predios)
	private Texture gameOver; //textura do game over (aquele texto q pelo menos eu odeio kkkkk)
	private Texture playBtn; //textura do bota de play
	private MyGdxGame game; //instancia da classe MygdxGame, usado para poder abrir outras telas

	public GameOverScreen(MyGdxGame game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		//inicializacao do spritebatch
		batch = new SpriteBatch();
		
		//inicializacao das texturas
		backgroundTexture = new Texture("background-day.png");
		gameOver = new Texture("gameover.png");
		playBtn = new Texture("playbtn.png");
	}

	@Override
	public void render(float delta) {
		//inicializar o batch
		batch.begin();
		
		//desenhar o plano de fundo (tem q desenhar ele 3 veses, pq a imagem e pequena demais para preencher a tela em modo paisagem)
		batch.draw(backgroundTexture, 0, 0, W/3, H);
		batch.draw(backgroundTexture, W/3, 0, W/3, H);
		batch.draw(backgroundTexture, W/3*2, 0, W/3, H);
		
		//desenhar game over
		float height = 42f/192f*W/3;
		batch.draw(gameOver, W/2-W/3/2, H-80-height, W/3, height);
		
		//desenhar botao de play
		height = 188f/300f*W/3.5f;
		batch.draw(playBtn, W/2-W/3.5f/2, H/2-height/2, W/3.5f, height);
		
		//verificar click do botao:
		if (Gdx.input.justTouched()) { //quando o dedo acabou de tocar a tela...
			if (Gdx.input.getX() > W/2-W/3.5f/2 && Gdx.input.getX() < (W/2-W/3.5f/2)+W/3.5f) { //verificar se o dedo esta dentro do botao na horizontal
				if (Gdx.input.getY() > H/2-height/2 && Gdx.input.getY() < (H/2-height/2)+height) { //verificar se o dedo esta dentro do botao na vertical
					//iniciar uma nova tela de jogo 
					game.setScreen(new GameScreen(game));
				}
			}
		}
		//finalizar o batch
		batch.end();
	}

	@Override
	public void dispose() {
		//Nesse metodo eu chamo todos os disposes, o q 
		//e muuuuuuuito importante, pq se n fazer isso,
		//as textura vao ficar presas na memoria ram
		//mesmo sem ser usadas e alguma hora isso vai crashar o app
		backgroundTexture.dispose();
		gameOver.dispose();
		playBtn.dispose();
		batch.dispose();
	}
}
