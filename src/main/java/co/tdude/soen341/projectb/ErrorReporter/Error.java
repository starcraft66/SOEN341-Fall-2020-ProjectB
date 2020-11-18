package co.tdude.soen341.projectb.ErrorReporter;

public class Error {
    String description;
    Position position;

    /**
     * Constructor of Error
     * @param description: description of the error
     * @param position: line and column of the location
     */
    public Error(String description, Position position){
        this.description = description;
        this.position = position;
    }

    /**
     * Creating an error
     * @param desc: description of the error
     * @param pos: position (line, column) of the error
     * @return: error object
     */
    public Error create (String desc, Position pos){
        return  new Error(desc, pos);
    }
}
