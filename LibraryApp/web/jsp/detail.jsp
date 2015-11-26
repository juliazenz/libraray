<%-- 
    Document   : detail
    Created on : 19.11.2015, 14:44:33
    Author     : Julia
--%>

<%@page import="java.util.LinkedList"%>
<%@page import="Beans.Book"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/infonovaStyle.css" rel="stylesheet" type="text/css"/>
        <title>Detail</title>
    </head>
    <body>
        <%! Book book = new Book();%>
        <% String bookID = request.getParameter("bookID");
            LinkedList<Book> booklist = (LinkedList<Book>) request.getServletContext().getAttribute("booklist");
            for (Book b : booklist) {
                if (b.getBookID().equals(bookID)) {
                    book = b;
                }
            }
        %>
    <center>
        <div id='leftDetail'>
            <table border="0">
                <tbody>
                    <tr>
                        <td><h1><%=book.getTitle()%></h1></td>
                    </tr>
                    <tr>
                        <td><h3>from <%=book.getAuthor()%> (Author)</h3></td>
                    </tr>
                    <tr>
                        <td><p><i><%=book.getLanguage()%></i></p></td>
                    </tr>
                    <tr>
                        <td><%=book.getSummary()%></td>
                    </tr>
                    <tr>
                        <td><a href='OverviewServlet'>Zur&uuml;ck</a></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div id='rightDetail'> 
            <img id='imgBook' src='res/<%=book.getPicture() %>'/>
        </div>
    </center>

</body>
</html>
