package wtf.sheashaa.mario.entity.powerup;

import java.awt.Graphics;

import wtf.sheashaa.mario.Game;
import wtf.sheashaa.mario.Handler;
import wtf.sheashaa.mario.Id;
import wtf.sheashaa.mario.entity.Entity;

public class Flower extends Entity {

	public Flower(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.flower[frame].getBufferedImage(), x, y, width, height, null);
	}

	@Override
	public void tick() {
		frameDelay++;
		if (frameDelay >= 10) {
			frame++;
			if (frame > 3) frame = 0;
			frameDelay = 0;
		}
	}
	

}
