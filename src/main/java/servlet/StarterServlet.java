package servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.*;
import java.sql.*;

@WebServlet(name = "Starter", urlPatterns = {"/search"})
//@WebServlet(name = "servletName", urlPatterns = {"/servicePathName"})

/*require mysql dependency in your pom.xml
 <dependencies>
...

    <dependency>
    	<groupId>mysql</groupId>
    	<artifactId>mysql-connector-java</artifactId>
    	<version>8.0.15</version>
    </dependency>
...
*/

public class StarterServlet extends HttpServlet {
    private Statement stmt;
    private Connection conn;
    private String URL = "jdbc:mysql://p2jxdb.mysql.database.azure.com:3306/papers?useSSL=true&requireSSL=false&serverTimezone=UTC";
    private String username = "db_user@p2jxdb";
    private String password = "zksTu8%d";

    public void init(ServletConfig config) throws ServletException {
      super.init(config);

      try {
        conn = DriverManager.getConnection(URL,username,password);
        stmt = conn.createStatement();
        Class.forName("com.mysql.cj.jdbc.Driver");
      } catch (Exception e) {
        e.printStackTrace();
        conn = null;
      }
    }

    public void destroy() {
	     try {
         stmt.close();
         conn.close();
       } catch (Exception e) {
         System.err.println("problem closing the database");
	    }
    }

    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      try {
        conn = DriverManager.getConnection(URL,username,password);
        stmt = conn.createStatement();
        Class.forName("com.mysql.cj.jdbc.Driver");

        ResultSet rs;
        String author = request.getParameter("author");
        String title = request.getParameter("title");
        String type = request.getParameter("type");
        String year = request.getParameter("year");
        String sorted_by = request.getParameter("sorted_by");
        String record_num = request.getParameter("record_num");
        String pub_id = request.getParameter("pub_id");

        //if the user searched via publication ID and it's not an empty string
        //assuming they entered a valid publication ID
        if ((!pub_id.equals(""))) {
          String sql = "SELECT * " + "FROM Publications " + "WHERE PublicationID = " + pub_id;
          rs = stmt.executeQuery(sql);

            while (rs.next()) {
              int pid = rs.getInt("PublicationID");
              String ttl = rs.getString("Title");
              int yr = rs.getInt("Year");
              String ty = rs.getString("Type");
              String summary = rs.getString("Summary");
              String URL = rs.getString("URL");

              //formats the page with the publication ID results
              response.setContentType("text/html");
              PrintWriter out = response.getWriter();
              PrintHead(out);

              //prints the body
              out.println("<table style=\"width: 50%\">");
          		out.println("<tr>");
              out.println("<td>Publication ID: </td>");
          		out.println("<td>" + pid + "</td>");
          		out.println("</tr>");

              out.println("<tr>");
              out.println("<td>Title: </td>");
          		out.println("<td>" + ttl + "</td>");
          		out.println("</tr>");

              out.println("<tr>");
              out.println("<td>Year: </td>");
          		out.println("<td>" + yr + "</td>");
          		out.println("</tr>");

              out.println("<tr>");
              out.println("<td>Type: </td>");
          		out.println("<td>" + ty + "</td>");
          		out.println("</tr>");

              out.println("<tr>");
              out.println("<td>Summary: </td>");
          		out.println("<td>" + summary + "</td>");
          		out.println("</tr>");

              out.println("<tr>");
              out.println("<td>URL: </td>");
          		out.println("<td><a href=\"" + URL + "\"><p>Click here to read</p></a></td>");
          		out.println("</tr>");
              PrintEnd(out);
            }
        }

        //author not empty
        //if ((!author.equals("")) && (title.equals("")) && (year.equals("")) && (type.equals("long") || type.equals("short"))) {
        //  sql = "SELECT * " + "FROM Authors " + "WHERE Author = " + author;
        //  rs = stmt.executeQuery(sql);
        //iuj`}

        if (((!title.equals("")) || (!year.equals("")) && (type.equals("long") || type.equals("short")) && (pub_id.equals("")))) {

          //if the year is searched
          if (!year.equals("") && (title.equals(""))) {
            String sql = "SELECT * " + "FROM Publications " + "WHERE Year = " + year + " AND Type = '" + type + "'";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
              int pid = rs.getInt("PublicationID");
              String ttl = rs.getString("Title");
              int yr = rs.getInt("Year");
              String ty = rs.getString("Type");
              String summary = rs.getString("Summary");
              String URL = rs.getString("URL");

              //formats the page with the publication ID results
              response.setContentType("text/html");
              PrintWriter out = response.getWriter();
              PrintHead(out);

              //prints the body
              out.println("<table style=\"width: 50%\">");
              out.println("<tr>");
              out.println("<td>Publication ID: </td>");
              out.println("<td>" + pid + "</td>");
              out.println("</tr>");

              out.println("<tr>");
              out.println("<td>Title: </td>");
              out.println("<td>" + ttl + "</td>");
              out.println("</tr>");

              out.println("<tr>");
              out.println("<td>Year: </td>");
              out.println("<td>" + yr + "</td>");
              out.println("</tr>");

              out.println("<tr>");
              out.println("<td>Type: </td>");
              out.println("<td>" + ty + "</td>");
              out.println("</tr>");

              out.println("<tr>");
              out.println("<td>Summary: </td>");
              out.println("<td>" + summary + "</td>");
              out.println("</tr>");

              out.println("<tr>");
              out.println("<td>URL: </td>");
              out.println("<td><a href=\"" + URL + "\"><p>Click here to read</p></a></td>");
              out.println("</tr>");
              PrintEnd(out);
            }
          }

          //if the title is searched
          if (!title.equals("") && (year.equals(""))) {
            String sql = "SELECT * " + "FROM Publications " + "WHERE Title LIKE '%" + title + "%' AND Type = '" + type + "';";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
              int pid = rs.getInt("PublicationID");
              String ttl = rs.getString("Title");
              int yr = rs.getInt("Year");
              String ty = rs.getString("Type");
              String summary = rs.getString("Summary");
              String URL = rs.getString("URL");

              //formats the page with the publication ID results
              response.setContentType("text/html");
              PrintWriter out = response.getWriter();
              PrintHead(out);

              //prints the body
              out.println("<table style=\"width: 50%\">");
              out.println("<tr>");
              out.println("<td>Publication ID: </td>");
              out.println("<td>" + pid + "</td>");
              out.println("</tr>");

              out.println("<tr>");
              out.println("<td>Title: </td>");
              out.println("<td>" + ttl + "</td>");
              out.println("</tr>");

              out.println("<tr>");
              out.println("<td>Year: </td>");
              out.println("<td>" + yr + "</td>");
              out.println("</tr>");

              out.println("<tr>");
              out.println("<td>Type: </td>");
              out.println("<td>" + ty + "</td>");
              out.println("</tr>");

              out.println("<tr>");
              out.println("<td>Summary: </td>");
              out.println("<td>" + summary + "</td>");
              out.println("</tr>");

              out.println("<tr>");
              out.println("<td>URL: </td>");
              out.println("<td><a href=\"" + URL + "\"><p>Click here to read</p></a></td>");
              out.println("</tr>");
              PrintEnd(out);
            }
          }

