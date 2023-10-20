Hi Dipto!

Demo Links:

Assessment 1 Demo:
https://rmiteduau-my.sharepoint.com/:v:/r/personal/s3952320_student_rmit_edu_au/Documents/Recordings/furtherProgrammingDemo1_OISIN-20230825_140220-Meeting%20Recording.mp4?csf=1&web=1&e=yk0eQm&nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJTdHJlYW1XZWJBcHAiLCJyZWZlcnJhbFZpZXciOiJTaGFyZURpYWxvZyIsInJlZmVycmFsQXBwUGxhdGZvcm0iOiJXZWIiLCJyZWZlcnJhbE1vZGUiOiJ2aWV3In19

Assessment 2 Demo:
https://rmiteduau-my.sharepoint.com/:v:/r/personal/s3952320_student_rmit_edu_au/Documents/Recordings/Ass2ComplementaryDemoOisin-20231020_175312-Meeting%20Recording.mp4?csf=1&web=1&e=9eA9aE&nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJTdHJlYW1XZWJBcHAiLCJyZWZlcnJhbFZpZXciOiJTaGFyZURpYWxvZyIsInJlZmVycmFsQXBwUGxhdGZvcm0iOiJXZWIiLCJyZWZlcnJhbE1vZGUiOiJ2aWV3In19

NOTE: This was not mandatory, but it demonstrates the JavaFX functionality without the need for installation. Feel free to look after the Interview in case you want to see something again. 

Code Standards & Documentation:

Adhered to Oracle's Java Programming Conventions:
https://www.oracle.com/java/technologies/javase/codeconventions-programmingpractices.html for more information. 

-Fixed the few bad conventions I used in Assessment 1 (PascalCase instead of camelCase).

-Properly Indented, and included comments where necessary.

JavaDoc has been generated for a further comprehensive documentation of my project.

UML Class Diagrams, use-case/user-story diagrams, and sequence diagrams are provided for deeper insights into code functionalities and interactions.

Core Functionality:

To-reiterate my code has the following functionalities described in Assessment 1:

• Add a post to the collection
• Remove a post from the collection based on the post ID
• Retrieve a post from the collection based on the post ID
• Retrieve the top N posts with the most likes, and show retrieved posts in descending order of #likes
• Retrieve the top N posts with the most shares, and show retrieved posts in descending order of #shares

As well as the new capabilities described in the brief for Assessment 2 (JavaFX Data Analytics Hub), and some extra:

• Database Connectivity using a singleton Database
• Login using a user in the Database
• Register a new user to the Database
• Upgrade user type to VIP
• All described features of Assessment 1 in JavaFX format
• Add Images to posts
• Export single, or multiple posts to csv
• Export a Collection to csv
• Administration to upgrade users to admin, delete, or change user type
• Ability to delete ones on account
• Visualisation of user type distribution (standard, VIP, and admin)
• Pie Chart Visualisation of the distribution of posts with 0-99, 100-999, and 1000+ likes or shares
• Ability to search for a post by username, and sort by postId, date, likes, or shares in ascending or descending order
• Ability to change your user account details, or add a profile picture
• Ability to Import posts from an external .csv
• As well as a few other things

I have written 2 Test Class (BasicDAOTest, and BasicTestFX) written using the J-Unit Framework, JAssert, and TestFX which covers around half of the major functionality in my code. As well as Tests some very basic JavaFX GUI Functionality.

All of my User Inputs have custom error exceptions which will give a custom alert error which all follow the same style using my stylesheet.

My Program should continue to run in case a user inputs an invalid value, and through my rigorous tests does not crash. 

As well as my reader for the .csv file (Imports) is also able to detect errors, and continue going. 

It will ignore any badly formatted lines when the Program reads these .csv files

This is because only objects that are read from the .csv, in addition to any new objects being added are written back to the .csv

Development Insights:

High Cohesiveness and Low Coupling: Achieving high cohesiveness and low coupling was paramount, ensuring clear, modular, and reusable code.

Transitioning to TableView: Adopted TableView in around half of my JavaFX based on Dipto's recommendation, offering enhanced data management functionalities.

Enhanced Features: Introduced features like user profile pictures, post images, and additional data analytics capabilities.

Styling in JavaFX: Ensured consistent and appealing styling across the application, overcoming initial challenges due to differences between JavaFX and Java Swing.

GUI Development: Chose to hardcode the GUI, deriving from prior experience with Java Swing. This offered granular control over the GUI components.

Agile Approach: Adopted agile's incremental development strategy. The progress can be traced via the GitHub repository where I have just under 100 commits: https://github.com/des1-gner/ass2_oisinAeonn

Comments & Annotations: Placed strategic comments throughout the codebase for clarity and better understanding.

Installation & Dependencies:

Basic Guide to Install a JavaFX Project:

Create a MySQL, or SQLite Database.

Use the SQL scripts to create the database, and tables required for my project. I.e. run CreateDatabase.sql, and DatabaseData.sql. 

Alter the DatabaseConnector class to connect to the new Database. I.e. update the connection point, username, and password. 

Open preferred IDE, cloning or download the project repository. https://github.com/des1-gner/ass2_oisinAeonn

