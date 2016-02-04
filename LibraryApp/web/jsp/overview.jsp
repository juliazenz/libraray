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

        <meta charset='utf-8'>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="css/infonovaStyleOverview.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="css/cssmenu/styles.css">
        <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
        <script src="css/cssmenu/script.js"></script>
        <title>Book Overview</title>
    </head>
    <body>


        <%! LinkedList<Book> bookList = new LinkedList<>();
            LinkedList<Book> filteredList = new LinkedList<>();
            String searchString = "";
        %>
        <jsp:include page="/css/cssmenu/index.html"></jsp:include>
            <div id="container">
                <div id="headLeft">
                    <img id="logo" src="res/infonova.png" alt="infonova">
                </div>
                <div id="headCenter">
                    <h1>Library</h1>
                </div>
                <form action="OverviewServlet" method="post">
                <% searchString = (String) (session.getAttribute("search") != null ? session.getAttribute("search") : "");%>
                <div id="headRight">
                    <input type="text" name="search" value="<%=searchString%>"/>
                    <input type="submit" value="go"/>
                </div>
            </form>
            <form action="OverviewServlet">
                <% bookList = (LinkedList<Book>) request.getServletContext().getAttribute("bookList");
                    if (bookList != null) {
                        if (searchString != null && !searchString.equals("")) {
                            filteredList = (LinkedList<Book>) session.getAttribute("filterList");
                        } else {
                            filteredList = (LinkedList<Book>) bookList.clone();
                        }
                        if (filteredList != null) {
                            for (Book b : filteredList) {
                                String action = (b.isAvailable()) ? "loan" : "reserve";%>
                <div class='row'>
                    <div id='pic'>
                        <a href='DetailServlet?bookID=<%=b.getBookID()%>'>
                            <img id='imgBook' src='res/<%=b.getPicture()%>' alt="<%=b.getTitle()%>"></a>
                    </div>
                    <div id='book'><p><a href='DetailServlet?bookID=<%=b.getBookID()%>' class='bookTitle'><%=b.getTitle()%></a>
                            (<%=b.getAuthor()%>)</p><p>
                            <%=b.getLanguage()%></p></div>

                    <div id='info'><a href='DetailServlet?bookID=<%=b.getBookID()%>'>more Information...</a></div>
                    <div id='lend'>
                        <a href='DetailServlet?bookID=<%=b.getBookID()%>&action=<%=action%>'><input type='button' value='<%=action%>'/></a>
                    </div>
                </div>
                <% }
                        }
                    }%>
            </form>
        </div></body></html>