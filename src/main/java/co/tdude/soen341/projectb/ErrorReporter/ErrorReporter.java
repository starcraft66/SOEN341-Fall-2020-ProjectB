package co.tdude.soen341.projectb.ErrorReporter;

import java.util.ArrayList;


public class ErrorReporter implements IReportable {
   //ArrayList which contains every error needing to be printed
    private ArrayList<Error> errorLst;

    public ErrorReporter ()
    {
        errorLst = new ArrayList<Error>();
    }

    //adds an error object to the errorLst ArrayList
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
