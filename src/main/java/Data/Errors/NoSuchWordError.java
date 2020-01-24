package Data.Errors;

import Data.Application.DataManager;

public class NoSuchWordError extends Error {

    public NoSuchWordError(DataManager dataManager) {
        this.printStackTrace();
        dataManager.fatalErrorShutDown();
    }

}
