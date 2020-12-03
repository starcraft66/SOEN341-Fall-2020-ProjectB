package co.tdude.soen341.projectb.Reader;

import co.tdude.soen341.projectb.ErrorReporter.Error;
import co.tdude.soen341.projectb.ErrorReporter.ErrorReporter;

import java.io.*;
import java.util.logging.Logger;

/**
 * Used to read characters from the assembly file.
 */
public class Reader implements IReader {
    private BufferedReader bfReader;
    private  ErrorReporter ereporter=null;
    /**
     * Reads one character at a time from the assembly file.
     * @return The next character.
     * If it fails, add an IO type error to ErrorReporter
     */
    @Override
    public char read() throws IOException{
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
     * If it fails, add an IO type error to ErrorReporter
     */
    public void closeReader ()  {
       try {bfReader.close();}
       catch(IOException e){
           Error e1=new Error();
           e1.generatemsg(Error.err_type.IOERROR, null, null);
           ereporter.record(e1);

       }
    }

    /**
     * Verifies if the next char is an EOF character
     * @param nextChar: The next character
     * @return: true if EOF, false if not
     */
    private boolean isEOFchar(int nextChar){
        return (nextChar == -1);
    }
}
