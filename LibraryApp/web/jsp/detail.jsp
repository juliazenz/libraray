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
        <link href="css/infonovaStyleDetail.css" rel="stylesheet" type="text/css"/>
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
        <div id="container">
            <div id="leftDetail">
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
                            <td><a href='OverviewServlet'>Back to Overview</a></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div id="rightDetail"> 
                <form>
                    <img id="imgBook" src='res/<%=book.getPicture()%>'/>
                    <p><%=book.isAvailable() ? "available" : "not available"%></p>
                    <input type="button" value=<%=book.isAvailable() ? "lend out" : "reserve"%>>
                </form>
            </div>
        </div>

    </body>
</html>
