package wtf.sheashaa.mario.entity;

import java.awt.Graphics;

import wtf.sheashaa.mario.Game;
import wtf.sheashaa.mario.Handler;
import wtf.sheashaa.mario.Id;
import wtf.sheashaa.mario.tile.Tile;

public class Fireball extends Entity {

	public Fireball(int x, int y, int width, int height, boolean solid, Id id, Handler handler, int facing) {
		super(x, y, width, height, solid, id, handler);
		
		switch (facing) {
		case 0:
			setVelX(-8);
			break;
		case 1:
			setVelX(8);
			break;
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.fire.getBufferedImage(), x, y, width, height, null);
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		for (int i = 0; i < handler.tile.size(); i++) {
			Tile t = handler.tile.get(i);
			if (t.isSolid()) {
				if (getBoundsLeft().intersects(t.getBounds()) || getBoundsRight().intersects(t.getBounds())) {
					die();
				}
				
				if (getBoundsBottom().intersects(t.getBounds())) {
					jumping = true;
					falling = false;
					gravity = 4.0;
				} else if (!falling && !jumping) {
					falling = true;
					gravity = 0.1;
				}
			}
		}
		
		for (int i = 0; i < handler.entity.size(); i++) {
			Entity e = handler.entity.get(i);
			
			if (e.getId() == Id.goomba || e.getId() == Id.plant || e.getId() == Id.koopa) {
				if (getBounds().intersects(e.getBounds())) {
					die();
					e.die();
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
	}
	

}
