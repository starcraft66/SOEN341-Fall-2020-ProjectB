package co.tdude.soen341.projectb.Reader;

import java.io.*;
import java.util.logging.Logger;

/**
 * Used to read characters from the assembly file.
 */
public class Reader implements IReader {
    private BufferedReader bfReader;

    /**
     * Reads one character at a time from the assembly file.
     * @return The next character.
     * @throws IOException
     */
    @Override
    public char read() throws IOException {
        int nextChar = bfReader.read();
        return isEOFchar(nextChar) ? '\0' : (char) nextChar;
    }

    /**
     * Constructor used to create a Reader object.
     * @param asmFile: The assembly file to be read.
     */
    public Reader(File asmFile) {
        try {
            this.bfReader = new BufferedReader(new FileReader(asmFile));
        } catch (FileNotFoundException e) {
            Logger.getLogger("").severe("File not found: " + asmFile.getAbsolutePath());
        }
    }

    /**
     * Close the reader
     * @throws IOException: In case an error occurs
     */
    public void closeReader () throws IOException {
        bfReader.close();
    }

    private boolean isEOFchar(int nextChar){
        return (nextChar == -1);
    }
}
