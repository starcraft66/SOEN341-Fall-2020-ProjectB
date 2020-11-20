package co.tdude.soen341.projectb.ErrorReporter;

import java.util.ArrayList;

public interface IReportable {
    void record(Error error);
    public ArrayList<Error> getErrorLst();
}
