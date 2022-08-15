package service;

import dataAccess.DataAccessException;
import result.ClearResult;

public class ClearService extends Service {
    public ClearResult Clear() throws DataAccessException {
        openConnections();
        if(clearData()) {
            closeConnections(true);
            return new ClearResult(true);
        }
        closeConnections(false);
        return new ClearResult(false);
    }
}
