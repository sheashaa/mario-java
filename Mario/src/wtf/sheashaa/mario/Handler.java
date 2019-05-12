package wtf.sheashaa.mario;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import wtf.sheashaa.mario.entity.Coin;
import wtf.sheashaa.mario.entity.Entity;
import wtf.sheashaa.mario.entity.mob.Goomba;
import wtf.sheashaa.mario.entity.mob.Koopa;
import wtf.sheashaa.mario.entity.mob.Player;
import wtf.sheashaa.mario.entity.mob.TowerBoss;
import wtf.sheashaa.mario.entity.powerup.Star;
import wtf.sheashaa.mario.tile.Flag;
import wtf.sheashaa.mario.tile.Pipe;
import wtf.sheashaa.mario.tile.PowerUpBlock;
import wtf.sheashaa.mario.tile.Tile;
import wtf.sheashaa.mario.tile.Wall;

public class Handler {
	
	public LinkedList<Entity> entity = new LinkedList<Entity>();
	public LinkedList<Tile> tile = new LinkedList<Tile>();

	public int levelWidth;
	public int levelHeight;
	
	public Handler() {
		
	}
	
	public void render(Graphics g) {
		for (Entity en : entity) {
			if (Game.getVisibleArea() != null && en.getBounds().intersects(Game.getVisibleArea())) en.render(g);
			
		}
		
		for (Tile ti : tile) {
			if (Game.getVisibleArea() != null && ti.getBounds().intersects(Game.getVisibleArea())) ti.render(g);
		}
	}
	
	public void tick() {
		for (int i = 0; i < entity.size(); i++) {
//			System.out.println(en.getId().toString());
			Entity en = entity.get(i);
			if ((en.getId() != Id.player && Game.getVisibleArea() != null && en.getBounds().intersects(Game.getVisibleArea())) || en.getId() == Id.player) {
				en.tick();
			}
		}
		
		for (int i = 0; i < tile.size(); i++) {
			Tile ti = tile.get(i);
			if (Game.getVisibleArea() != null && ti.getBounds().intersects(Game.getVisibleArea())) ti.tick();
		}
	}
	
	public void addEntity(Entity en) {
		entity.add(en);
	}
	
	public void removeEntity(Entity en) {
		entity.remove(en);
	}
	
	public void addTile(Tile ti) {
		tile.add(ti);
	}
	
	public void removeTile(Tile ti) {
		tile.remove(ti);
	}
	
	public void createLevel(BufferedImage level) {
//		for (int i = 0; i < Game.WIDTH * Game.SCALE / 64 + 1; i++) {
//			addTile(new Wall(i*64, Game.HEIGHT * Game.SCALE - 64, 64, 64, true, Id.wall, this));
//			if (i != 0 && i != 1 && i != 16 && i != 17) 			addTile(new Wall(i*64, 300, 64, 64, true, Id.wall, this));
//
//		}
		levelWidth = level.getWidth();
		levelHeight = level.getHeight();
		
		for (int y = 0; y < levelHeight; y++) {
			for (int x = 0; x < levelWidth; x++) {
				int pixel = level.getRGB(x, y);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = pixel & 0xff;

				if (red == 0 && green == 0 && blue == 0) addTile(new Wall(x * 64, y * 64, 64, 64, true, Id.wall, this));
				if (red == 0 && green == 0 && blue == 255) addEntity(new Player(x * 64, y * 64, 64, 64, true, Id.player, this));
//				if (red == 0 && green == 255 && blue == 0) addEntity(new Mushroom(x * 64, y * 64, 64, 64, true, Id.mushroom, this));
				if (red == 255 && green == 0 && blue == 0) addEntity(new Goomba(x * 64, y * 64, 64, 64, true, Id.goomba, this));
				if (red == 255 && green == 255 && blue == 0) addTile(new PowerUpBlock(x * 64, y * 64, 64, 64, true, Id.powerUp, this, Game.flower[0], 2));
				if (red == 100 && green == 100 && blue == 100) addTile(new Pipe(x * 64, y * 64, 64, 64*3, true, Id.pipe, this, 0, true));
				if (red == 200 && green == 200 && blue == 200) addTile(new Pipe(x * 64, y * 64, 64, 64*3, true, Id.pipe, this, 2, false));
				if (red == 20 && green == 20 && blue == 20) addEntity(new Coin(x * 64, y * 64, 64, 64, false, Id.coin, this));
				if (red == 6 && green == 6 && blue == 6) addEntity(new TowerBoss(x * 64, y * 64, 64, 64, true, Id.towerBoss, this, 3));
				if (red == 69 && green == 69 && blue == 69) addEntity(new Koopa(x * 64, y * 64, 64, 64, true, Id.koopa, this));
				if (red == 13 && green == 13 && blue == 13) addTile(new Flag(x * 64, y * 64, 64, 64 * 8, true, Id.flag, this));
				if (red == 26 && green == 26 && blue == 26) addEntity(new Star(x * 64, y * 64, 64, 64, true, Id.star, this));
			}
		}
		
		levelWidth *= 64;
		levelHeight *= 64;
		
	}
	
	public void clearLevel() {
		entity.clear();
		tile.clear();
	}
}
