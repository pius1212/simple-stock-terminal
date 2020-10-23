package v1.template.imGuiMain;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.util.JniLoader;

public class main {
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
