package co.tdude.soen341.projectb.ErrorReporter;

import java.util.ArrayList;

/**
 * Implementation of IReportable. Reports assembly file parsing errors.
 */
public class ErrorReporter implements IReportable {
    /**
     * ArrayList which contains every error needing to be printed.
     */
    private ArrayList<Error> errorLst;

    /**
     * Constructor used to create and ErrorReporter object.
     */
    public ErrorReporter ()
    {
        errorLst = new ArrayList<Error>();
    }

    /**
     * Adds an error object to the errorLst ArrayList.
     * @param error The error object.
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
