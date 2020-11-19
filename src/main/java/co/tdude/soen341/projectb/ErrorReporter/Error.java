package co.tdude.soen341.projectb.ErrorReporter;

/**
 * Error reporting class used to hold error descriptions and positions within the assembly file.
 */
public class Error {
    String description;
    Position position;

    /**
     * Constructor used to create an Error object
     * @param description: Description of the error
     * @param position: Line and column of the location
     */
    public Error(String description, Position position){
        this.description = description;
        this.position = position;
    }

    /**
     * Creating an error
     * @param desc: Description of the error
     * @param pos: Position (line, column) of the error
     * @return: error object
     */
    public Error create (String desc, Position pos){
        return new Error(desc, pos);
    }
}
