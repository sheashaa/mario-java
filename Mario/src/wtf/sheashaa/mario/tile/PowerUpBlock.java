package wtf.sheashaa.mario.tile;

import java.awt.Graphics;

import wtf.sheashaa.mario.Game;
import wtf.sheashaa.mario.Handler;
import wtf.sheashaa.mario.Id;
import wtf.sheashaa.mario.entity.powerup.Flower;
import wtf.sheashaa.mario.entity.powerup.Mushroom;
import wtf.sheashaa.mario.gfx.Sprite;

public class PowerUpBlock extends Tile {

	private Sprite powerUp;
	private boolean popped = false;
	private int spriteY = getY();
	
	private int type;
	
	public PowerUpBlock(int x, int y, int width, int height, boolean solid, Id id, Handler handler, Sprite powerUp, int type) {
		super(x, y, width, height, solid, id, handler);
		this.powerUp = powerUp;
		this.type = type;
	}

	@Override
	public void render(Graphics g) {
		if (!popped) {
			if (type == 0) {
				g.drawImage(Game.mushroom.getBufferedImage(), x, spriteY, width, height, null);
			} else if (type == 1) {
				g.drawImage(Game.oneUpMushroom.getBufferedImage(), x, spriteY, width, height, null);
			} else if (type == 2) {
				g.drawImage(Game.flower[0].getBufferedImage(), x, spriteY, width, height, null);
			}
		}
		if (!activated) { 
			g.drawImage(Game.powerUp[frame].getBufferedImage(), x, y, width, height, null);
		}
		else g.drawImage(Game.powerUpUsed.getBufferedImage(), x, y, width, height, null);

	}

	@Override
	public void tick() {
		if (activated && !popped) {
			spriteY--;
			if (spriteY <= y - height) {
				if (powerUp == Game.mushroom || powerUp == Game.oneUpMushroom) handler.addEntity(new Mushroom(x, spriteY, width, height, true, Id.mushroom, handler, type));
				else if (powerUp == Game.flower[0]) handler.addEntity(new Flower(x, spriteY, width, height, true, Id.flower, handler));
				popped = true;
			}
		}
		else if (!activated) {
			frameDelay++;
			if (frameDelay >= 8) {
				frame++;
				if (frame >= 4) frame = 0;
				frameDelay = 0;
			}
		}
	}
	

}
