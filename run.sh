#!/bin/bash

echo "Java Test Script written by Oisin s3952320"
echo "WARNING: Please ensure you are in the parent ass2_oisinAeonn directory."

# Replace this with the actual path to your JavaFX SDK's lib directory.
JAVAFX_PATH="lib/"

while true
do
    # Menu 
    echo
    echo "Please select an option:"
    echo "1. Delete all .class files" 
    echo "2. Build all .java files"
    echo "3. Run the program"
    echo "4. Run a J Unit Test"
    echo "5. Generate a JavaDoc"
    echo "6. Exit"
    
    read option

    # Switch case statement
    case $option in
        1)
            # Delete all .class files before running the program
            find . -name "*.class" -type f -delete
            echo "Successfully deleted all .class files!"
            echo
            ;;
        2)
            # Build all .java files for my project
            javac --module-path $JAVAFX_PATH --add-modules javafx.controls,javafx.fxml -cp .:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar ass2_oisinAeonn/*.java
            echo "Successfully built the project! Try to run!"
            echo
            ;;
        3)
            # Run my program
            java --module-path $JAVAFX_PATH --add-modules javafx.controls,javafx.fxml ass2_oisinAeonn.Main
            ;;
        4) 
            # Run a JUnit Test
            java -cp .:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore ass2_oisinAeonn.socialMediaAnalyzerAppTest
            ;;
        5)
            # Generate JavaDoc
            javadoc -d doc -classpath .:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:$JAVAFX_PATH/* $(find . -name "*.java")
            echo "JavaDoc successfully created!"
            ;;
        6)
            # Exit
            echo 
            echo "Thank you for using my easy Script! :)"
            exit 0
            ;;
        *)
            echo "Invalid option. Please select between 1 and 6."
            ;;
    esac
done
