# Fetch Coding Exercise - SDET

This project involves the utilization of Java with Selenium for executing various tasks and scenarios specified in the Fetch Rewards SDET Coding Exercise.

- Link to the task can be found [here](https://fetch-hiring.s3.amazonaws.com/SDET/Fetch_Coding_Exercise_SDET.pdf).

## Tech Stack:

- **Programming Language**: Java
- **Build Tool**: Maven
- **Version Control**: GitHub

## Getting Started

### Dependencies

- **selnium**: used to interact with web browsers and applications.
- **webdrivermanager**:  Automated driver management and other helper features for Selenium WebDriver in Java.
- **testng**: tool to simplify the testing.

### Set up

- Make sure you have [java](https://www.java.com/en/download/help/download_options.html) and [maven](https://maven.apache.org/install.html) running on your machine.

- Run ```mvn -v``` in terminal and confirm maven is installed.

##  Running the script:
- Clone this repo, and in the root folder run the following:

```
 mvn install
```

- Now all the dependencies are installed.
- To compile and run the scipt, run the following:

```
mvn compile
```

```
mvn exec:java
```

- You will see output similar to beow output, once it is started

```
------------------------
Game Page Opened
Test Started
Test Completed
------------------------
Alert message is Yay! You find it! and expected is Yay! You find it!
Weighings list:
Weighing 1: [0,1,2,3] > [4,5,6,7]
Weighing 2: [7,6] < [4,5]
Weighing 3: [7] < [6]
Number of weighings: 3
Fakebar Number: 7
------------------------
------------------------
```
