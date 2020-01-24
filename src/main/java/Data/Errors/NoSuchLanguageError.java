package Data.Errors;

import Data.Application.DataManager;

public class NoSuchLanguageError extends Error {

    public NoSuchLanguageError(DataManager dataManager) {
        this.printStackTrace();
        dataManager.fatalErrorShutDown();
    }

}
