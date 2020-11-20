package co.tdude.soen341.projectb.ErrorReporter;

public class Position {
    private int column;
     private int line;

    /**
     * Constructor to create a new Position object.
     * @param column: Column within the assembly file.
     * @param line: Line within the assembly file.
     */
    public Position (int column, int line){
        this.column = column;
        this.line = line;
    }

    /**
     * The column within the assembly file.
     * @return The column position within its line.
     */
    public int getColumn() {
        return column;
    }

    /**
     * The line within the assembly file.
     * @return The line position within the assembly file.
     */
    public int getLine() {
        return line;
    }
}
