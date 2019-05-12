package wtf.sheashaa.mario.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import wtf.sheashaa.mario.Game;
import wtf.sheashaa.mario.Id;
import wtf.sheashaa.mario.entity.Entity;
import wtf.sheashaa.mario.entity.Fireball;
import wtf.sheashaa.mario.states.PlayerState;
import wtf.sheashaa.mario.tile.Tile;

public class KeyInput implements KeyListener {

	private boolean fire;
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		for (Entity en : Game.handler.entity) {
			if (en.getId() == Id.player) {
				if (en.goingDownPipe) return;
				switch (key) {
				case KeyEvent.VK_W:
				case KeyEvent.VK_SPACE:
				case KeyEvent.VK_UP:
//					en.setVelY(-5);
					for (int j = 0; j < Game.handler.tile.size(); j++) {
						Tile t = Game.handler.tile.get(j);
						if (t.getId() == Id.pipe && t.facing == 0) {
							if (en.getBoundsTop().intersects(t.getBounds())) {
								if (!en.goingDownPipe) en.goingDownPipe = true;
							}
						} else if (t.isSolid()) {
							if (en.getBoundsBottom().intersects(t.getBounds())) {
								if (!en.jumping) {
									en.jumping = true;
									en.gravity = 10.0;
								}
							}
						}
					}
					break;
				case KeyEvent.VK_S:
					for (int j = 0; j < Game.handler.tile.size(); j++) {
						Tile t = Game.handler.tile.get(j);
						if (t.getId() == Id.pipe && t.facing == 2) {
							if (en.getBoundsBottom().intersects(t.getBounds())) {
								if (!en.goingDownPipe) en.goingDownPipe = true;
							}
						}
					}
					break;
				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					en.setVelX(-5);
					en.facing = 0;
					break;
				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					en.setVelX(5);
					en.facing = 1;
					break;
				case KeyEvent.VK_E:
					if (en.state == PlayerState.FIRE && !fire) {
						switch (en.facing) {
						case 0:
							Game.handler.addEntity(new Fireball(en.getX() - 24, en.getY() + 12, 24, 24, false, Id.fireball, Game.handler, en.facing));
							fire = true;
							break;
						case 1:
							Game.handler.addEntity(new Fireball(en.getX() - 24, en.getY() + 12, 24, 24, false, Id.fireball, Game.handler, en.facing));
							fire = true;
							break;
						}
					}
					break;
				}
			}	
		}
		if (key == KeyEvent.VK_B) Game.BOX = !Game.BOX;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for (Entity en : Game.handler.entity) {
			if (en.getId() == Id.player) {
				switch (key) {
				case KeyEvent.VK_W:
				case KeyEvent.VK_SPACE:
				case KeyEvent.VK_UP:
					en.setVelY(0);
					break;
//				case KeyEvent.VK_S:
//					en.setVelY(0);
//					break;
				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					en.setVelX(0);
					break;
				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					en.setVelX(0);
					break;
				case KeyEvent.VK_E:
					fire = false;
					break;
				}
			}	
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
