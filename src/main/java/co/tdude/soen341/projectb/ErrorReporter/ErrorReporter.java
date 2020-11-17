package co.tdude.soen341.projectb.ErrorReporter;

import java.util.ArrayList;

public class ErrorReporter {
    private ArrayList<Error> errorLst;

    public ErrorReporter (){
        errorLst = new ArrayList<Error>();
    }

    public record (Error error){
        errorLst.add(error);
    }

    public ArrayList<Error> getErrorLst() {
        return errorLst;
    }
}
