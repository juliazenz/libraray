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
                        <td>Author(s):</td>
                        <td><input type="text" name="author"/></td>
                    </tr>
                    <tr>
                        <td>Year of Publication:</td>
                        <td><select name="year">
                                <% for(int i=0; i < 50; i++){
                                    out.println("<option>"+LocalDate.now().minusYears(i).getYear()+"</option>");
                                }%>
                            </select>
                        </td>
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
                        <td><input type="submit" value="cancle" name="eve"/></td>
                        <td><input type="submit" value="add Book" name="eve"/></td>
                    </tr>
                </tbody>
            </table>
        </form>
        </center>
    </body>
</html>
