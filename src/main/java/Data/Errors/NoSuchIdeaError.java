package Data.Errors;

import Data.Application.DataManager;

public class NoSuchIdeaError extends Error {

    public NoSuchIdeaError(DataManager dataManager) {
        this.printStackTrace();
        dataManager.fatalErrorShutDown();
    }

}
