package network;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * @author Harry Tran on 6/14/19.
 * @project NeatSearch
 * @email trunghieu.tran@utdallas.edu
 * @organization UTDallas
 */
public class FileWriter {
	public static void writeStringToFile(String outputFile, String data) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new java.io.FileWriter(outputFile));
			writer.write(data);
		}
		catch (IOException e) {
			System.out.println("Writing error <<<");
		}
		finally {
			try {
				if (writer != null)
					writer.close();
			}
			catch (IOException e) {
				System.out.println("Flie Closing error <<<");
			}
		}
	}
}
