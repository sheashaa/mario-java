package wtf.sheashaa.mario.entity.powerup;

import java.awt.Graphics;
import java.util.Random;

import wtf.sheashaa.mario.Game;
import wtf.sheashaa.mario.Handler;
import wtf.sheashaa.mario.Id;
import wtf.sheashaa.mario.entity.Entity;
import wtf.sheashaa.mario.tile.Tile;

public class Star extends Entity {

	private Random random = new Random();
	
	public Star(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
		int dir = random.nextInt(2);
		switch (dir) {
		case 0:
			setVelX(-4);
			facing = 0;
			break;
		case 1:
			setVelX(4);
			facing = 1;
			break;
		}	
		falling = true;
		gravity = 0.15;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.star[frame].getBufferedImage(), x, y, width, height, null);
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		for (Tile t : Game.handler.tile) {
			if (t.isSolid()) {
				if (getBoundsBottom().intersects(t.getBounds())) {
					jumping = true;
					falling = false;
					gravity = 8.0; 
				}
				else if (getBoundsRight().intersects(t.getBounds())) {
					setVelX(-4);
				}
				else if (getBoundsLeft().intersects(t.getBounds())) {
					setVelX(4);
				}
			}
		}
		
		if (jumping) {
			gravity -= 0.15;
			setVelY((int) -gravity);
			if (gravity <= 0.6) {
				jumping = false;
				falling = true;
			}
		}
		
		if (falling) {
			gravity += 0.15;
			setVelY((int) gravity);
		}
		
		frameDelay++;
		if (frameDelay >= 10) {
			frame++;
			if (frame > 3) frame = 0;
			frameDelay = 0;
		}
	}

}