          //if the title and year are searched
          if ((!title.equals("")) && (!year.equals(""))) {
            String sql = "SELECT * " + "FROM Publications " + "WHERE Year = " + year + " AND Title LIKE '%" + title + "%' AND Type = '" + type + "';";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
              int pid = rs.getInt("PublicationID");
              String ttl = rs.getString("Title");
              int yr = rs.getInt("Year");
              String ty = rs.getString("Type");
              String summary = rs.getString("Summary");
              String URL = rs.getString("URL");

              //formats the page with the publication ID results
              response.setContentType("text/html");
              PrintWriter out = response.getWriter();
              PrintHead(out);

              //prints the body
              out.println("<table style=\"width: 50%\">");
              out.println("<tr>");
              out.println("<td>Publication ID: </td>");
              out.println("<td>" + pid + "</td>");
              out.println("</tr>");

              out.println("<tr>");
              out.println("<td>Title: </td>");
              out.println("<td>" + ttl + "</td>");
              out.println("</tr>");

              out.println("<tr>");
              out.println("<td>Year: </td>");
              out.println("<td>" + yr + "</td>");
              out.println("</tr>");

              out.println("<tr>");
              out.println("<td>Type: </td>");
              out.println("<td>" + ty + "</td>");
              out.println("</tr>");

              out.println("<tr>");
              out.println("<td>Summary: </td>");
              out.println("<td>" + summary + "</td>");
              out.println("</tr>");

              out.println("<tr>");
              out.println("<td>URL: </td>");
              out.println("<td><a href=\"" + URL + "\"><p>Click here to read</p></a></td>");
              out.println("</tr>");
              PrintEnd(out);
            }
          }
      }

        stmt.close();
      } catch (Exception e) {
        e.printStackTrace();
        conn = null;
      }
    }

    // Prints the <head> of the HTML page.
    private void PrintHead(PrintWriter out) {
       out.println("<html>");
       out.println("");

       out.println("<head>");
       out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=US-ASCII\">");
       out.println("<title>Search Results</title>");
       out.println("</head>");
       out.println("");
    }

    // Prints the end of the HTML page.
    private void PrintEnd(PrintWriter out) {
       out.println("");
       out.println("</html>");
    }
}
