package ass2_oisinAeonn.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDate;

public class DashboardView {

    private VBox dashboardVBox;
    private HBox topHBox; // For welcome label and menu
    private Label welcomeLabel;
    private TabPane tabPane;
    private MenuButton menuButton;
    private TextArea postList, searchResultsArea;
    private DatePicker datePicker;
    private ImageView postImageView;
    private TextField postContentField;
    private Button postButton;
    private FileChooser fileChooser;

    public DashboardView(String username) {
        
        dashboardVBox = new VBox(10);
        dashboardVBox.setPadding(new Insets(20));

        welcomeLabel = new Label("Welcome, " + username + "!");

        // Setting up the menu
        ImageView menuIcon = new ImageView(new Image("file:assets/menu.png"));
        menuIcon.setFitHeight(16);
        menuIcon.setFitWidth(16);
        menuButton = new MenuButton("", menuIcon);
        menuButton.setStyle("-fx-background-insets:0; -fx-padding:0;");

        MenuItem profileItem = new MenuItem("Profile");
        MenuItem upgradeAccountItem = new MenuItem("Upgrade Account");
        MenuItem logoutItem = new MenuItem("Logout");
        menuButton.getItems().addAll(profileItem, upgradeAccountItem, logoutItem);

        topHBox = new HBox(10, welcomeLabel, menuButton);
        HBox.setHgrow(welcomeLabel, Priority.ALWAYS);
        topHBox.setAlignment(Pos.CENTER_RIGHT);

        // Set up the tabs
        tabPane = new TabPane();

        VBox addPostVBox = new VBox(10, 
            postContentField = new TextField(),
            datePicker = new DatePicker(LocalDate.now()), // Set the current date as default
            postButton = new Button("Post"),
            new Button("Upload Image"), // Opens the file explorer
            postList = new TextArea()
        );
        addPostVBox.setPadding(new Insets(15));

        postList.setEditable(false);
        postList.setPromptText("Posts will be displayed here...");
        postContentField.setPromptText("Content");
        datePicker.setPromptText("Date of Post");
        datePicker.setEditable(false);  // To make sure users can't change the date

        Tab addPostTab = new Tab("Add Post", addPostVBox);
        addPostTab.setClosable(false);

        // Search Tab components
        searchResultsArea = new TextArea();
        searchResultsArea.setEditable(false);
        VBox searchVBox = new VBox(10, new TextField(), new Button("Search"), new Button("Delete Post"), new Button("Retrieve Post"), searchResultsArea);

        Tab searchTab = new Tab("Search", searchVBox);
        searchTab.setClosable(false);

        tabPane.getTabs().addAll(addPostTab, searchTab);

        dashboardVBox.getChildren().addAll(topHBox, tabPane);

    }

    public void setPostImageView(Image img) {
        postImageView.setImage(img);
    }

    // Getters for the controller to use
    public TextArea getPostList() {
        return postList;
    }

    public TextField getPostContentField() {
        return postContentField;
    }

    public Button getPostButton() {
        return postButton;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public FileChooser getFileChooser() {
        return fileChooser;
    }

    public Button getUploadImageButton() {
        return getUploadImageButton();
    }    

    public Parent getPane() {
        return dashboardVBox;
    }

    public File showImageFileChooser() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select an Image");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp"));
    return fileChooser.showOpenDialog(null);
}

}
