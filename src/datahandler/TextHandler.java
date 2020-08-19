package datahandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class TextHandler {
	private final String CONSTANT_PATH = "C:\\Users\\ozwemir\\eclipse-workspace\\social-platform-for-films\\resource\\";

	public boolean createTxtFile(String path, String comment) {// if only text has written then do the database work

		/* Static would be a better solution */
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONSTANT_PATH + path))) {
			writer.write(comment);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("could not create text file");
		}
		return false;
	}

	public void deleteCreatedTextFile(String path) {// delete the text file in case database errors
		try {
			Files.deleteIfExists(Paths.get(CONSTANT_PATH + path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getTxtFile(String path) {
		
		String theText = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(CONSTANT_PATH + path))) {
			while ((theText = reader.readLine()) != null) {
				return theText;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("could not get the text file");
		}
		return theText;
	}

	public String createPath(String id, String userName, List<String> paths) { // make the path unique in future
		Random random = new Random();
		int randomInt = random.nextInt(999);
		String tempPath = id + userName + randomInt + ".txt";
		for (int i = 0; i < paths.size(); i++) {

			if (tempPath.equals(paths.get(i))) {
				randomInt = random.nextInt(999);
				tempPath = id + userName + randomInt + ".txt";
				i = 0; // re-start the loop in case of generated same number
			}
		}
		return tempPath;
	}
}
