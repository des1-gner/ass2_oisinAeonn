package ass2_oisinAeonn.Views;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

import ass2_oisinAeonn.Model.Post;

// Represents the primary dashboard view for users to interact with the Data Analytics Hub application

public class DashboardView {

    private VBox dashboardVBox;
    private HBox topHBox;
    private Label welcomeLabel;
    protected TabPane tabPane;
    private MenuButton menuButton;
    private TextArea searchResultsArea;
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
    private TextField usernameFilterField;
    private int likes;
    private int shares;
    private ListView<Post> postsListView;
    private ImageView searchedPostImageView;
    private Button exportSearchedPostButton;
    protected Tab allTab;
    protected TableView<Post> allPostsTableView;
    private Button exportSelectedPostsButton;

    // Constructor initializing the dashboard view with a given username

    public DashboardView(String username) {
        
        searchedPostImageView = new ImageView();
        searchedPostImageView.setFitHeight(300);
        searchedPostImageView.setFitWidth(300);
        searchedPostImageView.setPreserveRatio(true);
        dashboardVBox = new VBox(10);
        dashboardVBox.setPadding(new Insets(20));

        Image logo = new Image(getClass().getResource("../../assets/logo.jpg").toString());
        ImageView logoView = new ImageView(logo);
        Label titleLabel = new Label("Data Analytics Hub made by Oisin Aeonn s3952320");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        logoView.setFitWidth(50);
        logoView.setPreserveRatio(true);

        HBox logoBox = new HBox(10, logoView, titleLabel);
        logoBox.setAlignment(Pos.TOP_LEFT);
        logoBox.setPadding(new Insets(0, 0, 0, 0));

        dashboardVBox.getChildren().add(0, logoBox);

        postContentField = new TextField();
        postButton = new Button("Post");
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

        VBox addPostVBox = new VBox(10, postContentField, likesLabel, likesField, sharesLabel, sharesField, datePicker, uploadImageButton, postImageView, postButton);
        addPostVBox.setPadding(new Insets(15));

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
        exportSearchedPostButton = new Button("Export Searched Post to CSV");
        
        VBox searchVBox = new VBox(10, searchField, deleteButton, retrieveButton, exportSearchedPostButton, searchedPostImageView, searchResultsArea);
        Tab searchTab = new Tab("Search", searchVBox);
        searchTab.setClosable(false);

        Tab trendingTab = new Tab("Trending");
        trendingTab.setClosable(false);
        tabPane.getTabs().addAll(addPostTab, searchTab, trendingTab);
        dashboardVBox.getChildren().addAll(topHBox, tabPane);

        postImageView.setFitHeight(300);
        postImageView.setFitWidth(300);
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
        usernameFilterField = new TextField();
        usernameFilterField.setPromptText("Filter by Username (optional)");
        usernameFilterField.setPrefWidth(200);  // Example width; adjust as needed
        retrieveCountField = new TextField();
        retrieveCountField.setPromptText("Number of posts (up to 100)");
        retrieveCountField.setPrefWidth(200);    // Example width; adjust as needed
        sortOrderComboBox = new ComboBox<>();
        sortOrderComboBox.setItems(FXCollections.observableArrayList("Ascending", "Descending"));
        sortOrderComboBox.setValue("Descending");
        controls.getChildren().addAll(filterButton, usernameFilterField, postIdRadio, dateRadio, likesRadio, sharesRadio, retrieveCountField, sortOrderComboBox);
        postsListView = new ListView<>();
        
        setupPostsListView();
        
        layout.getChildren().addAll(controls, postsListView);
        trendingTab.setContent(layout);
        
        for (Node node : controls.getChildren()) {
        
            HBox.setMargin(node, new Insets(0, 5, 0, 5));  // Add margin of 5 pixels to left and right of each control
        
        }
        
        allTab = new Tab("All");
        allTab.setClosable(false);

        allPostsTableView = new TableView<>();
        setupallPostsTableView();
        exportSelectedPostsButton = new Button("Export Selected Posts");

        VBox allTabLayout = new VBox(10, allPostsTableView, exportSelectedPostsButton);
        allTab.setContent(allTabLayout);

        tabPane.getTabs().add(allTab);

    }

    // Sets up the posts list view

    private void setupPostsListView() {
    
        postsListView.setCellFactory(postsListView -> new ListCell<Post>() {
    
            @Override
    
            protected void updateItem(Post post, boolean empty) {
    
                super.updateItem(post, empty);
    
                if (post == null || empty) {
    
                    setText(null);
    
                } 
                
                else {
                
                    String displayText = "Post ID: " + post.getPostId() + "\n" + 
                
                    "Author: " + post.getAuthor() + 
                    "\nContent: " + post.getContent() +
                    "\nLikes: " + post.getLikes() +
                    "\nShares: " + post.getShares() + 
                    "\nDateTime: " + post.getDateTime();
                    setText(displayText);
                
                }
            
            }
        
        });
    }

    // Sets up the all posts table view
    
