package co.tdude.soen341.projectb.Reader;

import java.io.IOException;

/**
 * Used to read characters from the assembly file.
 */
public interface IReader {
    /**
     * Reads one character at a time from the assembly file.
     * @return The next character.
     * @throws IOException
     */
    char read() throws IOException;
}
