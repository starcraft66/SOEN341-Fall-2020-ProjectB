package co.tdude.soen341.projectb.ErrorReporter;

public class Error {
    String description;
    Position position;

    public Error(String description, Position position){
        this.description = description;
        this.position = position;
    }

    public Error create (String desc, Position pos){
        return  new Error(desc, pos);
    }
}
