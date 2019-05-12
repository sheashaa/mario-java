package wtf.sheashaa.mario.tile;

import java.awt.Color;
import java.awt.Graphics;

import wtf.sheashaa.mario.Game;
import wtf.sheashaa.mario.Handler;
import wtf.sheashaa.mario.Id;

public class Wall extends Tile {

	public Wall(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
	}

	@Override
	public void render(Graphics g) {
//		g.setColor(Color.GREEN);
//		g.fillRect(x,  y, width, height);
		g.drawImage(Game.grass.getBufferedImage(), x, y, width, height, null);

	}

	@Override
	public void tick() {
		
	}

}