Ensure the relevant version of Java and JavaFX SDK is installed. 

I used: 

openjdk 21 2023-09-19
OpenJDK Runtime Environment (build 21+35)
OpenJDK 64-Bit Server VM (build 21+35, mixed mode, sharing)

Add these Dependencies as external jars:

JUnit 4.13.2
https://github.com/junit-team/junit4/wiki/Download-and-Install

JavaFX Linux 21.0.1:
https://gluonhq.com/products/javafx/

MySQL Connector 8.0.23
https://www.codejava.net/java-se/jdbc/jdbc-driver-library-download

TestFx 4.0.17
https://jar-download.com/artifacts/org.testfx

AssertJ 3.13.2
https://jar-download.com/artifacts/org.testfx

These are all backwards compatible, however should you encounter an issue with a newer version use the version specified. 

Navigate to the project directory and compile the Java files. You can use my easy scripts. 

Run the main application file to start the program.

Here is a guide to running my Program outside an IDE via the command-line. I have included instructions for both Linux (my everyday platform), and Windows. 

WARNING: You will need to be in the correct directory in order for these commands to work. You should be in the parents directory of ass2_oisinAeonn. 

NOTE: If you are a Mac User you can switch to the bash terminal via running the following command: 

-s /bin/bash

Or the Bash commands can be easily adapted to serve Mac's Z Shell. 

----------------------------------------------------------------------------------------------------------------------------------------------------------------------

Linux:

Run the following command to use my Java runner script which helps test my Program easily:

bash run.sh 

-

Reset posts.csv to quickly get a fresh version of the posts.csv file:
    
cp "posts copy.csv" posts.csv

-    

Delete all .class files:

find . -name "*.class" -type f -delete

-

Build all .java files for my project:

