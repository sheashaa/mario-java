package wtf.sheashaa.mario.tile;

import java.awt.Graphics;

import wtf.sheashaa.mario.Game;
import wtf.sheashaa.mario.Handler;
import wtf.sheashaa.mario.Id;

public class Flag extends Tile {

	public Flag(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.flag[1].getBufferedImage(), x, y + 0 * 64, width, 64, null);
		g.drawImage(Game.flag[0].getBufferedImage(), x + 32, y + 1 * 64, width, 64, null);
		g.drawImage(Game.flag[2].getBufferedImage(), x, y + 1 * 64, width, 64, null);
		g.drawImage(Game.flag[2].getBufferedImage(), x, y + 2 * 64, width, 64, null);
		g.drawImage(Game.flag[2].getBufferedImage(), x, y + 3 * 64, width, 64, null);
		g.drawImage(Game.flag[2].getBufferedImage(), x, y + 4 * 64, width, 64, null);
		g.drawImage(Game.flag[2].getBufferedImage(), x, y + 5 * 64, width, 64, null);
		g.drawImage(Game.flag[2].getBufferedImage(), x, y + 6 * 64, width, 64, null);
		g.drawImage(Game.flag[3].getBufferedImage(), x, y + 7 * 64, width, 64, null);
	}

	@Override
	public void tick() {
		
	}
	

}
