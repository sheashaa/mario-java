package wtf.sheashaa.mario.gfx.gui;

import java.awt.Color;
import java.awt.Graphics;

import wtf.sheashaa.mario.Game;

public class Launcher {
	
	public Button[] buttons;
	
	public Launcher() {
		buttons = new Button[2];
		
		buttons[0] = new Button(Game.getFrameWidth() / 2 - 200, 350, 400, 100, "Start Game");
		buttons[1] = new Button(Game.getFrameWidth() / 2 - 200, 500, 400, 100, "Exit Game");
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.getFrameWidth(), Game.getFrameHeight());
		g.drawImage(Game.player[0].getBufferedImage(), Game.getFrameWidth() / 2 - 75, 150, 150, 150, null);
		
		for (Button button : buttons) {
			button.render(g);
		}
	}
}
