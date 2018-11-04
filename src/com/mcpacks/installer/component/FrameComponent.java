package com.mcpacks.installer.component;

public abstract class FrameComponent {

	private boolean visible;
	
	public FrameComponent() {
		
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}