package ass2_oisinAeonn.UI;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import ass2_oisinAeonn.Model.Post;

public class DashboardView {

    private VBox dashboardVBox;
    private HBox topHBox;
    private Label welcomeLabel;
    private TabPane tabPane;
    private MenuButton menuButton;
    private TextArea postList, searchResultsArea;
    private DatePicker datePicker;
    private ImageView postImageView;
    private TextField postContentField, searchField, likesField, sharesField;
    private Button postButton, uploadImageButton, deleteButton, retrieveButton;
    private MenuItem profileItem, upgradeItem, logoutItem;
    private Button filterButton;
    private RadioButton postIdRadio, dateRadio, likesRadio, sharesRadio;
    private ToggleGroup radioGroup;
    private TextField retrieveCountField;
    private ComboBox<String> sortOrderComboBox;
    private int likes;
    private int shares;
    private ListView<Post> postsListView;
    private ImageView searchedPostImageView;

    public DashboardView(String username) {

        searchedPostImageView = new ImageView();
searchedPostImageView.setFitHeight(100);
searchedPostImageView.setFitWidth(100);
searchedPostImageView.setPreserveRatio(true);
        dashboardVBox = new VBox(10);
        dashboardVBox.setPadding(new Insets(20));
        postContentField = new TextField();
        postButton = new Button("Post");
        postList = new TextArea();
        datePicker = new DatePicker();

        welcomeLabel = new Label("Welcome, " + username + "!");
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

        tabPane = new TabPane();
        postImageView = new ImageView();
        uploadImageButton = new Button("Upload Image");

        Label likesLabel = new Label("Likes:");
        likesField = new TextField("0");
        likesField.setEditable(true);

        Label sharesLabel = new Label("Shares:");
        sharesField = new TextField("0");
        sharesField.setEditable(true);

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
        datePicker.setEditable(false);

        Tab addPostTab = new Tab("Add Post", addPostVBox);
        addPostTab.setClosable(false);

        searchField = new TextField();
        searchField.setPromptText("Enter Post ID...");
        deleteButton = new Button("Delete Post");
        retrieveButton = new Button("Retrieve Post");
        searchResultsArea = new TextArea();
        searchResultsArea.setEditable(false);
        
        VBox searchVBox = new VBox(10, searchField, deleteButton, retrieveButton, searchedPostImageView, searchResultsArea);
        Tab searchTab = new Tab("Search", searchVBox);
        searchTab.setClosable(false);

        Tab trendingTab = new Tab("Trending");
        trendingTab.setClosable(false);
        tabPane.getTabs().addAll(addPostTab, searchTab, trendingTab);
        dashboardVBox.getChildren().addAll(topHBox, tabPane);

        postImageView.setFitHeight(100);
        postImageView.setFitWidth(100);
        postImageView.setPreserveRatio(true);

        VBox layout = new VBox();
        HBox controls = new HBox();

        filterButton = new Button("Filter");
        radioGroup = new ToggleGroup();
        postIdRadio = new RadioButton("Post ID");
        dateRadio = new RadioButton("Date");
        postIdRadio.setSelected(true);
        likesRadio = new RadioButton("Likes");
        sharesRadio = new RadioButton("Shares");
        postIdRadio.setToggleGroup(radioGroup);
        dateRadio.setToggleGroup(radioGroup);
        likesRadio.setToggleGroup(radioGroup);
        sharesRadio.setToggleGroup(radioGroup);
        retrieveCountField = new TextField();
        retrieveCountField.setPromptText("Number of posts (up to 100)");
        sortOrderComboBox = new ComboBox<>();
        sortOrderComboBox.setItems(FXCollections.observableArrayList("Ascending", "Descending"));
        sortOrderComboBox.setValue("Descending");
        controls.getChildren().addAll(filterButton, postIdRadio, dateRadio, likesRadio, sharesRadio, retrieveCountField, sortOrderComboBox);
        postsListView = new ListView<>();
        setupPostsListView();
        layout.getChildren().addAll(controls, postsListView);
        trendingTab.setContent(layout);
    }

    private void setupPostsListView() {
        postsListView.setCellFactory(postsListView -> new ListCell<Post>() {
            @Override
            protected void updateItem(Post post, boolean empty) {
                super.updateItem(post, empty);
                if (post == null || empty) {
                    setText(null);
                } else {
                    String displayText = "Author: " + post.getAuthor() + 
                        "\nContent: " + post.getContent() +
                        "\nLikes: " + post.getLikes() +
                        "\nShares: " + post.getShares() + 
                        "\nDateTime: " + post.getDateTime();
                    setText(displayText);
                }
            }
        });
    }

    public void setPostImageView(Image img) {
        postImageView.setImage(img);
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getFilterButton() {
        return filterButton;
    }

    public RadioButton getPostIdRadio() {
        return postIdRadio;
    }

    public ToggleGroup getRadioGroup() {
        return radioGroup;
    }

    public RadioButton getDateRadio() {
        return dateRadio;
    }

    public RadioButton getLikesRadio() {
        return likesRadio;
    }

    public RadioButton getSharesRadio() {
        return sharesRadio;
    }

    public TextField getRetrieveCountField() {
        return retrieveCountField;
    }

    public ComboBox<String> getSortOrderComboBox() {
        return sortOrderComboBox;
    }

    public Button getRetrieveButton() {
        return retrieveButton;
    }

    public TextArea getSearchResultsArea() {
        return searchResultsArea;
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
        backButton.setOnAction(e -> setDashboardContent());
        profileVBox.getChildren().add(backButton);
        return profileVBox;
    }

    public VBox getUpgradeView() {
        VBox upgradeVBox = new VBox(new Label("Upgrade Account View"));
        Button backButton = new Button("Back to Dashboard");
        backButton.setOnAction(e -> setDashboardContent());
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

    public ListView<Post> getPostsListView() {
        return postsListView;
    }

    public ImageView getSearchedPostImageView() {
        return searchedPostImageView;
    }

}
