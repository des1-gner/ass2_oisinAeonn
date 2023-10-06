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
    private TextField postContentField, searchField, likesField, sharesField;
    private Button postButton, uploadImageButton, deleteButton, retrieveButton;
    private MenuItem profileItem, upgradeItem, logoutItem;

    public DashboardView(String username) {
        
        dashboardVBox = new VBox(10);
        dashboardVBox.setPadding(new Insets(20));
        postContentField = new TextField();
postButton = new Button("Post");
postList = new TextArea();
datePicker = new DatePicker();

        welcomeLabel = new Label("Welcome, " + username + "!");

        // Setting up the menu
        ImageView menuIcon = new ImageView(new Image("file:assets/menu.png"));
        menuIcon.setFitHeight(16);
        menuIcon.setFitWidth(16);
        menuButton = new MenuButton("", menuIcon);
        menuButton.setStyle("-fx-background-insets:0; -fx-padding:0;");
        profileItem = new MenuItem("Profile");
        upgradeItem = new MenuItem("Upgrade Account");
        logoutItem = new MenuItem("Logout");
        menuButton.getItems().addAll(profileItem, upgradeItem, logoutItem);

        topHBox = new HBox(10, welcomeLabel, menuButton);
        HBox.setHgrow(welcomeLabel, Priority.ALWAYS);
        topHBox.setAlignment(Pos.CENTER_RIGHT);

        // Set up the tabs
        tabPane = new TabPane();

        postImageView = new ImageView();
        uploadImageButton = new Button("Upload Image");
        
        Label likesLabel = new Label("Likes:");
likesField = new TextField("0");
likesField.setEditable(true);  // Assuming users can't manually set likes.

Label sharesLabel = new Label("Shares:");
sharesField = new TextField("0");
sharesField.setEditable(true);  // Assuming users can't manually set shares.

// ... other components ...

VBox addPostVBox = new VBox(10, 
    postContentField,
    likesLabel,
    likesField,
    sharesLabel,
    sharesField,
    datePicker,
    uploadImageButton,
    postImageView,
    postButton,
    postList
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
        searchField = new TextField();
        searchField.setPromptText("Enter Post ID...");
        deleteButton = new Button("Delete Post");
        retrieveButton = new Button("Retrieve Post");
        searchResultsArea = new TextArea();
        searchResultsArea.setEditable(false);
        
        VBox searchVBox = new VBox(10, searchField, deleteButton, retrieveButton, searchResultsArea);

        Tab searchTab = new Tab("Search", searchVBox);
        searchTab.setClosable(false);

        tabPane.getTabs().addAll(addPostTab, searchTab);

        dashboardVBox.getChildren().addAll(topHBox, tabPane);

        postImageView.setFitHeight(100);
postImageView.setFitWidth(100);
postImageView.setPreserveRatio(true);  // This will ensure the image's aspect ratio is maintained

    }

    public void setPostImageView(Image img) {
        postImageView.setImage(img);
    }

    // ... rest of the getters ...

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getRetrieveButton() {
        return retrieveButton;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public Button getUploadImageButton() {
        return uploadImageButton;
    }

    public Button getPostButton() {
        return postButton;
    }

    public TextField getLikesField() {
        return likesField;
    }
    
    public TextField getSharesField() {
        return sharesField;
    }
    
    
    public MenuItem getProfileMenuItem() {
        return profileItem;
    }
    
    public MenuItem getUpgradeMenuItem() {
        return upgradeItem;
    }
    
    public MenuItem getLogoutMenuItem() {
        return logoutItem;
    }
    
    public TextField getPostContentField() {
        return postContentField;
    }
    

    public File showImageFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp"));
        return fileChooser.showOpenDialog(null);
    }

    public VBox getProfileView() {
        VBox profileVBox = new VBox(new Label("Profile View"));
        Button backButton = new Button("Back to Dashboard");
        backButton.setOnAction(e -> setDashboardContent()); // We will need to modify this action in the controller
        profileVBox.getChildren().add(backButton);
        return profileVBox;
    }

    public VBox getUpgradeView() {
        VBox upgradeVBox = new VBox(new Label("Upgrade Account View"));
        Button backButton = new Button("Back to Dashboard");
        backButton.setOnAction(e -> setDashboardContent()); // We will need to modify this action in the controller
        upgradeVBox.getChildren().add(backButton);
        return upgradeVBox;
    }
    
    private Object setDashboardContent() {
        return dashboardVBox;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
    
    public void setShares(int shares) {
        this.shares = shares;
    }
    

    public VBox getDashboardView() {
        return dashboardVBox;
    }

    public Parent getPane() {
        return dashboardVBox;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }
    
    
}
