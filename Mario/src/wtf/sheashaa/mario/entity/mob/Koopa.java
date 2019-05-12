package wtf.sheashaa.mario.entity.mob;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Random;

import wtf.sheashaa.mario.Game;
import wtf.sheashaa.mario.Handler;
import wtf.sheashaa.mario.Id;
import wtf.sheashaa.mario.entity.Entity;
import wtf.sheashaa.mario.states.KoopaState;
import wtf.sheashaa.mario.tile.Tile;

public class Koopa extends Entity {

	private Random random = new Random();
	
	private int shellCount;
	
	public Koopa(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
		int dir = random.nextInt(2);
		switch (dir) {
		case 0:
			setVelX(-2);
			facing = 0;
			break;
		case 1:
			setVelX(2);
			facing = 1;
			break;
		}	
		
		koopaState = KoopaState.WALKING;
	}

	@Override
	public void render(Graphics g) {
		if (koopaState == KoopaState.WALKING) {
			if (facing == 0) {
				BufferedImage image = Game.koopa[frame].getBufferedImage();
				AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
				tx.translate(-image.getWidth(), 0);
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				image = op.filter(image, null);
				g.drawImage(image, x, y, width, height, null);
			} else if (facing == 1) {
				g.drawImage(Game.koopa[frame].getBufferedImage(), x, y, width, height, null);
			}
		} else {
			g.drawImage(Game.koopa[3].getBufferedImage(), x, y, width, height, null);
		}
		if (Game.BOX) g.drawRect(x, y, width, height);
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		if (koopaState == KoopaState.SHELL) {
			setVelX(0);
			shellCount++;
			if (shellCount >= 300) {
				koopaState = KoopaState.WALKING;
				shellCount = 0;
			}
		}
		
		if (koopaState == KoopaState.WALKING || koopaState == KoopaState.SPINNING) {
			shellCount = 0;
			if (velX == 0) {
				int dir = random.nextInt(2);
				switch (dir) {
				case 0:
					setVelX(-2);
					facing = 0;
					break;
				case 1:
					setVelX(2);
					facing = 1;
					break;
				}	
			}
		}
		
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
					if (koopaState == KoopaState.SPINNING) setVelX(10);
					else setVelX(2);
					facing = 1;
				}

				if (getBoundsRight().intersects(t.getBounds())) {
					if (koopaState == KoopaState.SPINNING) setVelX(-10);
					else setVelX(-2);
					facing = 0;
				}

			}
		}
		
//		for (int i = 0; i < handler.entity.size(); i++) {
//			Entity e = handler.entity.get(i);
//			if (koopaState == KoopaState.SPINNING && (e.getId() == Id.goomba || e.getId() == Id.koopa) && getBounds().intersects(e.getBounds())) e.die();
//		}
		
		if (falling) {
			gravity += 0.1;
			setVelY((int) gravity);
		}
		
		if (velX != 0) {
			frameDelay++;
			if (frameDelay >= 10) {
				frame++;
				if (frame > 2) frame = 0;
				frameDelay = 0;
			}
		}
	}
	
	

}
