<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "https://www.w3.org/TR/html4/loose.dtd">
<html>

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
    <script>
      var servletURL = window.location.origin;
    </script>
    <title>ACL Paper Search</title>
  </head>

  <body>
      <!-- <form action="../java/servlet/StarterServlet.java" method="post"> -->
      <!-- <form action="servletName" method="post"> -->
      <!-- <form action="../java/servlet/servletName" method="post"> -->
      <!-- <form action="StarterServlet" method="post"> -->
      <!-- <form action="../servlet/StarterServlet.java" method="post"> -->
      <form action="/search" method="post">
      <!-- Searching by Publication ID, Title, Year, Type, Summary, and URL -->
  		<table style="width: 50%">
  			<tr>
  				<td>Author:</td>
  				<td><input type="text" name="author"/></td>
  			</tr>

  			<tr>
  				<td>Title:</td>
  				<td><input type="text" name="title"/></td>
  			</tr>

  			<tr>
  				<td>Year:</td>
  				<td><input type="text" name="year"/></td>
  			</tr>

  			<tr>
          <td>
            <label for="type">Type: </label>
                <select name="type" id="type">
                  <option value="short">short</option>
                  <option value="long">long</option>
                </select>
          <td>
  			</tr>

  			<tr>
  				<td>
            <label for="sorted_by">Sorted By: </label>
                <select name="type" id="type">
                  <option value="year_of_pub">year of publication</option>
                  <option value="title_of_paper">title of paper</option>
                  <option value="name_of_author">name of author</option>
                </select>
          </td>
  			</tr>

  			<tr>
          <td>
            <label for="record_num">Record Per Page: </label>
                <select name="type" id="type">
                  <option value="ten">10</option>
                  <option value="fifteen">15</option>
                </select>
          </td>
          <td><input type="submit" value="Search"/><td>
  			</tr>
      </table>
      </br>
      <!-- Searching by Publication ID, Title, Year, Type, Summary, and URL -->

      <!-- Searching by Publication ID and Author -->
      <table style="width: 50%">
        <tr>
          <td>Enter Publication ID:</td>
          <td><input type="text" name="pub_id"/></td>
          <td><input type="submit" value="Search"/></form><td>
        </tr>
      </table>
      <!-- Searching by Publication ID and Author -->

    </form>
  </body>
</html>
