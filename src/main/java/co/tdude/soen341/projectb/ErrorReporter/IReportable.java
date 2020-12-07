package co.tdude.soen341.projectb.ErrorReporter;

import java.util.ArrayList;

/**
 * Interface that records errors and gets the list of errors.
 */
public interface IReportable {
    /**
     * Record the current error.
     * @param error The error to be recorded.
     */
    void record(Error error);

    /**
     * The list of Errors.
     * @return An ArrayList of Error objects.
     */
    ArrayList<Error> getErrorLst();
}
