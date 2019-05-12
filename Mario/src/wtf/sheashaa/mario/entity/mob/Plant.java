package wtf.sheashaa.mario.entity.mob;

import java.awt.Graphics;

import wtf.sheashaa.mario.Game;
import wtf.sheashaa.mario.Handler;
import wtf.sheashaa.mario.Id;
import wtf.sheashaa.mario.entity.Entity;

public class Plant extends Entity {

	private int wait;
	private int pixelsTravelled;
	
	private boolean moving;
	private boolean insidePipe;
	
	public Plant(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
		frame = 0;
		moving = false;
		insidePipe = true;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.plant[frame].getBufferedImage(), x, y, width, height, null);
	}

	@Override
	public void tick() {
		y += velY;
		if (!moving) wait++;
		if (wait >= 180) {
			if (insidePipe) insidePipe = false;
			else insidePipe = true;
			
			moving = true;
			wait = 0;
		}
		
		if (moving) {
			if (insidePipe) setVelY(-3);
			else setVelY(3);
			pixelsTravelled += velY;
			if (pixelsTravelled >= height || pixelsTravelled <= -height) {
				pixelsTravelled = 0;
				moving = false;
				setVelY(0);
			}
		}
		
		frameDelay++;
		if (frameDelay >= 10) {
			frame ^= 1;
			frameDelay = 0;
		}
	}

}
