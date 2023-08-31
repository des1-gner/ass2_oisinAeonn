Link to demo: https://rmiteduau-my.sharepoint.com/:v:/r/personal/s3952320_student_rmit_edu_au/Documents/Recordings/furtherProgrammingDemo1_OISIN-20230825_140220-Meeting%20Recording.mp4?csf=1&web=1&e=yk0eQm&nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJTdHJlYW1XZWJBcHAiLCJyZWZlcnJhbFZpZXciOiJTaGFyZURpYWxvZyIsInJlZmVycmFsQXBwUGxhdGZvcm0iOiJXZWIiLCJyZWZlcnJhbE1vZGUiOiJ2aWV3In19

I have followed Oracle's Java Programming Conventions; indenting, and properly spacing my code to make it beautiful and easy to read.

Please refer to https://www.oracle.com/java/technologies/javase/codeconventions-programmingpractices.html for more information. 

I have generated a JavaDoc to accompany this Project to serve as further documentation. 

I have also included a UML Class Diagram which I created to show all of the parts of my code, and how they interact. 

My code should meet all of the criterian described in the brief, and rubric.

To-reiterate my code has the following functionalities:

• Add a post to the collection
• Remove a post from the collection based on the post ID
• Retrieve a post from the collection based on the post ID
• Retrieve the top N posts with the most likes, and show retrieved posts in descending order of #likes
• Retrieve the top N posts with the most shares, and show retrieved posts in descending order of #shares

As well as has a Test Class written using the J-Unit Framework which covers most of the above functionalities in my code. 

All of my User Inputs have custom error exceptions which will give a custom error, and continue to run in case a user inputs an invalid value. 

As well as my reader for the .csv file is also able to detect errors, and continue going. It will also delete any rubbish, or badly formatted lines when the Program writes to the .csv
This is because only objects that are read from the .csv, in addition to any new objects being added are written back to the .csv
It also makes deleting easy, as the LinkedHashMap order can be easily reorganised unlike some other Data Structures that would be inconsistent. 

My specific design choices were to use OOP; I included Method Overloading to handle Unit Testing, and also did: 

Abstraction: I did this through the seperation, and use of Classes in my code; 

I also imported many packages which I used throughout my code which helped add additional functionality without me having to code it from scratch, or paste it explicitly into my code; this significantly reduces the complexity. 
My Post Class is the most notable form of Abstraction, and Encapsulation in my Project; as you can see the definition of different data types, and levels of protection for methods which change how my program functions. 

JCF: 
In my code I implemented the Serializable Interface, as well as utilized 2 different Data Structures: ArrayList, and LinkedHashMap. 
While not a complicated algorithm, my program also implemented algorithmic searching to sort the Data Structures to filter based on number of likes, or shares. 

I choice the LinkedHashMap because it offers:
Insertion Order: maintains the insertion order of entries, meaning when you iterate over a LinkedHashMap, entries are returned in the order they were put into the map. 
I tried to just use a normal HashMap, however this gave me some errors such as anytime I did anything the .csv would be written in a completely different order which wasn't very consistent. 

Some things I struggled with were:

1. Choice of data-structure - LinkedHashMap

2. The formatting for the Date-Time as the default format has a 'T' inbetween which was different to the format in the other .csv entries.

3. J-Unit: I had never run explicit test cases, and these I had to learn from scratch. 

4. Java Projects: My Project kept not being recognized by my IDE as a Java Project, and so it wouldn't allow me to run tests, or debug my code. 

I was able to fix all of them, however some took me several hours of work, and research to solve. 

Assumptions are commented in my code. 

----------------------------------------------------------------------------------------------------------------------------------------------------------------------

Please contact me if you are unable to run my Program:

Contact:

Name: Oisin Sol Emlyn Aeonn

Ph: +61 434 265 853

M.S. Teams @ s3952320@student.rmit.edu.au (usual response time 5 minutes: 7 - 11 x 7)

Email: s3952320@student.rmit.edu.au, or oisin.aeonn@outlook.com

_____________________________________________________________________________________________________________________________________________________________________

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

Thank you for running, and testing my Program! -Oisin s3952320

References:

2023 https://www.oracle.com/java/technologies/javase/codeconventions-programmingpractices.html

2023 https://www.draw.io

2020 - 2023 https://www.youtube.com/@CodingWithJohn Coding with John 

2023 RMIT University - Canvas, and Course: 
    -Weeks 1 - 5 Further Programming COSC2391
    -Apply introductory object-oriented language skills (ICTPRG430) COSC7391C
    -Apply intermediate object-oriented language skills (ICTPRG549) COSC7394C
    -Programming 1 COSC2395

2023 March https://www.linkedin.com/learning/certificates/16b99e7b85b2c311f63c7133e20b7fec6eb27afa445aed181428ff153ba8a36c?u=2104756 Kathryn Hodge 





