import Data.Application.DataManager;
import GUI.Containers.ContentPanel;
import GUI.Containers.SidebarPanel;
import GUI.Containers.TopContainer;
import GUI.GUIManager;
import GUI.SidebarMenu;

public class Babilonia {

    public static void main(String[] args) {

//        DataManager dataManager = new DataManager();
//        dataManager.loadData();
//        GUIManager guiManager = new GUIManager();
//        guiManager.createAndShowGUI(dataManager);

        // GUI remake in progress
        TopContainer topContainer = new TopContainer();

        SidebarPanel sidebar = new SidebarPanel();
        SidebarMenu sideBarMenu = new SidebarMenu();
        sideBarMenu.init(sidebar);

        ContentPanel content = new ContentPanel();

        topContainer.pack();
        topContainer.show(sidebar, content);

    }

}
