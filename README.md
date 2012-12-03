Simple Content Management System
====

SCMS is a Java project, aimed at creating simple lightweight CMS suitable for news sites, blogs or even social networks.

Dependencies
----
As I use Maven, I strongly recommend it, because it makes deploying projects to remote webservers very easy (I use Tomcat Maven Plugin for it).

You also will need the following:

- MySQL server running (you may choose PostgreSQL or whatever you like, but you'll have to edit *connectToDatabase* function)
- Twitter Bootstrap (2.2.1) - only *bootstrap.min.css* and *bootstrap-responsive.min.css* are needed, put them under *css* directory
- html5shiv - (optional) put it under *js* directory
- Few Java libs:
 - Appropriate Servlet API and JSP API for your webserver version
 - JSTL and JSTL API
 - Appropriate driver for your database
 - UrlRewriteFilter (4.0.4)
 - Apache Commons Lang3
 - Joda-Time

Initial setup
----
1. Deploy this project to your webserver
2. Create a new database (instructions will be added later)
3. Fill the *imgdb* directory with jpg-images
4. ?????
5. PROFIT!