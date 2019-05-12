package wtf.sheashaa.mario;

import wtf.sheashaa.mario.entity.Entity;

public class Camera {

	public int x, y;
	
	public void tick(Entity player) {
//		setX(-player.getX() + Game.getFrameWidth() / 2);
//		setX(Math.min(0, -player.getX() + Game.getFrameWidth() / 2));
//		setY(-player.getY() + Game.getFrameHeight() / 2);
//		setY(Math.min(-player.getY() + Game.getFrameHeight() / 2, Game.handler.levelHeight));
		setX(constrain(-player.getX() + Game.getFrameWidth() / 2, -Game.handler.levelWidth + Game.getFrameWidth(), 0));
		setY(constrain(-player.getY() + Game.getFrameHeight() / 2, -Game.handler.levelHeight + Game.getFrameHeight(), 0));
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}	
	
	private int constrain(int val, int min, int max) {
		return Math.max(min, Math.min(val, max));
	}
	
}
