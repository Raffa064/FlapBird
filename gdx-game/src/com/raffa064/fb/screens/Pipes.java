package com.raffa064.fb.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//classe dos canos, cada objeto dessa classe equivale a 2 canos, um q fica em cima e outro q fica em baixo.

public class Pipes {
    private Texture texture; //textura dos canos
	public Vector2 pos, size; //posicao e tamanho (leia a observacao)
	private float distance; //distancia entre o cano de cima e o cano de baixo
	
	//OBS: a posicao e exatamente o centro entre os dois canos, e o tanhanho e dos canos em si

	public Pipes(Texture texture, Vector2 pos, Vector2 size, float distance) {
		this.texture = texture;
		this.pos = pos;
		this.size = size;
		this.distance = distance;
	}
	
	public void draw(SpriteBatch batch, float vel) {
		//desenhar o cano de cima (presisa de muitos parametros pq tem q rotacinar em 180 graos a textura), cada parametro foi explicado no metodo draw da classe Bird
		batch.draw(
			new Sprite(texture),
			pos.x,
			pos.y+distance/2,
			size.x/2,
			size.y/2,
			size.x,
			size.y,
			1,
			1,
			180
		);
		
		//desenhar o cano de baixo
		batch.draw(texture, pos.x, pos.y-distance/2-size.y, size.x, size.y); //os paramentro sao bem simples: textura, posicao x (horizontal), posicao y (vertical), tamanho x (largura), tamanho y (altura)
		
		//atualizar a posicao do cano (nesse jogo so os canos andam, o passaro apenas pula ksks)
		pos.x -= vel; 
	}
	
	//Metodo q verifica a colicao dos canos com o passsaro
	public boolean overlaps(Bird bird) {
		Rectangle birdRect = new Rectangle(bird.pos.x, bird.pos.y, bird.size.x, bird.size.y); //caixa de colicao do passsaro (caixa invisivel q fica em volta de algo para q seja possivel a deteccao de colisoes)
		Rectangle a = new Rectangle(pos.x, pos.y+distance/2, size.x, size.y); //caixa de colicao do cano de cima
		Rectangle b = new Rectangle(pos.x, pos.y-distance/2-size.y, size.x, size.y); //caixa de colicao do cano de baixo
		return a.overlaps(birdRect) || b.overlaps(birdRect); //verificar se o passaro ta encostando em algum dos canos
	}
}
