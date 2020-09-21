import GUI.Containers.ContentPanel;
import GUI.Containers.SidebarPanel;
import GUI.Containers.TopContainer;

public class Babilonia {

    public static void main(String[] args) {

//        DataManager dataManager = new DataManager();
//        dataManager.loadData();
//        GUIManager guiManager = new GUIManager();
//        guiManager.createAndShowGUI(dataManager);

        // GUI remake in progress
        TopContainer topContainer = new TopContainer();

        SidebarPanel sidebar = new SidebarPanel();
        sidebar.init();

        ContentPanel content = new ContentPanel();

        topContainer.pack();
        topContainer.show(sidebar, content);

    }

}
