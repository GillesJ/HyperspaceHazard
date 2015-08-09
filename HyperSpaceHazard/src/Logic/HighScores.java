package Logic;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Stream;

public class HighScores {

	private String filename;

	//constructor
	public HighScores(String filename) {
		this.setFileName(filename);
	}

	//methods
	public String[] readScores(int amount) {
		/** this method simply reads a certain number of scores from the document equalling the given amount */
		//count the number of lines in the score document
		int numberOfLines;
		try {
			numberOfLines = countLines(this.filename);
		} catch (IOException e) {
			e.printStackTrace();
			numberOfLines = 1;
		}

		//adapt to the amount
		int readLines = 0;
		int leftOver = 0;
		if (amount <= numberOfLines) {
			readLines = amount;
		} else {
			readLines = numberOfLines;
			leftOver = amount - numberOfLines;
		}

		//read the file and sort the data
		String[] emptyOutput = new String[leftOver];
		if (leftOver > 0) {
			for (int i = 0; i < emptyOutput.length; i++) {
				emptyOutput[i] = "Unknown:Unknown:Unknown:Unknown";
			}
		}
		String[] data = new String[numberOfLines];
		String[] sortedData = new String[numberOfLines];
		String[] output = new String[numberOfLines];
		data = readFile(this.filename, numberOfLines);
		sortedData = sortData(data);
		output = Arrays.copyOfRange(sortedData, 0, readLines);

		String[] result = new String[amount];
		result = Stream.concat(Arrays.stream(output), Arrays.stream(emptyOutput)).toArray(String[]::new);
		return result;
	}

	public void writeScore(double score, String ship, String commander) {
		/** this method writes a new score to the current file */
		LocalDate today = LocalDate.now();
		String newScore = score + ":" + ship + ":" + commander + ":" + today;

		//count the number of lines in the score document
		int numberOfLines;
		try {
			numberOfLines = countLines(this.filename);
		} catch (IOException e) {
			e.printStackTrace();
			numberOfLines = 1;
		}

		//test how many scores in the file and act accordingly
		if (numberOfLines < 5) {
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(new FileOutputStream(this.filename, true));
				writer.println(newScore);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				writer.close();
			}
		} else {
			int n = numberOfLines + 1;
			String[] data = new String[numberOfLines];
			String[] newData = new String[n];
			String[] sortedData = new String[n];
			String[] writeData = new String[5];

			//append the new score to the data
			data = readFile(filename, numberOfLines);
			for (int i = 0; i < data.length; i++) {
				newData[i] = data[i];
			}
			newData[n - 1] = newScore;

			//sort the data based on the scores (first element of every data string)
			sortedData = sortData(newData);
			for (int j = 0; j < writeData.length; j++) {
				writeData[j] = sortedData[j];
			}

			//overwrite the document with the first 5 scores
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(filename);
				for (int k = 0; k < writeData.length; k++) {
					writer.println(writeData[k]);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				writer.close();
			}
		}
	}

	public String[] sortData(String[] inputData) {
		/** this method sorts a string array of data based upon a given element in the string array (the first) */
		double[] scores = new double[inputData.length];
		int[] sortedIndex = new int[inputData.length];

		//extract the first element from the inputData string array
		for (int i = 0; i < inputData.length; i++) {
			String[] dataLine = inputData[i].split(":");
			scores[i] = Double.parseDouble(dataLine[0]);
		}

		//now sort and return the sorted indices
		sortedIndex = sortIndices(scores);

		//sort the string array accordingly and return the new sorted array
		String[] sortedData = new String[inputData.length];
		for (int j = 0; j < sortedIndex.length; j++) {
			int index = sortedIndex[j];
			sortedData[j] = inputData[index];
		}
		return sortedData;
	}

	public String[] readFile(String filename, int numberOfLines) {
		/** this method reads all the data in a file and returns them as a string array */
		String[] data = new String[numberOfLines];

		//initialize the reader to the file
		Scanner reader = null;
		try {
			reader = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//read the file
		String line;
		for (int i = 0; i < numberOfLines; i++) {
			line = reader.nextLine();
			data[i] = line;
		}

		//close readers and return output
		reader.close();
		return data;
	}

	public static int countLines(String filename) throws IOException {
		/** this method reads the number of lines in the file (which equals the number of times the game has been played */
		InputStream is1 = new BufferedInputStream(new FileInputStream(filename));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is1.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} finally {
			is1.close();
		}
	}

	public static int[] sortIndices(double[] array) {
		/** this method takes a list and returns a list of the sorted indices (in descending order) of that list */
		Integer[] indices = new Integer[array.length];
		for(int i = 0; i < indices.length; i++) {
			indices[i] = i;
		}
		Comparator<Integer> scoreComparator = new ScoreComparator(array);
		Arrays.sort(indices, scoreComparator);
		int[] sortedIndices = new int[array.length];
		for(int j = 0; j < indices.length; j++) {
			sortedIndices[j] = indices[j];
		}
		return sortedIndices;
	}

	//setter
	public void setFileName(String filename) {
		this.filename = filename;
	}

	//getter
	public String getFileName() {
		return this.filename;
	}
}
