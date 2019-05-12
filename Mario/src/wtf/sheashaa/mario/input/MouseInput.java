package wtf.sheashaa.mario.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import wtf.sheashaa.mario.Game;
import wtf.sheashaa.mario.gfx.gui.Button;

public class MouseInput implements MouseListener, MouseMotionListener {

	public int x, y;
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (Button button : Game.launcher.buttons) {
			if ((x >= button.getX() && x <= button.getX() + button.getWidth())
					&& (y >= button.getY() && y <= button.getY() + button.getHeight())) {
				button.triggerEvent();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
}
