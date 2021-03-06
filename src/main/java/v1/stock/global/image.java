package v1.stock.global;

import org.apache.commons.io.FileUtils;
import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImTextureID;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class image {
	@SuppressWarnings("AccessStaticViaInstance")
	public static void renderImageFromLink(JImGui imGui, String link) throws IOException {
		Random r = new Random();
		URL url = new URL(link);

		File file = new File("temp/" + r.nextInt());
		FileUtils.copyURLToFile(url, file);

		JImTextureID texture = JImTextureID.fromFile(file);
		imGui.initNewFrame();
		imGui.image(texture, imGui.getWindowWidth(), imGui.getWindowHeight());
	}
}