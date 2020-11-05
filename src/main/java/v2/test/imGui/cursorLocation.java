package v2.test.imGui;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImGuiIO;
import org.ice1000.jimgui.JImStyleColors;
import org.ice1000.jimgui.JImVec4;
import org.ice1000.jimgui.util.JniLoader;

public class cursorLocation {
	public static void main(String[] args) {
		JniLoader.load();
		try(JImGui imGui = new JImGui()) {
			imGui.initBeforeMainLoop();
			while (!imGui.windowShouldClose()) {
				imGui.initNewFrame();

				imGui.showStyleEditor();

				float x = JImGuiIO.getMousePosX();
				float y = JImGuiIO.getMousePosY();

				imGui.text(String.valueOf(x));
				imGui.text(String.valueOf(y));

				imGui.render();
			}
		}
	}
}
