package co.tdude.soen341.projectb.ErrorReporter;

import java.util.ArrayList;

/**
 * Records errors and stores them in a List.
 */
public class ErrorReporter {

    /**
     * List of errors.
     */
    private ArrayList<Error> errorLst;

    /**
     * Constructor to create new ErrorReporter objects.
     */
    public ErrorReporter (){
        errorLst = new ArrayList<Error>();
    }

    /**
     * Adding error objects to the array list
     * @param error The error to be appended
     */
    public void record (Error error){
        errorLst.add(error);
    }

    /**
     * Returns the array list
     * @return: The error list
     */
    public ArrayList<Error> getErrorLst() {
        return errorLst;
    }
}
