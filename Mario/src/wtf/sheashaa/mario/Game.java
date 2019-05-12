package wtf.sheashaa.mario;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import wtf.sheashaa.mario.entity.Entity;
import wtf.sheashaa.mario.gfx.Sprite;
import wtf.sheashaa.mario.gfx.SpriteSheet;
import wtf.sheashaa.mario.gfx.gui.Launcher;
import wtf.sheashaa.mario.input.KeyInput;
import wtf.sheashaa.mario.input.MouseInput;

public class Game extends Canvas implements Runnable {
	
	public static final int WIDTH = 270;
	public static final int HEIGHT = WIDTH / 14 * 10;
	public static final int SCALE = 4;
	public static final String TITLE = "Mario";
	
	private Thread thread;
	private boolean running = false;
	private static BufferedImage[] level;
	private BufferedImage background;
	
	public static int playerX, playerY;
	public static int currentLevel = 0;
	
	private int fpsCounter;
	
	public static int coins = 0;
	public static int lives = 5;
	public static int deathScreenTime = 0;
	
	public static boolean showDeathScreen = true;
	public static boolean gameOver = false;
	public static boolean playing = false;
	
	public static Handler handler;
	public static SpriteSheet sheetPlayer;
	public static SpriteSheet sheetTiles;
	public static SpriteSheet sheetPowerUps;
	public static SpriteSheet sheetPlant;
	public static SpriteSheet sheetStar;
	public static SpriteSheet sheetFire;
	public static Camera cam;
	public static Launcher launcher;
	public static MouseInput mouse;
	
	public static boolean BOX = true;
	
	public static Sprite grass;
	public static Sprite[] player;
	public static Sprite mushroom;
	public static Sprite[] goomba;
	public static Sprite[] powerUp;
	public static Sprite powerUpUsed;
	public static Sprite[] coin;
	public static Sprite oneUpMushroom;
	public static Sprite[] koopa;
	public static Sprite[] flag;
	public static Sprite[] plant;
	public static Sprite[] star;
	public static Sprite[] flower;
	public static Sprite[] bowser;
	public static Sprite fire;
//	public static Sprite player;
	
	private static final int[] SPRITE_IDX = {1, 3, 5, 6, 7, 9, 10, 11, 13, 15, 17, 34};
	private static final int[] COIN_SPRITES_IDX = {8, 9, 10, 11};
	private static final int[] QUES_BLOCK_SPRITES_IDX = {1, 2, 3, 4};
	private static final int[] KOOPA_IDX = {5, 6, 7, 3};
	private static final int[] FLAG_IDX = {36, 37, 38, 39};
	private static final int[] FLOWER_IDX = {6, 7, 8, 9};

	public Game() {
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
	}
	
