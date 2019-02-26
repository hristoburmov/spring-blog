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
1. Download and extract [**STS (Spring Tool Suite)**](https://spring.io/tools3/sts/all)
2. Clone this repository
3. Create a MySQL database called **spring_blog** with collation *utf8mb4_unicode_ci*
4. Import the SQL file from the cloned project
5. Open STS, click on **File -> Import**, search for **Existing Maven Projects** and select the cloned project directory
6. Importing might take a while, so be patient and **wait for the green progress bar on the bottom right to disappear**
7. Expand the newly imported project, **unfold the src/main/resources directory** and open the **application.properties** file that's inside
8. Change the username and/or password to match your MySQL database credentials
9. Right click the project and select **Run As -> Spring Boot App**
10. Go to [**http://localhost:8080**](http://localhost:8080) to see the blog in action

### Users
* Admin access: username **admin**, password **asdf12345**
* Normal user: username **test**, password **test12345**