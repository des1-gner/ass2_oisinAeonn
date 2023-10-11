Write-Host "Java Test Script written by Oisin s3952320"
Write-Host "WARNING: Please ensure you are in the parent ass2_oisinAeonn directory."

# Replace this with the actual path to your JavaFX SDK's lib directory.
$JAVAFX_PATH = "lib/"

while ($true) {
    # Menu 
    Write-Host "`nPlease select an option:"
    Write-Host "1. Delete all .class files"
    Write-Host "2. Build all .java files"
    Write-Host "3. Run the program"
    Write-Host "4. Run a J Unit Test"
    Write-Host "5. Generate a JavaDoc"
    Write-Host "6. Exit"
    
    $option = Read-Host

    # Switch statement
    switch ($option) {
        1 {
            # Delete all .class files before running the program
            Get-ChildItem -Recurse -Filter "*.class" | ForEach-Object { Remove-Item $_.FullName }
            Write-Host "Successfully deleted all .class files!"
        }
        2 {
            # Build all .java files for my project
            & javac --module-path $JAVAFX_PATH --add-modules javafx.controls,javafx.fxml -cp ".;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" ass2_oisinAeonn/*.java
            Write-Host "Successfully built the project! Try to run!"
        }
        3 {
            # Run my program
            & java --module-path $JAVAFX_PATH --add-modules javafx.controls,javafx.fxml ass2_oisinAeonn.Main
        }
        4 {
            # Run a JUnit Test
            & java -cp ".;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore ass2_oisinAeonn.socialMediaAnalyzerAppTest
        }
        5 {
            # Generate JavaDoc
            & javadoc -d doc -classpath ".;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar;$JAVAFX_PATH/*" $(Get-ChildItem -Recurse -Filter "*.java" | ForEach-Object { $_.FullName })
            Write-Host "JavaDoc successfully created!"
        }
        6 {
            # Exit
            Write-Host "`nThank you for using my easy Script! :)"
            exit
        }
        default {
            Write-Host "Invalid option. Please select between 1 and 6."
        }
    }
}
