package com.mcpacks.installer;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.mcpacks.installer.util.Utils;

@SuppressWarnings("serial")
public class Main extends Canvas implements Runnable {

	public static final String TITLE = "Functster - Data Pack installer";
	public static final String VERSION = "1.0";
	public static final int WIDTH = 800;
	public static final int HEIGHT = WIDTH / 12 * 9;
	
	public static final String API_LINK = "https://minecraftpacks.net/api/resources.php";

	private static Main instance;

	private JFrame frame;
	private boolean running;

	private Frame window;

	public Main() {
		instance = this;
	}

	private void init() throws Exception {
		this.frame = new JFrame(TITLE + " v" + VERSION);
		this.frame.setIconImage(Utils.loadImage("icon/icon.png"));
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLayout(new BorderLayout());

		Dimension size = new Dimension(WIDTH, HEIGHT);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setPreferredSize(size);
		
		this.frame.add(this, BorderLayout.CENTER);
		this.frame.pack();
		
		this.frame.setResizable(false);
		this.frame.setLocationRelativeTo(null);
		
		this.frame.requestFocus();
		this.frame.setVisible(true);

		this.window = new Frame();
	}

	@Override
	public void run() {
		try {
			this.init();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		this.start();

		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000000D / 60D;
		double delta = 0;
		
		while (true) {
			try {
				while (this.running) {
					long now = System.nanoTime();
					delta += (now - lastTime) / nsPerTick;
					lastTime = now;
					
					while(delta >= 1) {
						this.update();						
					}
					
					this.render();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	private void update() {
		this.window.update();
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		this.window.render(g);
		g.dispose();
		bs.show();
	}

	public void start() {
		if (this.running)
			return;
		this.running = true;
	}

	public void stop() {
		if (!this.running)
			return;
		this.running = false;
	}

	public static void main(String[] args) {
		new Thread(new Main()).start();
	}

	public static Main getInstance() {
		return instance;
	}
}