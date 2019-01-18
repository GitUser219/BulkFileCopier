# BulkFileCopier

A tool that is used to copy all files within a directory and all files within all subdirectories to a new location while renaming any files with duplicate names

### Prerequisites

A minimum [Java Runtime Environment](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) version of 1.8.0 is required to run BulkFileCopier.exe

### Built With

* [Java](https://www.java.com/en/) - a general-purpose computer-programming language that is concurrent, class-based, object-oriented, and specifically designed to have as few implementation dependencies as possible
* [Eclipse](https://www.eclipse.org) - An integrated development environment (the most widely used Java IDE)
* [Launch4j](http://launch4j.sourceforge.net/) - Cross-platform Java executable wrapper for creating lightweight Windows native executable files

## Development

The following instructions will help you to set up an environment for development and testing:

1. Download [BulkFileCopier-master.zip](https://github.com/GitUser219/BulkFileCopier/archive/master.zip) and extract all of the files to some location on your machine
2. Extract all of the files from Images.zip to some location on your machine
3. Ensure that you have installed a minimum [Java Runtime Environment](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) version of 1.8.0
4. Download and install [Eclipse IDE for Java Developers](https://www.eclipse.org/downloads/)
5. Run Eclipse and click File, New, Java Project
6. Name the project whatever you like, click Next, click Create new source folder, name it resources, and click Finish twice
7. Right click src, click New then Package, name it automation.filecopier, click Finish
8. Drag BulkFileCopier.java to the automation.filecopier package and click OK
9. Right click resources, click New then Package, name it images, click Finish
10. Drag all of the files from Images.zip, except for logo.ico, to the images package and click OK
11. The environment is now set up; right click BulkFileCopier.java (in Eclipse) and click Run As then Java Application to run the program
12. You can extract the files from Source.zip to test the program

## Deployment

If you make a change to the code and you would like to create a new executable file, follow these steps:

1. After running the program with no errors, right click on the project and click Export
2. Select Runnable JAR file in the Java folder and set the Launch configuration to BulkFileCopier - YOUR_PROJECT_NAME
3. Choose the destination for the JAR file, set the file name to BulkFileCopier.jar, click Save then Finish
4. You should now see BulkFileCopier.jar in the location that you chose
5. Install and run the [Launch4j Executable Wrapper](https://sourceforge.net/projects/launch4j/) tool
6. Make the following changes in the Basic tab of Launch4j
   - Output file: choose the file destination and set the file name to BulkFileCopier.exe
   - Jar: select the BulkFileCopier.jar file that was just created
   - Icon: select logo.ico from Images.zip
7. Make the following changes in the JRE tab of Launch4j
   - Min JRE version: 1.8.0
8. Click Build wrapper (gear icon in the upper left-hand corner)
9. Save a configuration file with any name and BulkFileCopier.exe will be created
10. You can delete BulkFileCopier.jar and the configuration file once the executable has been created
11. Run BulkFileCopier.exe to test that it is working properly

## Authors

* **Jonathan Nowak** - [GitUser219](https://github.com/GitUser219) - **Last Revised 1/17/19**
