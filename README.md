# The project name
### **Student check-in system based on student management system**

## Content

1. Project introduction
2. The project uses renderings
3. Installation instructions
4. Directions for use
5. The directory structure
6. Database design version
7. About the author
8. Update log


## Project introduction
### Project positioning
	It is used to manage student information and 
	realize student check-in function of online course. 
	The goal is to help online course teacher confirm 
	student attendance rate by sending sign-in request.
### Project characteristics
	Fully functional, can simultaneously achieve 
	attendance and student management functions. 
	Permission management is strict for different user types.
### Software functions
	1. Login with different user types
	2. Sign in message prompt
	3. Course information prompt
	4. Student information management
	5. Teacher information management
	6. Class information management
	7. Course information management
	8. Management of student course selection
	9. Check-in management
	10. Check attendance records

	Realized fuzzy search and real-time data update
### Version information
	There are five versions, and information about 
	each version can be found in the wiki
	
URL: [https://csgitlab.ucd.ie/19206223/software-engproject-group9/-/wikis/Historical-Editions-Summary](https://csgitlab.ucd.ie/19206223/software-engproject-group9/-/wikis/Historical-Editions-Summary)


## Rendering
Version1:
---
URL: [https://csgitlab.ucd.ie/19206223/software-engproject-group9/-/wikis/Wiki-Sprint1](https://csgitlab.ucd.ie/19206223/software-engproject-group9/-/wikis/Wiki-Sprint1)

Version2:
---
URL: [https://csgitlab.ucd.ie/19206223/software-engproject-group9/-/wikis/Wiki-Sprint2](https://csgitlab.ucd.ie/19206223/software-engproject-group9/-/wikis/Wiki-Sprint1)
Version3:
---
URL: [https://csgitlab.ucd.ie/19206223/software-engproject-group9/-/wikis/Wiki-Sprint3](https://csgitlab.ucd.ie/19206223/software-engproject-group9/-/wikis/Wiki-Sprint1)
Version4:
---
URL: [https://csgitlab.ucd.ie/19206223/software-engproject-group9/-/wikis/Wiki-Sprint4](https://csgitlab.ucd.ie/19206223/software-engproject-group9/-/wikis/Wiki-Sprint1)
Version5:
---
URL: [https://csgitlab.ucd.ie/19206223/software-engproject-group9/-/wikis/Wiki-Sprint5](https://csgitlab.ucd.ie/19206223/software-engproject-group9/-/wikis/Wiki-Sprint1)

## Installation instructions
### Basic environmental dependence
	Java13
	HTML4.01 Transitional
	CSS3
	JavaScript1.8.5
	mysql8.0.22
	tomcat
	
### Third-party packages
	H-ui 1.0
	easyui 1.0
	Hui-iconfon 1.0.1
	jquery 1.9.1

### The deployment of installation
	Install the Java 8+ version
	Install Tomcat7+ version
	Install mysql8.0.22



## Directions for use
### Brief description
	1. You need to manually import database resources 
	into the local database
	
	2. You need to match the database manually

	3. You need to deploy the Tomcat server locally
	
### Specify description
	1. The db_stu_web.sql file can be found in the following 
	directory structure. Transfer it to the local database
	
	2. The database account password needs to be configured 
	in the db.properties file. 
	This file can also be found in the directory structure
	
	3. You need to configure our project to the Tomcat server


## The directory structure
```
├── Readme.md						// help
├── Stuweb                      	// project                
│   ├── build						// class files
│   ├── src                		    // java files
│   │  ├── dao 						// database connection
│   │  ├── filter					// filter
│   │  ├── model					// pojo classes
│   │  ├── servlet					// servlets
│   │  ├── util						// utils
│   ├── WebContent         			// web files
│   ├── Wiki Images          	    // wiki image
│   ├── Informations           	    // resources
│   ├── db_stu_web.sql              // db file

```


## Database relationship version
URL: [https://csgitlab.ucd.ie/19206223/software-engproject-group9/-/wikis/Database-Relationship]()

## About the author
	Jinfeng Xu
	Ziyang Chen
	Zhuoyu Wu
	Xuan Wu


## Update log

URL: [https://csgitlab.ucd.ie/19206223/software-engproject-group9/-/wikis/Historical-Editions-Summary](https://csgitlab.ucd.ie/19206223/software-engproject-group9/-/wikis/Historical-Editions-Summary)

### README Author: Jinfeng Xu

