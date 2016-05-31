<%-- 
    Document   : addBook
    Created on : 07.01.2016, 14:17:02
    Author     : Julia
--%>

<%@page import="java.time.LocalDate"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/infonovaStyleOverview.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="css/cssmenu/styles.css">
        <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
        <script src="css/cssmenu/script.js"></script>
        <title>Add new Book</title>
    </head>
    <body>
        <jsp:include page="/css/cssmenu/index.html"></jsp:include>
        <center>
            <h1>Add a new book to the library</h1>
            <form action="AddBookServlet" method="POST">
                <table border="0">
                    <tbody>
                        <tr>
                            <td>Title:</td>
                            <td><input type="text" name="title"/></td>
                        </tr>
                        <tr>
                            <td>ISBN:</td>
                            <td><input type="text" name="isbn"/></td>
                        </tr>
                        <tr>
                            <td>Author(s):</td>
                            <td><input type="text" name="author"/></td>
                        </tr>
                        <tr>
                            <td>Publisher:</td>
                            <td><input type="text" name="publisher"/></td>
                        </tr>
                        <tr>
                            <td>Date of Publication:</td>
                            <td><input type="text" name="publication" value="DD.MM.YYYY" /></td>
                    </tr>
                    <tr>
                        <td>Language:</td>
                        <td><input type="text" name="language"/></td>
                    </tr>
                    <tr>
                        <td>Link to Amazon</td>
                        <td><input type="text" name="amazonlink"/></td>
                    </tr>
                    <tr>
                        <td>Summary:</td>
                        <td><textarea name="summary" rows="5" cols="20">
                            </textarea></td>
                    </tr>
                    <tr>
                        <td>Picture:</td>
                        <td><input type="file" name="picture" accept="image/*"/></td>
                    </tr>
                    <tr>
                        <td>Number of Books:</td>
                        <td><input type="text" name="exemplare"/></td>
                    </tr>
                    <tr>
                        
                        <td><input type="reset" value="cancle" name="eve"/></td>
                        <td><input type="submit" value="add Book" name="eve"/></td></tr>
                    <tr>
                        <td><a href='OverviewServlet'> Back to Overview</a></td>
                    </tr>
                </tbody>
            </table>
                <% if(request.getAttribute("error") != null){ %>
                    <p id="error"><%=request.getAttribute("error")%></p>
                    <% } %>
        </form>
    </center>
</body>
</html>
