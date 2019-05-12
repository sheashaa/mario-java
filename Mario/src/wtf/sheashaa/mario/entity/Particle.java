package wtf.sheashaa.mario.entity;

import java.awt.Graphics;

import wtf.sheashaa.mario.Game;
import wtf.sheashaa.mario.Handler;
import wtf.sheashaa.mario.Id;

public class Particle extends Entity {

	private boolean fading = false;
	private int time = 0;
	
	public Particle(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
	}

	@Override
	public void render(Graphics g) {
		if (!fading) {
			g.drawImage(Game.star[frame].getBufferedImage(), x, y, width, height, null);
		} else {
			g.drawImage(Game.star[Game.star.length - frame].getBufferedImage(), x, y, width, height, null);
		}
	}

	@Override
	public void tick() {
		frameDelay++;
		time++;
		if (frameDelay >= 5) {
			if (frame < 4) frame++;
			if (frame >= 4) frame = 0;
			frameDelay = 0;
		}
		
		if (frame >= Game.star.length) fading = true;

		if (time > 50) die();
		
	}

	
}
