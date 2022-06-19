package com.raffa064.fb.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

//classe do passaro, aqui temos quase tudo sobre o passaro, exeto as colisoes

public class Bird {
    public Animation<Texture> anim; //animaçao do passaro
	public Vector2 pos, size, vel; //posicao, tamanho e velocidade do passaro
	private float angle; //angulo do passaro

	public Bird(Animation anim, Vector2 pos, Vector2 size, Vector2 vel) {
		this.anim = anim;
		this.pos = pos.sub(size.x/2, size.y/2);
		this.size = size;
		this.vel = vel;
	}
	
	//metodo q denha e atualiza o passaro
	public void draw(SpriteBatch batch, float speed, float time, float delta) {
		//atualizacao e correcao do angulo
		vel.x = speed;
		angle = vel.angle();
		vel.x = 0;
		if (angle > 40 && angle < 320) {
			if (vel.y > 0) {
				angle = 40;
			} else angle = 320;
		}
		
		//desenhar passaro:
		batch.draw(
			new Sprite(anim.getKeyFrame(time, true)), //textura do passaro (para a frame atual com base no time(tempo))
			pos.x, //posicao x do passaro 
			pos.y, //posicao y do passaro
			size.x/2, //centro x da rotacao do passaro
			size.y/2, //centro y da rotacao do passaro
			size.x, //tamanho x do passaro
			size.y, //tamanho y do passaro
			1, //scala x (multiplica o tamanho horizontal do passaro)
			1, //scala y (multiplica o tamanho vertical do passaro)
			angle //angulo do passaro
		);
		vel.y -= 40 * delta; //gravidade (valor padrao e 40)
		if (Gdx.input.justTouched()) { //quando o usuario clica na tela
			vel.y = 15; //pular (valor padrao e 15)
		}
		pos.add(vel); //adicionar velocidade ao passaro
	}
}
