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
        <link rel="stylesheet" href="css/cssmenu/styles.css">
        <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
        <script src="css/cssmenu/script.js"></script>
        <title>Detail</title>
    </head>
    <body>  
        <%! Book book = new Book();%>      
        
        <jsp:include page="/css/cssmenu/index.html"></jsp:include>
        
        <% String bookID = request.getParameter("bookID");
            LinkedList<Book> booklist = (LinkedList<Book>) request.getServletContext().getAttribute("bookList");
            if (booklist.size() > 0) {
                for (Book b : booklist) {
                    if (b.getBookID().equals(bookID)) {
                        book = b;
                    }
                }
            }
        %>

        <h1><% booklist.size();%></h1>

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
                        <tr><td> <p><i><b>Status:</b></i><%=book.isAvailable() ? " available" : " not available"%></p></td></tr>
                        <tr>
                            <td><a href=<%=book.getAmazonlink()%>>To Amazon</a></td>
                        </tr>
                        <tr>
                            <td><a href='OverviewServlet'><- Back to Overview</a></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div id="rightDetail"> 
                <form>
                    <img id="imgBook" src='res/<%=book.getPicture()%>'/>
                   <input type='button' value='<%=(book.isAvailable() )? "lend out" : "reserve"%>'>
                </form>
            </div>
        </div>

    </body>
</html>
