package com.mcpacks.installer.component;

import com.mcpacks.installer.Main;
import com.renderengine.api.Display;
import com.renderengine.api.components.ScrollableLayout;

public class LayoutResourceSelection extends ScrollableLayout {

	public static final int OPTIONS_BAR_HEIGHT = 25;
	
	public LayoutResourceSelection() {
		super(0, OPTIONS_BAR_HEIGHT * Main.SCALE, (int) (200 * Main.SCALE), (int) (Display.getHeight() - OPTIONS_BAR_HEIGHT * Main.SCALE));
		this.setScrollSpeed(16 * Main.SCALE);
	}
}