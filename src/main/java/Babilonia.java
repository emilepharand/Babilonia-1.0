import Data.Application.DataManager;
import GUI.GUIManager;

public class Babilonia {

    public static void main(String[] args) {

        DataManager dataManager = new DataManager();
        dataManager.loadData();
        GUIManager guiManager = new GUIManager();
        guiManager.createAndShowGUI(dataManager);

    }

}