	private void init() {
		handler = new Handler();
		sheetPlayer = new SpriteSheet("/player.png", 24);
		sheetTiles = new SpriteSheet("/tiles.png", 16);
		sheetPowerUps = new SpriteSheet("/powerups.png", 16);
		sheetPlant = new SpriteSheet("/plant.png", 16);
		sheetStar = new SpriteSheet("/star.png", 16);
		sheetFire = new SpriteSheet("/fire.png", 8);
		cam = new Camera();
		launcher = new Launcher();
		
		addKeyListener(new KeyInput());
		mouse = new MouseInput();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
		grass = new Sprite(sheetTiles, 2, 1);
//		powerUp = new Sprite(sheetTiles, 3, 1);
		powerUp = new Sprite[4];
		for (int i = 0; i < powerUp.length; i++) {
			powerUp[i] = new Sprite(sheetTiles, QUES_BLOCK_SPRITES_IDX[i], 34);
		}
		
		powerUpUsed = new Sprite(sheetTiles, 6, 1);
		mushroom = new Sprite(sheetPowerUps, 1, 1);
//		player = new Sprite(sheetPlayer, 1, 37);
		player = new Sprite[12];
		for (int i = 0; i < player.length; i++) {
			player[i] = new Sprite(sheetPlayer, SPRITE_IDX[i], 107);
		}
		
		bowser = new Sprite[12];
		for (int i = 0; i < bowser.length; i++) {
			bowser[i] = new Sprite(sheetPlayer, SPRITE_IDX[i], 4);
		}
		
		goomba = new Sprite[12];
		for (int i = 0; i < goomba.length; i++) {
			goomba[i] = new Sprite(sheetPlayer, SPRITE_IDX[i], 5);
		}
		
		koopa = new Sprite[4];
		for (int i = 0; i < koopa.length; i++) {
			koopa[i] = new Sprite(sheetPlayer, KOOPA_IDX[i], 19);
		}
		
		coin = new Sprite[4];
		for (int i = 0; i < coin.length; i++) {
			coin[i] = new Sprite(sheetTiles, COIN_SPRITES_IDX[i], 34);
		}
		
		oneUpMushroom = new Sprite(sheetPowerUps, 3, 1);
		
		flag = new Sprite[4];
		for (int i = 0; i < flag.length; i++) {
			flag[i] = new Sprite(sheetTiles, FLAG_IDX[i], 36);
		}
		
		plant = new Sprite[2];
		for (int i = 0; i < plant.length; i++) {
			plant[i] = new Sprite(sheetPlant, i + 1, 1);
		}
		
		star = new Sprite[4];
		for (int i = 0; i < star.length; i++) {
			star[i] = new Sprite(sheetStar, i + 1, 1);
		}
		
		flower = new Sprite[4];
		for (int i = 0; i < flower.length; i++) {
			flower[i] = new Sprite(sheetPowerUps, FLOWER_IDX[i], 1);
		}
		
		fire = new Sprite(sheetFire, 1, 1);
		
		level = new BufferedImage[2];
		try {
			for (int i = 0; i < level.length; i++) {
				level[i] = ImageIO.read(getClass().getResource("/level" + (i+1) + ".png"));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			background = ImageIO.read(getClass().getResource("/bg.png")).getSubimage(1, 1, 512, 512);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		handler.createLevel(level);
//		handler.addEntity(new Player(300, 512, 64, 64, true, Id.player, handler));
//		handler.addTile(new Wall(200, 200, 64, 64, true, Id.wall, handler));
	}
	
	private synchronized void start() {
//		System.out.println("Working");
		if (running) return;
		running = true;
		thread = new Thread(this, "Thread");
		thread.start();
	}
	
	private synchronized void stop() {
		if (!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		init();
		requestFocus();
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		int frames = 0;
		int ticks = 0;
//		long now = System.nanoTime();
//		System.out.println(now-lastTime);
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				ticks++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				fpsCounter = frames;
				System.out.println(frames + " FPS, " + ticks + " Ticks/sec");
				frames = 0;
				ticks = 0;
				
			}
			render();
			tick();
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
//		g.setColor(Color.BLUE);
//		g.fillRect(0, 0, getWidth(), getHeight());
//		g.setColor(Color.RED);
//		g.fillRect(200, 200, getWidth() - 400, getHeight() - 400);
		if (playing) {
			if (showDeathScreen) {
				if (gameOver) {
					g.setColor(Color.BLACK);
					g.fillRect(0, 0, getFrameWidth(), getFrameHeight());
					g.setColor(Color.WHITE);
					g.setFont(new Font("STCaiyun", Font.BOLD, 80));
					g.drawString("Game OVER!", getFrameWidth()/2 - 250, getFrameHeight()/2 + 50);
					lives = 5;
				} else {
					g.setColor(Color.BLACK);
					g.fillRect(0, 0, getFrameWidth(), getFrameHeight());
					g.drawImage(Game.player[0].getBufferedImage(), getFrameWidth()/2-64, getFrameHeight()/2-64, 128, 128, null);
					g.setColor(Color.WHITE);
					g.setFont(new Font("STCaiyun", Font.BOLD, 40));
					g.drawString("x " + lives, getFrameWidth()/2 - 31, getFrameHeight()/2 + 100);
				}
			} else {
				g.drawImage(background, 0, 0, getFrameWidth(), getFrameHeight(), null);
				g.translate(cam.getX(), cam.getY());
				handler.render(g);
				g.translate(-cam.getX(), -cam.getY());
				g.drawImage(coin[0].getBufferedImage(), 50, 20, 50, 50, null);
				g.setColor(Color.WHITE);
				g.setFont(new Font("STCaiyun", Font.BOLD, 40));
				g.drawString("" + coins, 110, 60);
				g.drawImage(Game.player[11].getBufferedImage(), getFrameWidth() / 2 - 60, 20, 64, 64, null);
				g.drawString("" + lives, getFrameWidth() / 2 + 10, 60);
				g.setFont(new Font("Arial", Font.PLAIN, 40));
				g.drawString(fpsCounter + " FPS", getFrameWidth() - 150, 60);
			}
		} else {
			launcher.render(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	// or "update"
	public void tick() {
		if (playing) handler.tick();
		
		for (Entity en : handler.entity) {
			if (en.getId() == Id.player) {
				if (!en.goingDownPipe) cam.tick(en);
			}
		}
		
		if (playing) {
			if (gameOver) {
				deathScreenTime++;
				if (deathScreenTime >= 180) {
					gameOver = false;
					showDeathScreen = true;
					deathScreenTime = 0;
					handler.clearLevel();
					handler.createLevel(level[currentLevel]);
				}
			} else {		
				if (showDeathScreen) deathScreenTime++;
				if (deathScreenTime >= 180) {
					showDeathScreen = false;
					deathScreenTime = 0;
					handler.clearLevel();
					handler.createLevel(level[currentLevel]);
				}
			}
		}
	}
	
	public static int getFrameWidth() {
		return WIDTH * SCALE;
	}
	
	public static int getFrameHeight() {
		return HEIGHT * SCALE;
	}
	
	public static void switchLevel() {
		Game.currentLevel++;
		handler.clearLevel();
		handler.createLevel(level[currentLevel]);
	}
	
	public static Rectangle getVisibleArea() {
		for (Entity e : handler.entity) {
			if (e.getId() == Id.player) {
				if (!e.goingDownPipe) {
					playerX = e.getX();
					playerY = e.getY();
					return new Rectangle(playerX - (getFrameWidth() / 2 - 5), playerY - (getFrameHeight() / 2 - 5), getFrameWidth() + 10, getFrameHeight() + 10);
				}
			}
		}
		return new Rectangle(playerX - (getFrameWidth() / 2 - 5), playerY - (getFrameHeight() / 2 - 5), getFrameWidth() + 10, getFrameHeight() + 10);
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame(TITLE);
		frame.add(game);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		game.start();
	}


}
