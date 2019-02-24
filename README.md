# Spring Blog

A relatively simple blog made with the popular Java framework - Spring.

### Features
* Ability to register and login
* 3 user roles
  * Guest - can only view posts
  * User - can comment on posts
  * Admin - can comment on posts and access the admin panel
* Admin panel with CRUD operations on category, post and comment entities
* Search and pagination

### Installation
1. Download this repository
2. Extract the archive
3. Create a MySQL database called **spring_blog** with collation *utf8mb4_unicode_ci*
4. Import the SQL file from the extracted archive
5. Import the project as a Maven project using Eclipse, IntelliJ or another Java IDE **(Eclipse is preferred)**
6. Open **application.properties** (located in the src/main/resources directory), and change the username and password to match your MySQL credentials
7. If using Eclipse, go to **Help -> Marketplace**, search for **Spring Tools 4**, install it and restart the IDE
8. Right click the project and select **Run as -> Spring Boot App**