package v3.global.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;

public class csv {
	public static csvItem[] parse(String path) throws IOException {
		LinkedList<csvItem> items = new LinkedList<csvItem>();

		String line;
		String cvsSplitBy = ",";

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			while ((line = br.readLine()) != null) {
				items.add(new csvItem(line.split(cvsSplitBy)));
			}
		}

		Object[] itemsArray = items.toArray();

		return Arrays.copyOf(itemsArray, itemsArray.length, csvItem[].class);
	}
}
