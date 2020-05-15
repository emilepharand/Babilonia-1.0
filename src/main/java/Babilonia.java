import Data.Application.DataManager;
import GUI.Containers.ContentPanel;
import GUI.Containers.SidebarPanel;
import GUI.Containers.TopContainer;
import GUI.GUIManager;

public class Babilonia {

    public static void main(String[] args) {

        DataManager dataManager = new DataManager();
        dataManager.loadData();
        GUIManager guiManager = new GUIManager();
        guiManager.createAndShowGUI(dataManager);

        // GUI remake in progress
//        TopContainer topContainer = new TopContainer();
//        SidebarPanel sidebar = new SidebarPanel();
//        ContentPanel content = new ContentPanel();
//        topContainer.show(sidebar, content);

    }

}
