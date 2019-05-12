package wtf.sheashaa.mario.entity;

import java.awt.Graphics;

import wtf.sheashaa.mario.Game;
import wtf.sheashaa.mario.Handler;
import wtf.sheashaa.mario.Id;
import wtf.sheashaa.mario.tile.Tile;

public class Coin extends Entity {

	public Coin(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.coin[frame].getBufferedImage(), x, y, width, height, null);
		if (Game.BOX) g.drawRect(x, y, width, height);
	}

	@Override
	public void tick() {
		frameDelay++;
		if (frameDelay >= 8) {
			frame++;
			if (frame >= 4) frame = 0;
			frameDelay = 0;
		}
	}

}
