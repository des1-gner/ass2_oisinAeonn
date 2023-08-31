#!/bin/bash

echo "Java Test Script written by Oisin s3952320" 

echo "WARNING: Please ensure you are in the parent ass1_oisinAeonn directory."

while true

do

    # Menu 
    
    echo

    echo "Please select an option:"
    
    echo "1. Reset posts.csv file"
    
    echo "2. Delete all .class files" 
    
    echo "3. Build all .java files"
    
    echo "4. Run the program"

    echo "5. Run a J Unit Test"

    echo "6. Generate a JavaDoc"
    
    echo "7. Exit"

    read option

    # Switch case statement
    
    case $option in
    
        1)
    
            # Reset posts.csv to quickly get a fresh version of the posts.csv file.
    
            cp "posts copy.csv" posts.csv
    
            echo "Successfully reset posts.csv!"

            echo

            ;;
    
        2)
    
            # Delete all .class files before running the program
    
            find . -name "*.class" -type f -delete
    
            echo "Successfully deleted all .class files!"

            echo

            ;;
    
        3)
    
            # Build all .java files for my project
    
            javac -cp .:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar ass1_oisinAeonn/*.java
    
            echo "Successfully built the project! Try to run!"

            echo

            ;;
    
        4)
    
            # Run my program
    
            java ass1_oisinAeonn.Main
    
            ;;

        5) 

            # Run a JUnit Test

            java -cp .:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore ass1_oisinAeonn.socialMediaAnalyzerAppTest

            ;;

        6)

            # Generate JavaDoc

            javadoc -d doc -classpath .:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar ass1_oisinAeonn/*.java

            echo "JavaDoc successfully created!"

            ;;
    
        7)
    
            # Exit
    
            echo 

            echo "Thank you for using my easy Script! :)"
    
            exit 0
    
            ;;
    
        *)
    
            echo "Invalid option. Please select between 1 and 5."
    
            ;;
    
    esac

done
