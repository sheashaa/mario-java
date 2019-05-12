package wtf.sheashaa.mario.entity.powerup;

import java.awt.Graphics;
import java.util.Random;

import wtf.sheashaa.mario.Game;
import wtf.sheashaa.mario.Handler;
import wtf.sheashaa.mario.Id;
import wtf.sheashaa.mario.entity.Entity;
import wtf.sheashaa.mario.tile.Tile;

public class Mushroom extends Entity {

	private Random random = new Random();
	
	public Mushroom(int x, int y, int width, int height, boolean solid, Id id, Handler handler, int type) {
		super(x, y, width, height, solid, id, handler);
		this.type = type;
		int dir = random.nextInt(2);
		switch (dir) {
		case 0:
			setVelX(-2);
			break;
		case 1:
			setVelX(2);
			break;
		}
	}

	@Override
	public void render(Graphics g) {
		switch (type) {
		case 0:
			g.drawImage(Game.mushroom.getBufferedImage(), x, y, width, height, null);
			break;
		case 1:
			g.drawImage(Game.oneUpMushroom.getBufferedImage(), x, y, width, height, null);
			break;
		}
		if (Game.BOX) g.drawRect(x, y, width, height);
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;

		for (int i = 0; i < handler.tile.size(); i++) {
			Tile t = handler.tile.get(i);
			if (t.isSolid()) {
				if (getBoundsBottom().intersects(t.getBounds())) {
					setVelY(0);
					if (falling) falling = false;
				} else {
					if (!falling) {
						gravity = 0.8;
						falling = true;
					}
				}

				if (getBoundsLeft().intersects(t.getBounds())) {
					setVelX(2);
				}

				if (getBoundsRight().intersects(t.getBounds())) {
					setVelX(-2);
				}

			}
		}
		
		if (falling) {
			gravity += 0.1;
			setVelY((int) gravity);
		}
	}

}
