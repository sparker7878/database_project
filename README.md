# Using Heroku to deploy servlets and JSPs

This tutorial will show you how to deploy and develop a Heroku app that runs servlets and JSPs.
There are two activities covered in this tutorial: deployment will cover how to obtain a remote repository, making a copy of it in your own repository, persisting changes from your local instance to your remote one in GitHub and automatically deploy your app in Heroku, and development will cover how to build and run your app locally.

Check the currently deployed version: [https://swe432tomcat.herokuapp.com](https://swe432tomcat.herokuapp.com)

## Prelude
We are working with two different phases of the software lifecycle: **development**(programming, debugging, program testing) and **deployment**(publishing your app in your servers so clients can use it).  Heroku offers a hosting service for your web app and can be linked with GitHub to auto-deploy it, and also offers development tools so you can run your app locally.

**Disclaimer:** Please take a moment to explore each concept, technology, command, activity and action used in this tutorial, for brevity the level of detail is limited to help you deploy servlets in Heroku. 

## Quick Reference
Use these terminal commands only if you already followed this tutorial and want a quick reminder of common tasks:

### Redeploying the app by pushing changes to the remote repo
```ShellSession
git a . & git c -m "TODO: I really should explain these changes" & git push
```

### Rerunning the app locally after some edits
```ShellSession
mvn package & heroku local
```

## Create GitHub and Heroku accounts

You can create accounts for free in both platforms (do not provide any payment info).

### GitHub

Go to [Github](https://github.com/) and create your account.

### Heroku

Go to [Heroku](https://signup.heroku.com) to create your account.

Optional: You can use the GitHub student package found [here](https://www.heroku.com/github-students). 

## Deployment: Create a Git repo(sitory) and and link it to a Heroku app 

If you have not installed Git before, you can get it [here](https://git-scm.com/downloads).

Now, follow these steps to bring this repo into your Github account:

### 1. Get this repo locally in your machine:
This code contains all necessary boilerplate for supporting servlets and JSPs in a Heroku app, in a terminal:
```ShellSession
git clone https://github.com/luminaxster/swe432tomcat.git
```

### 2. Create an empty repo in your Github:

a. Go to [Github](www.github.com), login into your account, select the "repositories" tab, click on "New". Once in the "Create a new repository" page: give a name to your repo, make it private and let other options default, and click on "create repository".

c. This will take you to your new repository's page. Copy the url to access your repo in the quick setup section.
It should look like this:

```HTTP
https://github.com/<your_username>/<newly_created_repo_name>.git
```

We will use this URL in step 3.

### 3. Redirect the local repo to your own repo and save the changes:

Remember to replace the url from step 2 ( `https://github.com/<your_username>/<newly_created_repo_name>.git` ) with your own repo's url.

#### a. Setting up your remote repo in Git
The following commands **only need have to be used once**, in a terminal:
```ShellSession
cd swe432tomcat
git init
git remote set-url origin "https://github.com/<your_username>/<newly_created_repo_name>.git"
```
#### b. Push your local changes into your remote repo
The following commands **will be reused every time you want to send your changes to GitHub**, in a terminal:
```ShellSession
git add .
git commit -m "Initial commit: cloned repo"
git push
```
Remember to be explicit with your commit messages, this will document the rationale of your code changes and be seen in each file history by everyone wanting to contribute to your project, or even you after a month not looking at the file.
### 4. Create a Heroku app

Go to your [Heroku dashboard](https://dashboard.heroku.com/apps): click on "New" > "Create New App", provide a name, and click on "create app".

### 5. Link repo and deploy 

Once in your Heroku app web page, select the "deploy" tab:

 a. set the deploy method to "Github"
 
 b. authorize Heroku to access your GitHub repositories
 
 c. select the recently created one
 
 d. click on "connect"
 
 e. activate automatic deploys
 
 f. click on deploy the `master` branch (only this time so you can see the changes immediately)
 
 g. Once your deploy is processed, click on "View"
 
### Updating your repo and redeploying

You only have to push your changes on your repo and they will redeploy automatically.

## Development: Running your app locally

Before deploying your app in the Web, you normally program, debug and test your app locally. To do so, we will need Apache Maven to build your app and Heroku CLI to run it locally. 

#### Apache Maven installation

If you have not installed Apache Maven before, you can get the binaries [here](https://maven.apache.org/download.cgi), and follow the instructions [here](https://maven.apache.org/install.html).

Note: if you are using a *Unix-like* system (e.g MacOS, Linux), you need to open a terminal and add the path to Maven permanently in your bash profile:
```ShellSession
vi ~/.bash_profile
```
And add the following line to this file so Maven is runnable from the terminal from now on:
```ShellSession
export PATH=/opt/apache-maven-3.6.3/bin:$PATH
```
The line above assumes Maven's path is `/opt/apache-maven-3.6.3/bin`, or that your Maven download has been moved there. You can use a different path too, make sure your path is reflected in this line.

For *Windows* machines, add Maven's path to the PATH property in the system's environment variables.

**Note:** Reopen your terminal for the changes to be reflected.

#### Heroku CLI installation
If you have not installed the Heroku CLI  before, you can get it [here](https://devcenter.heroku.com/articles/heroku-cli).

For Windows machines, this repo's [Procfile](https://github.com/luminaxster/swe432tomcat/blob/master/Procfile) is set up for Unix-like machines, "sh" is the shell command in Unix . In Windows, replace the following line in the Procfile:
```ShellSession
web: sh target/bin/webapp
```
 
for the following:
```ShellSession
web: target\bin\webapp.bat
```
**Note:** If you are Windows user, do not push your Procfile to your remote repo. An error like this one will be thrown:
`"targetbinwebapp not found" error and then an "app crashed" error with code H10 ...`.

### Build and run your app
To run the app contained in your repo, go your repo's root folder, the `POM.xml` should be there, this file is the configuration Maven uses to build your app so Heroku can run it. You should perform the following commands each time you want to run the latest version of your app, make sure there are no errors after you run them. 

Run this command in your terminal to build the app:
```ShellSession
mvn package
```

And this one to run it:

```ShellSession
heroku local
```

You should be able to access your app at `http://localhost:5000`.

### Making changes: adding a new servlet

In your machine, place your servlet file in the `src/main/java/servlet` folder and add the servlet annotation so your Apache Tomcat knows how to map it:
```Java
import javax.servlet.annotation.WebServlet;
...
@WebServlet( name = "servletName", urlPatterns = {"/servicePathName"} )
```
The line above handles **servlet mapping**, which makes its servlet instance available at yourServerUrl/**servicePathName**.

Now you can observe, debug or test your app locally by building your app (in terminal: `mvn package`) and running it in your local server (in terminal: `heroku local`). After that, `localhost:5000/servicePathName` (as in urlPatterns property from the annotation above) must be working.

**Note:** If your servlet mapping setup failed or is missing, the URL `localhost:5000/servicePathName` or `yourWebsite/servicePathName` won't be accessible and show a `404: Not found error`. Make sure the `@WebServlet` annotation is in the desired servlet java file and the `localhost:5000/servicePathName` matches `@WebServlet.. urlPatterns = {"/servicePathName"}`.

## Developing and Deploying continuous loop

Finally, once you are done making changes in your app locally -- you have achieved your development goals -- and now want to publish your app so users get the latest version -- deploy your app, pushing your local changes to your repo will automatically deploy this version of the app in your Heroku hosting (described in the Deployment section). 

## Important
After you are satisfied changing your code, remember they are still in your machine. You must push these changes to your github's web repo (remote), only then they will be visible to everybody. If you followed the steps linking your Heroku app with this repo, pushing changes in to your remote repo will redeploy your Heroku app.

# Add database persistence to your Heroku app
Go to your Heroku dashboard, choose your Tomcat servlet app, go to the Resources tab, click on find add-ons, type  postgres, Heroku-Postrgres will show up, select it with Hobby dev (free) tier.

**Or**, in a terminal:
```ShellSession
heroku addons:create heroku-postgresql:hobby-dev --app <your_heroku_app_name>
```
**Remember:** `<your_heroku_app_name>` is the name of your heroku app.

## Install PostgreSQL
You can get Postgres [here](https://postgresapp.com/downloads.html) and choose your platform binaries.

*Windows:* Using the wizard will install the DB, services and basic tools we need to manage and query the database.


*MacOS:* Select Postgres.app with PostgreSQL 12, and install it. Then execute this command in your terminal:
```ShellSession
sudo mkdir -p /etc/paths.d &&
echo /Applications/Postgres.app/Contents/Versions/latest/bin | sudo tee /etc/paths.d/postgresapp
```
Reopen the terminal and try:
```ShellSession
which psql
```

It should return a file system path like `/Applications/Postgres.app/Contents/Versions/latest/bin/psql`.

## Configure the connection to your remote DB add-ons in Heroku
In order for your Java applications to access the DB via JDBC, you need to setup the connection. In your terminal, execute:
```ShellSession
export JDBC_DATABASE_URL=`heroku run echo \\$JDBC_DATABASE_URL -a <your_heroku_app_name>`
```

If you running the commands on *Windows*, try running the commands separately: `heroku run echo \$JDBC_DATABASE_URL -a <your_heroku_app_name>`. Then copy the URL echoed by the previous command, and set it to your environment: `setx JDBC_DATABASE_URL "URL..."`, or create a JDBC_DATABASE_URL property in the system's environment variables with that URL.

**Remember:** `<your_heroku_app_name>` is the name of your heroku app.

Double check the environment variable was set:
```ShellSession
echo $JDBC_DATABASE_URL
```
For *Windows*, try this instead:
```ShellSession
echo %JDBC_DATABASE_URL%
```

It should return a string like `jdbc:postgresql://...`.

**Note:** This configuration will be lost once you close the terminal, do no try to make it permanent, the crendentials are renovated often.

### Connect to you database via CLI
In your terminal, enter to your DB with the following command:
```ShellSession
heroku pg:psql <your_postgresql_add_on_name> --app <your_heroku_app_name>
```
**Remember:** `<your_heroku_app_name>` is the name of your heroku app, and `<your_postgresql_add_on_name>` is your postgres add-on name.

You can get your *precise* command by going to the Heroku web page, and once in your account, go to the add-ons dashboard, select the Postgres add-on created some steps before. Then go to settings > admistration > view credentials > **Heroku cli**.

Once that command executes correctly, you should be now using the database CLI, in your shell, the input should look like this:
```ShellSession
<your_heroku_app_name>::DATABASE=>
```
Now you run DB management and query commands like:
```SQL
CREATE TABLE test(id SERIAL PRIMARY KEY, value VARCHAR (50) NOT NULL);
INSERT INTO test (value) VALUES ('a value');
SELECT name FROM test;
```
## Connecting to the database within your app: The Database Servlet
This project has an [example](https://github.com/luminaxster/swe432tomcat/blob/master/src/main/java/servlet/DatabaseServlet.java) using Java DataBase Connection(JDBC), there are plenty of ways to use Java or related frameworks APIs to connect to databases elsewhere.

Follow the next sections to follow how the Database Servlet was implemented. 
### 1. Manage and query your database
In your database CLI (the same accessed in [a previous section](#connect-to-you-database-via-cli), create the folowing table, it is required by the [DB servlet](https://github.com/luminaxster/swe432tomcat/blob/master/src/main/java/servlet/DatabaseServlet.java) to work:

```SQL
CREATE TABLE entries( 
  id serial PRIMARY KEY,
  name VARCHAR (50) NOT NULL,
  age INT  CHECK (age > 0  AND age <150) NOT NULL
);
```

Try adding a row to the table:
```SQL
INSERT INTO entries (name, age) VALUES ('Logan', 149);
```
Or querying persisted data in that table:
```SQL
SELECT name, age FROM entries;
```

### 2. Add Postgres to your app
You need to add Postgres to your dependencies in your `pom.xml`:
```XML
<dependencies>
    ...
    <dependency>
      <groupId>org.postgresql</groupId>
      <artifactId>postgresql</artifactId>
      <version>42.2.1</version>
    </dependency>
    ...
</dependencies>
```
### 3. Sanity Check
Try to run the app locally, at a terminal in your app's root folder:
```ShellSession
mvn package
heroku local
```
The terminal should show a line like:
```ShellSession
INFO: Starting ProtocolHandler ["http-nio-5000"]
```
Your Tomcat server should be up and running at `localhost:5000`, and the database servlet should be at `localhost:5000/database`.

**Note:** To stop the server from running, press `Ctrl+C`.  If you close the terminal, the server will stop as well. 

### 4. Connecting to database in the servlet
```Java
...
private class EntriesManager{
      private Connection getConnection()
        throws URISyntaxException, SQLException {
          String dbUrl = System.getenv("JDBC_DATABASE_URL");
          return DriverManager.getConnection(dbUrl);
      }
      ...
```
These lines are where adding Postgres as a dependency, and configuring the environment variable come together, missing any of these steps will cause a runtime error. In `String dbUrl = System.getenv("JDBC_DATABASE_URL");`, the environment variable is used `JDBC_DATABASE_URL`, make sure it is setup with `echo $JDBC_DATABASE_URL`. Then in `return DriverManager.getConnection(dbUrl);`, the Postgres database driver is detected based on the previous URL, the driver manager will look for it in your Postgres installed dependency and the use it to connect to the database with the credentials specified in the URL. 

**Troubleshooting:** You may get an error like `java.sql.SQLException: The url cannot be null`. Since `"JDBC_DATABASE_URL"` is not saved to your profile, make sure is set up before running `heroku local`, this is explained in this [previous section](https://github.com/luminaxster/swe432tomcat/blob/master/README.md#configure-the-connection-to-your-remote-db-add-ons-in-heroku).

### 5. Saving data into the database
```Java
...
public boolean save(String name, int age){
        PreparedStatement statement = null;
        try {
          connection = connection == null ? getConnection() : connection;
          statement = connection.prepareStatement(
          "INSERT INTO entries (name, age) values (?, ?)"
          );
          statement.setString(1, name);
          statement.setInt(2, age);
          statement.executeUpdate();
          return true;
          ...
```
After getting a new or existing `connection`, preparing a statement to insert a row in table `entries`, the order of values set follow the sequence determined by the tuple `(name, age)` in the statement: `statement.setString(1, name)` is concatenated where the first question mark is in `(?, ?)` and ` statement.setInt(2, age)` to the second.

Finally,  `statement.executeUpdate();` will attempt to insert the row, if no errors are thrown, it does not mean it succeded, the return value of the method is a count of the affected rows, in this context `1` should be successfully inserted one row. 

**Important:** Prepared statements prevent some types of SQL injection attacks. Using other API methods to do so will let your server vulnerable. more details in the next sections.

### 6. Querying data from the database
```Java
public String getAllAsHTMLTable(){
        Statement statement = null;
        ResultSet entries = null;
        StringBuilder htmlOut = new StringBuilder();
        try {
          connection = connection == null ? getConnection() : connection;
          statement = connection.createStatement();
          entries = statement.executeQuery(
          "SELECT "+Data.NAME.name()+", "+Data.AGE.name()+" FROM entries");

          while (entries.next()) {
              htmlOut.append("<tr><td>");
              htmlOut.append(Jsoup.clean(entries.getString(1), Whitelist.basic())); //name
              htmlOut.append("</td><td>");
              htmlOut.append(entries.getInt(2)); //age
              htmlOut.append("</td></tr>");
          }
          if(htmlOut.length() == 0){
            htmlOut.append("<tr><td> no entries</td></tr>");
          }
        }catch(URISyntaxException uriSyntaxException){
        ...
```
After gettign a new or exisitng connection the database, executing the query returns a ResultSet, which can be iterated. In this case were building a HTML table with rows obtained from the query result.

**Important:** Using only `executeQuery()` to make querys prevents some types of SQL injection attacks. Using other API methods to do so will let your server vulnerable. For instance, using `executeUpdate()` to make queries will allow attackers to delete your database tables by ending the current statement and appending a delete update `; delete from entries`.

### ImportantER: Avoid XSS attacks
A common way to attack (web) apps is to inject malicious code in data captured from user inputs -- Cross-Site Scripting. Since it is common to generate HTML content from user data, an attacker may add executable code that will trigger in the page. For example, adding `<script>function xss(){location.href='https://www.google.com'} </script><button onclick="xss()">click me</button>` in the name in any of the Persistence examples will succeded on adding a malicious button that takes you to `google.com` once clicked. The database fails because of the table column not accepting more than 50 characters. However, `<script>function x(){location.href='s'}()</script>` fits.
Consider using [Jsoup](https://jsoup.org/) to sanitize data when capturing user inputs in your services, and also when sending them back to your front-end. The Database example uses `Jsoup.clean()` when getting row's names from the database, and capturing string from the users `String name = Jsoup.clean(request.getParameter(Data.NAME.name()), Whitelist.basic());`.

**Always** use **sanitized** user inputs to assemble statements, and use **prepared statements** when concatenating **user inputs** in your queries or updates.

### 7. Saving an entry in and getting all entries from the database in the servlet

```Java
... doPost(...
 EntriesManager entriesManager = new EntriesManager();

       boolean ok = entriesManager.save(name,age);
       String saveStatusHTML =
       "<p>"+(ok? "Entry added.":"Entry was not added.")+"</p>";
       PrintHead(out);
       PrintEntriesBody(
        out, saveStatusHTML, entriesManager.getAllAsHTMLTable());
       PrintTail(out);
 ```
 
Finally, the servlet can use database persistence via the EntryManager instance to save (`save(...)`) a new entry and rendering all the entries in the database in a HTML table (`getAllAsHTMLTable()`).

# Grading: sharing your repo with the TA
Your assignment's repo must be private at all times and for me to grade your code, please add me as a contributor. My username is luminaxster.

## Follow the original guide
For more details about how to create a Tomcat setup from scratch, go to the Dev Center guide on how to [Create a Java Web Application using Embedded Tomcat](https://devcenter.heroku.com/articles/create-a-java-web-application-using-embedded-tomcat).

## Resources: 

[Git Tutorial](https://kbroman.org/github_tutorial/pages/init.html)

[Heroku Postgres](https://devcenter.heroku.com/articles/heroku-postgresql)

[Heroku Dataclips](https://devcenter.heroku.com/articles/dataclips)

[Java XML writing and reading](https://www.vogella.com/tutorials/JavaXML/article.html)



