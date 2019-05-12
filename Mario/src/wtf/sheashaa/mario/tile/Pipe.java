package wtf.sheashaa.mario.tile;

import java.awt.Color;
import java.awt.Graphics;

import wtf.sheashaa.mario.Handler;
import wtf.sheashaa.mario.Id;
import wtf.sheashaa.mario.entity.mob.Plant;

public class Pipe extends Tile {

	public Pipe(int x, int y, int width, int height, boolean solid, Id id, Handler handler, int facing, boolean plant) {
		super(x, y, width, height, solid, id, handler);
		this.facing = facing;
		if (plant) handler.addEntity(new Plant(getX(), getY() - 64, 64, 64, true,Id.plant, handler));
	}

	@Override
	public void render(Graphics g) {
		if (facing == 0) g.setColor(Color.RED);
		else if (facing == 2) g.setColor(Color.YELLOW);
//		g.setColor(new Color(128, 128, 128));
		g.fillRect(x, y, width, height);		
	}

	@Override
	public void tick() {
		
	}

}
