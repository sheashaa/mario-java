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
import wtf.sheashaa.mario.entity.Particle;
import wtf.sheashaa.mario.states.BossState;
import wtf.sheashaa.mario.states.KoopaState;
import wtf.sheashaa.mario.states.PlayerState;
import wtf.sheashaa.mario.tile.Tile;
import wtf.sheashaa.mario.tile.Trail;

public class Player extends Entity {
	
//	private boolean animate = false;
	
	private int pixelsTravelled = 0;
	private int invinciblityTime = 0;
	private int particleDelay = 0;
	private int restoreTime = 0;
	
	private Random random = new Random();
	
	private final int[] ANIM_SEQ = {3, 4, 5};
	private int ii = 0;
	
	private boolean invincible = false;
	private boolean restoring;
	
	public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
//		setVelX(5);
		
		state = PlayerState.SMALL;
	}

	@Override
	public void render(Graphics g) {
//		g.setColor(Color.BLACK);
//		g.fillRect(x, y, width, height);
		if (state == PlayerState.FIRE) {
			if (facing == 0) {
				BufferedImage image = Game.bowser[frame].getBufferedImage();
				AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
				tx.translate(-image.getWidth(), 0);
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				image = op.filter(image, null);
				g.drawImage(image, x, y, width, height, null);
			} else if (facing == 1) {
				g.drawImage(Game.bowser[frame].getBufferedImage(), x, y, width, height, null);
			}
		} else {
			if (facing == 0) {
				BufferedImage image = Game.player[frame].getBufferedImage();
				AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
				tx.translate(-image.getWidth(), 0);
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				image = op.filter(image, null);
				g.drawImage(image, x, y, width, height, null);
			} else if (facing == 1) {
				g.drawImage(Game.player[frame].getBufferedImage(), x, y, width, height, null);
			}
		}
		if (Game.BOX) g.drawRect(x, y, width, height);
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		if (invincible) {
			if (facing == 0) {
				BufferedImage image = Game.player[frame].getBufferedImage();
				AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
				tx.translate(-image.getWidth(), 0);
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				image = op.filter(image, null);
				handler.addTile(new Trail(x, y, width, height, false, Id.trail, handler, image));
			}
			else if (facing == 1) {
				handler.addTile(new Trail(x, y, width, height, false, Id.trail, handler, Game.player[frame].getBufferedImage()));
			}
			
			particleDelay++;
			if (particleDelay >= 10) {
				handler.addEntity(new Particle(x + random.nextInt(width), y + random.nextInt(height), 15, 15, false, Id.particle, handler));
				
				particleDelay = 0;
			}
			
			invinciblityTime++;
			if (invinciblityTime >= 600) {
				invincible = false;
				invinciblityTime = 0;
			}
			
			if (velX == 5) setVelX(8);
			else if (velX == -5) setVelY(-8);
		} else {
			if (velX == 8) setVelX(5);
			else if (velX == -8) setVelY(-5);
		}
		
		if (restoring) {
			restoreTime++;
			if (restoreTime >= 90) {
				restoring = false;
				restoreTime = 0;
			}
		}

//		if (x <= 0) x = 0;
//		if (y <= 0) y = 0;
//		if (x + width >= Game.WIDTH * Game.SCALE) x = Game.WIDTH * Game.SCALE - width;
//		if (y + height >= Game.HEIGHT * Game.SCALE) y = Game.HEIGHT * Game.SCALE - height;
//		if (velX != 0) animate = true;
//		else animate = false;
		if (x >= handler.levelWidth * 2 || x <= -100 || y >= handler.levelHeight * 2 || y <= -100) die();
		for (int i = 0; i < handler.tile.size(); i++) {
			Tile t = handler.tile.get(i);
			if (t.isSolid() && !goingDownPipe) {
				if (getBounds().intersects(t.getBounds())) {
					if (t.getId() == Id.flag) {
						Game.switchLevel();
					}
				}
				
				if (getBoundsTop().intersects(t.getBounds())) {
					setVelY(0);
//					y = t.getY() + t.height;
					if (jumping && !goingDownPipe) {
						jumping = false;
						gravity = 0.8;
						falling = true;
					}
					if (t.getId() == Id.powerUp) {
						if (getBounds().intersects(t.getBounds())) {
							t.activated = true;
						}
					}
				}

				if (getBoundsBottom().intersects(t.getBounds())) {
					setVelY(0);
//					y = t.getY() - t.height;
					if (falling) falling = false;
				} else {
					if (!falling && !jumping) {
						gravity = 0.8;
						falling = true;
					}
				}

				if (getBoundsLeft().intersects(t.getBounds())) {
					setVelX(0);
					x = t.getX() + t.width;
				}

				if (getBoundsRight().intersects(t.getBounds())) {
					setVelX(0);
					x = t.getX() - t.width;
				}
			}
		}
		
		for (int i = 0; i < handler.entity.size(); i++) {
			Entity en = handler.entity.get(i);
			
			if (en.getId() == Id.mushroom) {
				if (getBounds().intersects(en.getBounds())) {
					switch(en.getType()) {
					case 0:
						if (state == PlayerState.SMALL) {
							int tpX = getX();
							int tpY = getY();
							width *= 2;
							height *= 2;
							setX(tpX - width);
							setY(tpY - height);
							state = PlayerState.BIG;
							en.die();
						}
						break;
					case 1:
						Game.lives++;
						en.die();
						break;
					}
				}
			} else if (en.getId() == Id.goomba || en.getId() == Id.towerBoss) {
				if (invincible && getBounds().intersects(en.getBounds())) {
					en.die();
				} else {
					if (getBoundsBottom().intersects(en.getBoundsTop())) {
						if (en.getId() != Id.towerBoss) {
							en.die();
							jumping = true;
							falling = false;
							gravity = 5.0;
						}
						else if (en.attackable) {
							en.hp--;
							en.falling = true;
							en.gravity = 3.0;
							en.bossState = BossState.RECOVERING;
							en.attackable = false;
							en.phaseTime = 0;

							jumping = true;
							falling = false;
							gravity = 8.0;
						}
					}
					else if (getBounds().intersects(en.getBounds())) {
						takeDamage();
					}
				}
				
			} else if (en.getId() == Id.coin) {
				if (getBounds().intersects(en.getBounds())) {
					Game.coins++;
					en.die();
				}
			} else if (en.getId() == Id.plant)  {
				if (getBounds().intersects(en.getBounds())) takeDamage();
			}
			else if (en.getId() == Id.koopa) {
				if (invincible && getBounds().intersects(en.getBounds())) {
					en.die();
				} else {
					if (en.koopaState == KoopaState.WALKING) {
					if (getBoundsBottom().intersects(en.getBoundsTop())) {
						en.koopaState = KoopaState.SHELL;
						jumping = true;
						falling = false;
						gravity = 5.0;
					} else if (getBounds().intersects(en.getBounds())) takeDamage();
				} else if (en.koopaState == KoopaState.SHELL) {
					if (getBoundsBottom().intersects(en.getBoundsTop())) {
						en.koopaState = KoopaState.SPINNING;
						
						int dir = random.nextInt(2);
						switch (dir) {
						case 0:
							en.setVelX(-10);
							facing = 0;
							break;
						case 1:
							en.setVelX(10);
							facing = 1;
							break;
						}
						
						jumping = true;
						falling = false;
						gravity = 5.0;
					}
					if (getBoundsLeft().intersects(en.getBoundsRight())) {
						en.setVelX(-10);
						en.koopaState = KoopaState.SPINNING;
					}
					if (getBoundsRight().intersects(en.getBoundsLeft())) {
						en.setVelX(10);
						en.koopaState = KoopaState.SPINNING;
					}
				} else if (en.koopaState == KoopaState.SPINNING) {
					if (en.koopaState == KoopaState.WALKING) {
						if (getBoundsBottom().intersects(en.getBoundsTop())) {
							en.koopaState = KoopaState.SHELL;
							jumping = true;
							falling = false;
							gravity = 5.0;
						} else if (getBounds().intersects(en.getBounds())) takeDamage();
					}
				}
				}
				
			} else if (en.getId() == Id.star && getBounds().intersects(en.getBounds())) {
				invincible = true;
				en.die();
			} else if (en.getId() == Id.flower) {
				if (getBounds().intersects(en.getBounds())) {
					state = PlayerState.FIRE;
					en.die();
				}
			}
		}
		
		if (jumping && !goingDownPipe) {
			gravity -= 0.15;
			setVelY((int) -gravity);
			if (gravity <= 0.6) {
				jumping = false;
				falling = true;
			}
		}
		
		if (falling && !goingDownPipe) {
			gravity += 0.15;
			setVelY((int) gravity);
		}
		
		if (velX == 0) {
			frame = 0;
		} else {
			if (!jumping) {
				frameDelay++;
				if (frameDelay >= 10) {
					frame = ANIM_SEQ[ii++];
					if (ii >= ANIM_SEQ.length) ii = 0;
					frameDelay = 0;
				}
			} else {
				if (jumping || falling) frame = 3;
			}
		}
		
		if (goingDownPipe) {
			for (int i = 0; i < Game.handler.tile.size(); i++) {
				Tile t = Game.handler.tile.get(i);
				if (t.getId() == Id.pipe) {
					if (getBounds().intersects(t.getBounds())) {
						x = t.getX();
						switch(t.facing) {
						case 0: // up
							setVelY(-5);
							setVelX(0);
							pixelsTravelled -= velY;
							if (pixelsTravelled >= t.height) {
								goingDownPipe = false;
								x = t.getX();
								y = t.getY() - height;
								setVelX(0); setVelY(0);
								pixelsTravelled = 0;
							}
							break;
						case 2: // down
							setVelY(5);
							setVelX(0);
							pixelsTravelled += velY;
							if (pixelsTravelled >= t.height) {
								goingDownPipe = false;
								x = t.getX();
								y = t.getY() + t.height + height;
								setVelX(0); setVelY(0);
								pixelsTravelled = 0;
							}
							break;
						}
					}
				}
			}
		}
	}

	public void takeDamage() {
		if (restoring) return;
		if (state == PlayerState.SMALL) {
			die();
			restoring = true;
			restoreTime = 0;
			return;
		} else if (state == PlayerState.BIG) {
			state = PlayerState.SMALL;
			width /= 2;
			height /= 2;
			x += width;
			y += height;
			restoring = true;
			restoreTime = 0;
			return;
		} else if (state == PlayerState.FIRE) {
			state = PlayerState.SMALL;
			restoring = true;
			restoreTime = 0;
			return;
		}
	}
	
}
