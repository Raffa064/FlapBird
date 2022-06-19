package com.raffa064.fb.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.List;
import java.util.ArrayList;
import com.raffa064.fb.MyGdxGame;

public class GameScreen extends ScreenAdapter {
	//constantes de largura e altura da tela em pixels
	private static final float W = Gdx.graphics.getWidth(); //width
	private static final float H = Gdx.graphics.getHeight(); //height
	
	private SpriteBatch batch; //o batch e usado para rederizar texturas (rederizar e o mesmo q desenhar)
	private Texture backgroundTexture; //textura/imagem do plano de fundo (aquelas montanhas com predios)
	private Texture pipeTexture; //textura/imagem dos canos
	private Texture[] birdAnimationFrames; //texturas/imagems do passaro (ele e feito de 3 imagem, asa pra cima, asa no meio, e asa pra baixo)
	private Animation<Texture> birdAnimation; //animacao do passaro, responsavel por mostrar a textura certa na hora certa, usando o valor da variavel time
	private float time; //temporizador da animao do passaro
	private float speed = 9; //velocidade dos canos
	private Bird bird; //passaro
	private List<Pipes> pipes = new ArrayList<>(); //canos
	private MyGdxGame game; //instancia da classe MygdxGame, usado para poder abrir outras telas

	public GameScreen(MyGdxGame game) {
		this.game = game;
	}
	
  	@Override
	public void show() {
		//inicializacao do spritebatch
        batch = new SpriteBatch();
		
		//inicializacao de todas as texturas nessesarias
		backgroundTexture = new Texture("background-day.png");
		pipeTexture = new Texture("pipe-green.png");
		birdAnimationFrames = new Texture[3];
		birdAnimationFrames[0] = new Texture("yellowbird-downflap.png");
		birdAnimationFrames[1] = new Texture("yellowbird-midflap.png");
		birdAnimationFrames[2] = new Texture("yellowbird-upflap.png");
		birdAnimation = new Animation<Texture>(.1f, birdAnimationFrames);
		
		//inicializaçao do passaro
		bird = new Bird(
			birdAnimation, 
			new Vector2(W/2, H/2),
			new Vector2((W / 100 * 5) * 1.41666667f, (W / 100 * 5)),
			new Vector2()
		);
		
		//inicializacao dos canos
		for (int i = 0; i < 10; i++) {
			float width = (W / 100 * 5) * 1.41666667f;
			float height = width * 6.15384615f;
			float[] array = {100, -100, 200, -200};
			Pipes p = new Pipes(pipeTexture, new Vector2(W+(i*(W / 100 * 25)), H/2+array[(int)(Math.random()*4)]), new Vector2(width, height), (W / 100 * 5)*4);
			pipes.add(p);
		}
	}

	@Override
	public void render(float delta) {
		//verificar se o passsaro bateu no chao ou no teto, e caso esteja, abrir a tela de game over
		if (bird.pos.y < 0 || bird.pos.y + bird.size.y > H) {
			game.setScreen(new GameOverScreen(game));
		}
		
		//inicializar o batch
		batch.begin();
		
		//desenhar o plano de fundo (tem q desenhar ele 3 veses, pq a imagem e pequena demais para preencher a tela em modo paisagem)
		batch.draw(backgroundTexture, 0, 0, W/3, H);
		batch.draw(backgroundTexture, W/3, 0, W/3, H);
		batch.draw(backgroundTexture, W/3*2, 0, W/3, H);
		
		//atualizacao e remocao dos canos
		List<Pipes> pipesToRemove = new ArrayList<>();
		for (Pipes p : pipes) {
			//desenhar e atualizar os canos:
			p.draw(batch, speed);
			
			//p.pos.set(p.pos.x, bird.pos.y);
			
			//adicionar o cano na lista de remocao quando ele sair da tela
			if (p.pos.x+p.size.x < 0) {
				pipesToRemove.add(p);
			}
			
			//se algum cano estiver encostando no passaro, abra a tela de game over
			if (p.overlaps(bird)) {
				game.setScreen(new GameOverScreen(game));
			}
		}
		for (Pipes p : pipesToRemove) {
			//remover o cano
			pipes.remove(p);
			//sempre q remover um cano, nos temos q adicionar outro ao fim de todos:
			float width = (W / 100 * 5) * 1.41666667f;
			float height = width * 6.15384615f;
			float[] array = {100, -100, 200, -200};
			Pipes p2 = new Pipes(pipeTexture, new Vector2(pipes.get(pipes.size()-1).pos.x+(W / 100 * 25), H/2+array[(int)(Math.random()*4)]), new Vector2(width, height), (W / 100 * 5)*4);
			pipes.add(p2);
		}
		
		//desenhar e atualizar o passaro
		bird.draw(batch, speed, time, delta);
		
		//finalizar o batch
		batch.end();
		
		//atualizar o timer da animacao
		time += delta;
	}

	@Override
	public void dispose() {
		//Nesse metodo eu chamo todos os disposes, o q 
		//e muuuuuuuito importante, pq se n fazer isso,
		//as textura vao ficar presas na memoria ram
		//mesmo sem ser usadas e alguma hora isso vai crashar o app
		backgroundTexture.dispose();
		pipeTexture.dispose();
		for (int i = 0; i < birdAnimationFrames.length; i++) {
			birdAnimationFrames[i].dispose();
		}
		batch.dispose();
	}

}