    private void setupallPostsTableView() {
    
        TableColumn<Post, Integer> postIdColumn = new TableColumn<>("Post ID");
    
        postIdColumn.setCellValueFactory(new PropertyValueFactory<>("postId"));
        postIdColumn.setMaxWidth(Double.MAX_VALUE); 
        postIdColumn.prefWidthProperty().bind(allPostsTableView.widthProperty().multiply(0.1428)); 
        
        TableColumn<Post, String> contentColumn = new TableColumn<>("Content");
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        contentColumn.setMaxWidth(Double.MAX_VALUE); 
        contentColumn.prefWidthProperty().bind(allPostsTableView.widthProperty().multiply(0.1428)); 
        
        TableColumn<Post, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorColumn.setMaxWidth(Double.MAX_VALUE); 
        authorColumn.prefWidthProperty().bind(allPostsTableView.widthProperty().multiply(0.1428)); 
        
        TableColumn<Post, Integer> likesColumn = new TableColumn<>("Likes");
        likesColumn.setCellValueFactory(new PropertyValueFactory<>("likes"));
        likesColumn.setMaxWidth(Double.MAX_VALUE); 
        likesColumn.prefWidthProperty().bind(allPostsTableView.widthProperty().multiply(0.1428)); 
        
        TableColumn<Post, Integer> sharesColumn = new TableColumn<>("Shares");
        sharesColumn.setCellValueFactory(new PropertyValueFactory<>("shares"));
        sharesColumn.setMaxWidth(Double.MAX_VALUE); 
        sharesColumn.prefWidthProperty().bind(allPostsTableView.widthProperty().multiply(0.1428)); 
        
        TableColumn<Post, String> dateTimeColumn = new TableColumn<>("Date & Time");
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        dateTimeColumn.setMaxWidth(Double.MAX_VALUE); 
        dateTimeColumn.prefWidthProperty().bind(allPostsTableView.widthProperty().multiply(0.1428)); 
        
        TableColumn<Post, String> imageColumn = new TableColumn<>("Image");
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        imageColumn.setMaxWidth(Double.MAX_VALUE); 
        imageColumn.prefWidthProperty().bind(allPostsTableView.widthProperty().multiply(0.1428)); 
        
        allPostsTableView.getColumns().addAll(postIdColumn, contentColumn, authorColumn, likesColumn, sharesColumn, dateTimeColumn, imageColumn);

    }

    // Populates the all posts table with a list of posts


    public void populateAllPosts(List<Post> posts) {
    
        allPostsTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    // Validates whether the content is present and not empty
    
    public boolean isContentValid() {
    
        String content = postContentField.getText();
    
        return content != null && !content.trim().isEmpty();
    
    }

    // Validates whether the likes input is valid
    
    public boolean isLikesValid() {
    
        String likesStr = likesField.getText();
    
        try {
    
            int likes = Integer.parseInt(likesStr);
    
            return likes >= 0;
    
        } 
        
        catch (NumberFormatException e) {
        
            return false;
        
        }
    
    }

    // Validates whether the shares input is valid
    
    public boolean isSharesValid() {
    
        String sharesStr = sharesField.getText();
    
        try {
    
            int shares = Integer.parseInt(sharesStr);
    
            return shares >= 0;
    
        } 
        
        catch (NumberFormatException e) {
        
            return false;
        
        }
    
    }

    // Validates whether the dates input is valid
    
    public boolean isDateValid() {
    
        return datePicker.getValue() != null;
    
    }
    
    // Validates whether an image has been uploaded

    public boolean isImageUploaded() {
    
        return postImageView.getImage() != null;
    
    }

    // Opens a File Chooser dialog to allow the user to select an image

    public File showImageFileChooser() {
    
        FileChooser fileChooser = new FileChooser();
    
        fileChooser.setTitle("Select an Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp"));
    
        return fileChooser.showOpenDialog(null);
    
    }

    // Constructs and returns the Profile View

    public VBox getProfileView() {
    
        VBox profileVBox = new VBox(new Label("Profile View"));
        Button backButton = new Button("Back to Dashboard");
    
        backButton.setOnAction(e -> setDashboardContent());
        profileVBox.getChildren().add(backButton);
    
        return profileVBox;
    
    }

    // Constructs and returns the Upgrade Account View

    public VBox getUpgradeView() {
    
        VBox upgradeVBox = new VBox(new Label("Upgrade Account View"));
        Button backButton = new Button("Back to Dashboard");
    
        backButton.setOnAction(e -> setDashboardContent());
        upgradeVBox.getChildren().add(backButton);
    
        return upgradeVBox;
    
    }

    // Sets the scene's root to the main dashboard

    private void setDashboardContent() {
    
        getPane().getScene().setRoot(dashboardVBox);
    
    }

    // Getters and setters for various properties

    public TableView<Post> getAllPostsTableView() {

        return allPostsTableView;

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

    public Button getExportSearchedPostButton() {

        return exportSearchedPostButton;

    }

    public ToggleGroup getRadioGroup() {

        return radioGroup;
    }


    public RadioButton getDateRadio() {
    
        return dateRadio;
    
    }

    public TextField getUsernameFilterField() {
    
        return usernameFilterField;
    
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

    public TabPane getTabPane() {
    
        return tabPane;
    
    }

    public ButtonBase getExportSelectedPostsButton() {
    
        return exportSelectedPostsButton;
    
    }

}