package co.tdude.soen341.projectb.ErrorReporter;

public class Position {
    private int column;
     private int line;

    public Position (int column, int line){
        this.column = column;
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public int getLine() {
        return line;
    }
}
