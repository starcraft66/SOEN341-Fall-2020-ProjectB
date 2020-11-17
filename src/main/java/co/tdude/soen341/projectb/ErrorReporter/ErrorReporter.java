package co.tdude.soen341.projectb.ErrorReporter;

import java.util.ArrayList;

public class ErrorReporter {
    private ArrayList<Error> errorLst;

    public ErrorReporter (){
        errorLst = new ArrayList<Error>();
    }

    /**
     * Adding error objects to the array list
     * @param error: the error to be appended
     */
    public void record (Error error){
        errorLst.add(error);
    }

    /**
     * returning the array list
     * @return: the error list
     */
    public ArrayList<Error> getErrorLst() {
        return errorLst;
    }
}
