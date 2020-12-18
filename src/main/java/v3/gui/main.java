package v3.gui;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.util.JniLoader;
import v3.global.api.apiCredentials;

public class main {
	apiCredentials apikey = new apiCredentials();

	public static void main(String[] args) {
		JniLoader.load();
		try(JImGui imGui = new JImGui()) {
			imGui.initBeforeMainLoop();
			while (!imGui.windowShouldClose()) {
				imGui.initNewFrame();

				imGui.render();
			}
		}
	}
}
