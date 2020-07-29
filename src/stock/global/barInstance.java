package stock.global;

import stock.test.bar.bar;
import stock.test.bar.liveBar;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class barInstance {
	private int barColor = Color.GREEN.getRGB();
	private boolean requestBar = true;
	private boolean isLongPos = true;

	public barInstance(){

	}

	public void setBarColor(int barColor) {
		this.barColor = barColor;
	}

	public void setLongPos(boolean longPos) {
		isLongPos = longPos;
	}

	public void setRequestBar(boolean requestBar) {
		this.requestBar = requestBar;
	}

	public boolean isLongPos() {
		return isLongPos;
	}

	public boolean isRequestBar() {
		return requestBar;
	}

	public int getBarColor() {
		return barColor;
	}
}
