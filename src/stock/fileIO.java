package stock;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class fileIO {
	//created copy file, search, and make dir

	public static void copyFile(String source1, String dest1, String filename) throws IOException {
		InputStream in = null;
		OutputStream out = null;

		try {
			in = new FileInputStream(new File(source1));
			out = new FileOutputStream(new File(dest1, filename));

			IOUtils.copy(in, out);

		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}

	public void copyDir(Path source, Path target) throws IOException {
		Files.copy(source, target);
	}

	public void moveDir(Path source, Path target) throws IOException {
		Files.move(source, target, REPLACE_EXISTING);
	}

	public void makeDir(String dir) throws IOException {
		FileUtils.forceMkdir(new File(dir));
	}

	public static void search(final String pattern, final File folder, List<String> result) {
		for (final File f : Objects.requireNonNull(folder.listFiles())) {

			if (f.isDirectory()) {
				search(pattern, f, result);
			}

			if (f.isFile()) {
				if (f.getName().matches(pattern)) {
					result.add(f.getAbsolutePath());
				}
			}

		}
	}

	public static void modifyFile(String filePath, String oldString, String newString){
		File fileToBeModified = new File(filePath);
		StringBuilder oldContent = new StringBuilder();
		BufferedReader reader = null;
		FileWriter writer = null;

		try {
			reader = new BufferedReader(new FileReader(fileToBeModified));
			String line = reader.readLine();

			while (line != null) {
				oldContent.append(line).append(System.lineSeparator());

				line = reader.readLine();
			}
			String newContent = oldContent.toString().replaceAll(oldString, newString);

			writer = new FileWriter(fileToBeModified);
			writer.write(newContent);
		}
		catch (IOException e){
			e.printStackTrace();
		}
		finally {
			try {
				assert reader != null;
				reader.close();
				assert writer != null;
				writer.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
