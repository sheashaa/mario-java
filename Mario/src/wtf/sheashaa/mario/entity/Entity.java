package wtf.sheashaa.mario.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import wtf.sheashaa.mario.Game;
import wtf.sheashaa.mario.Handler;
import wtf.sheashaa.mario.Id;
import wtf.sheashaa.mario.states.BossState;
import wtf.sheashaa.mario.states.KoopaState;
import wtf.sheashaa.mario.states.PlayerState;

public abstract class Entity {
	
	public int x, y;
	public int width, height;
	public int facing = 0; // 0: left - 1: right
	public int hp;
	public int phaseTime;
	public int type;

	public boolean solid;
	public boolean jumping = false;
	public boolean falling = false;
	public boolean goingDownPipe = false;
	public boolean attackable = false;
	
	public int velX, velY;
	
	public Id id;
	public BossState bossState;
	public KoopaState koopaState;
	public PlayerState state;
	
	public double gravity = 0.0;
	
	public Handler handler;
	
	public int frame = 0;
	public int frameDelay = 0;
	
	public Entity(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.solid = solid;
		this.id = id;
		this.handler = handler;
	}
	
	public abstract void render(Graphics g);
	
	public abstract void tick();
	
	public void die() {
		handler.removeEntity(this);
		if (getId() == Id.player) {
			Game.lives--;
			Game.showDeathScreen = true;
			if (Game.lives <= 0) Game.gameOver = true;
		}
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

	public boolean isSolid() {
		return solid;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}
	
	public Id getId() {
		return id;
	}
	
	public int getType() {
		return type;
	}
	
	// COLLISION BOX CODE IS VERY BUGGED - RECALCULATE!
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), width, height);
	}
	
	public Rectangle getBoundsTop() {
		return new Rectangle(getX() + 10, getY(), width - 20, 5);
	}
	
	public Rectangle getBoundsBottom() {
		return new Rectangle(getX() + 10, getY() + height - 5, width - 20, 5);
	}
	
	public Rectangle getBoundsLeft() {
		return new Rectangle(getX(), getY() + 10, 5, height - 20);
	}
	
	public Rectangle getBoundsRight() {
		return new Rectangle(getX() + width - 5, getY() + 10, 5, height - 20);
	}
}
