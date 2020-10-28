package template.imGuiMain;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.flag.JImInputTextFlags;
import org.ice1000.jimgui.util.JniLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class textBox {
	public static void main(String[] args) throws IOException {
		byte[] InputBuf = new byte[6];
		String input = "";
		String inputFinal = "";

		JniLoader.load();
		try(JImGui imGui = new JImGui()) {
			imGui.initBeforeMainLoop();
			while (!imGui.windowShouldClose()) {
				imGui.initNewFrame();

				if(imGui.inputText("", InputBuf, JImInputTextFlags.CharsUppercase + JImInputTextFlags.CharsNoBlank)){
					input = new String(InputBuf, StandardCharsets.UTF_8);
				}

				imGui.sameLine();

				if(imGui.button("GO", 23f, 20f)){
					inputFinal = input;
					System.out.println(inputFinal);
				}

				imGui.render();
			}
		}
	}
}
