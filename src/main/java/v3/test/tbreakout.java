package v3.test;

import v3.global.api.apiCredentials;
import v3.screener.breakout.breakout;

import java.io.IOException;

public class tbreakout {
	public static void main(String[] args) throws IOException {
		breakout test = new breakout(new apiCredentials());

		test.update();
	}
}
