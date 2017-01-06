package com.spacenerd.protonpool.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.spacenerd.protonpool.ProtonPool;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Proton Pool";
        config.width = 800;
        config.height = 480;
		new LwjglApplication(new ProtonPool(), config);
	}
}