javac -cp .:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar ass2_oisinAeonn/*.java

-

Run my program:

java ass2_oisinAeonn.Main 

-

Run a JUnit Test:

java -cp .:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore ass2_oisinAeonn.socialMediaAnalyzerAppTest

-

Generate JavaDoc:

javadoc -d doc -classpath .:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar ass2_oisinAeonn/*.java

-

----------------------------------------------------------------------------------------------------------------------------------------------------------------------

Windows: 

Reset posts.csv to quickly get a fresh version of the posts.csv file:

copy /Y "posts copy.csv" posts.csv

-

Delete all .class files:

for /r . %G in (*.class) do del /S /Q "%G"

-

Build all .java files for my project:

javac -cp .;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar ass2_oisinAeonn\*.java

-

Run my program:

java ass2_oisinAeonn.Main

-

Run a JUnit Test:

java -cp .;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar org.junit.runner.JUnitCore ass2_oisinAeonn.socialMediaAnalyzerAppTest


-

Generate JavaDoc:

javadoc -d doc -classpath .:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar ass2_oisinAeonn/*.java

-

----------------------------------------------------------------------------------------------------------------------------------------------------------------------

Design Patterns & Data Structures:

Singleton Database Design Pattern: The Singleton pattern was employed for efficient and centralized database management. This design pattern ensured a single point of access to the database, enhancing the application's performance and reliability. By adopting this pattern, I managed to segment the complex database logic into three components: The Connector, and two distinct DAOs (Data Access Objects).

Model-View-Controller (MVC): To ensure a clear division between the application's user interface, data, and control logic, the MVC design pattern was implemented. The separation into Model (for entities like User and Post), View (the UI components), and Controllers ensured high cohesiveness and low coupling, making the code more modular and maintainable.

These also ensured I could package my different classes which further improved maintainability. 

Object-Oriented Programming (OOP):

Abstraction: Leveraged class structures to abstract and represent real-world entities like posts and users. The Post class, in particular, stands out as a clear instance of abstraction, defining the essential attributes and behaviors of a post.

Encapsulation: By importing specific packages and segregating functionalities into methods and classes, the complexity of the code was significantly reduced. The Post class exemplifies encapsulation, where data members and methods are bundled together, and access to them is restricted for protection.

Inheritance: Some classes shared common attributes and behaviors. Through inheritance, these commonalities were factored out into parent classes, promoting code reusability and hierarchy.

Polymorphism: Method overloading was employed, especially to facilitate unit testing, allowing methods to operate differently based on the inputs they receive.

Java Collections Framework (JCF): Utilized various data structures from the JCF, such as ArrayLists, Lists, and LinkedHashMaps. These structures streamlined data handling and management in the application.

SQL with MariaDB: SQL was used as the primary means of interacting with the MariaDB database. The decision to employ MariaDB, a robust production database, ensured swift and efficient data operations, especially when executing complex SQL queries.

LinkedHashMaps were no longer required as SQL maintained the order of my Posts, and Users. 

Encapsulation with Package Structuring: By structuring the application into packages, access control was effectively managed. Most variables were kept private, but inheritance necessitated some variables to be declared as protected or public.

In my code, I've made use of various data structures, SOLID principles, and design patterns:

Data Structures:

Lists (e.g., ArrayList): I utilized lists to aggregate and return sets of data, such as when fetching all users or posts.

Maps (e.g., HashMap): I employed maps for representing distributions in my UserDAO and PostDAO classes.

SOLID Principles:

Single Responsibility Principle (SRP):

I designed each class to have a specific, singular responsibility:

UpgradeView solely manages the upgrade screen.
VIPView is a specialized extension of the dashboard with VIP features.
UserDAO exclusively handles database operations related to users.
PostDAO is dedicated to database operations related to posts.

Open/Closed Principle (OCP):

I made an effort to uphold this principle by using inheritance. For instance, VIPView extends DashboardView, which allows for the extension of features without modifying existing behavior.
Liskov Substitution Principle (LSP):

I did not directly implement Interface Segregation Principle (ISP) or Dependency Inversion Principle (VIP). 
However I ensured as best as possible that the subclasses I created were substitutable for their base classes, and only implemented what they required to function.

I used the Singleton pattern with DatabaseConnector to ensure a level of abstraction, hinting at the application of DIP.

Structures / Patterns:

Singleton Pattern:

Both in UserDAO and PostDAO, I implemented the Singleton pattern for database connections using DatabaseConnector.getInstance(). This ensures a single instance of the database connection throughout my application.
Factory Method Pattern (potentially):

I've leveraged the getInstance() methods in a way that suggests the Factory Method pattern. However, in the context of my code, it primarily serves the Singleton pattern.
Inheritance/Extension:

By making VIPView a subclass of DashboardView, I showcased the use of inheritance and created a space for feature extension and specialization.
Data Access Object (DAO) Pattern:

I incorporated the DAO pattern in both UserDAO and PostDAO, providing a clear and abstract interface to the database for users and posts.
Model-View Separation:

I emphasized a separation of the view (GUI components) from the underlying data models, as seen in classes like UpgradeView and VIPView.

These all are apart of the MVC Pattern (Model, View, Controller).

Challenges Faced During Development:

1. Data Structure Decision: The selection of an appropriate data structure was pivotal. I initially considered various options but settled on LinkedHashMap. Its unique feature of preserving insertion order was crucial, especially when working with .csv files, to ensure consistency and reliability.

2. DateTime Formatting: A significant hiccup I faced was with the default Date-Time formatting. The presence of a 'T' character in the format was inconsistent with the expected format in the .csv entries. Adapting to this and ensuring uniformity across all entries required meticulous attention.

3. J-Unit Learning Curve: Embarking on the journey of implementing J-Unit test cases was initially daunting. Having no prior experience with explicitly running these tests, I had to climb a steep learning curve, understanding the intricacies of the framework from scratch.

4. IDE Project Recognition Issues: On several occasions, my IDE failed to recognize my project as a Java-based project. This unexpected hiccup barred me from running tests and debugging my code, demanding alternative solutions and workarounds.

5. Transition from Swing to JavaFX: Migrating from Swing, which I was previously acquainted with, to JavaFX presented its own set of challenges. The nuances of JavaFX required an adjustment period, especially when dealing with its diverse components and features.

6. Database Access Object (DAO) Implementation: Incorporating the DAO in the database was a crucial step but not without its hurdles. Ensuring smooth data interactions and operations, while keeping the code efficient, posed certain challenges.

7. Code Modularity and Cleanliness: One of the key objectives was to maintain small, purposeful methods. As the project grew in complexity, ensuring that each method remained concise and that the code stayed clean became increasingly challenging.

8. Design Pattern Implementations: Incorporating design patterns like MVC and Singleton, while beneficial, required a deep understanding and careful implementation to ensure they seamlessly integrated with the project's architecture.

9. Exception Handling and Alerts: Properly catching and handling exceptions was essential for the application's robustness. Moreover, designing meaningful alert messages to inform the user about various events or errors was crucial for a good user experience.

10. Styling with JavaFX: Implementing the styles.css in JavaFX proved to be more challenging than anticipated. The intricate styling details demanded precision and a deep understanding of the JavaFX styling paradigm.

11. JUnit with GUI: Combining JUnit testing with the GUI components of JavaFX introduced a new layer of complexity. Ensuring that the tests were both comprehensive and reliable, especially when testing GUI components, was a unique challenge.

12. I was able to fix all of them, however some took me several hours each of work, and research to solve. 

Assumptions are commented in my code. 

Conclusion:

Developing this JavaFX application was a journey of continuous learning, overcoming challenges, and achieving milestones. Every phase, be it the shift from Swing to JavaFX, feature integrations, or addressing various challenges, enriched the development experience.

Contact Information:

Name: Oisin Sol Emlyn Aeonn
Phone: +61 434 265 853
M.S. Teams: s3952320@student.rmit.edu.au
Email: s3952320@student.rmit.edu.au or oisin.aeonn@outlook.com

References & Resources:

https://www.flaticon.com/free-icons/profile

DALE-3 for generating some logos

Web Programming COSC2446 for the general Database structure

Used CodeWhisperer, and VSCode Auto to generate getters / setters and other basic Java Broiler Plate.

Dipto - Milestone 1, and Canvas Discussions

Thank you for running and testing my Program!

-Oisin s3952320