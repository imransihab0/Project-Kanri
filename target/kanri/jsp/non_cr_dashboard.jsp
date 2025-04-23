<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> <%-- For date formatting --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- Added for fn:length etc. --%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Kanri Blog</title>
    <%-- Reusing styles from cr_dashboard --%>
    <style>
        body { font-family: sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }
        header { background-color: #333; color: white; padding: 1em; display: flex; justify-content: space-between; align-items: center; }
        header h1 { margin: 0; }
        header a { color: white; text-decoration: none; margin-left: 1em; }
        .container { display: flex; margin: 1em; gap: 1em; /* Add gap between sidebar and main */}
        .sidebar { width: 250px; background: #fff; padding: 1em; border-radius: 5px; box-shadow: 0 0 5px rgba(0,0,0,0.1); height: fit-content; flex-shrink: 0; /* Prevent sidebar from shrinking */}
        .main-content { flex-grow: 1; /* Takes remaining space */ }
        .user-card { margin-bottom: 0.5em; padding: 0.5em; border-bottom: 1px solid #eee; }
        .user-card:last-child { border-bottom: none; }
        .user-card h4 { margin: 0 0 0.2em 0; }
        .user-card p { margin: 0; font-size: 0.9em; color: #555; }

        /* Post Card Styling */
        .post-card {
            background: #fff;
            padding: 1.5em;
            margin-bottom: 1em; /* Space between cards */
            border-radius: 5px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
        }
        .post-card .author-name {
            font-size: 0.9em;
            color: #666;
            margin-bottom: 0.5em;
        }
        .post-card h3 { /* Post Title */
            margin-top: 0;
            margin-bottom: 0.7em;
            font-size: 1.4em;
        }
        .post-card .content-snippet {
            margin-bottom: 1em;
            line-height: 1.6;
            color: #333;
        }
         .post-card .post-date {
            font-size: 0.85em;
            color: #888;
            text-align: right;
         }

        /* Removed old table styles */
    </style>
</head>
<body>

    <header>
        <h1>Kanri Blog - Dashboard</h1>
        <div>
            Welcome, <c:out value="${currentUser.name}"/>!
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </div>
    </header>

    <div class="container">
        <aside class="sidebar">
            <h3>CR Users</h3>
            <c:if test="${not empty allUsers}">
                <c:forEach var="user" items="${allUsers}">
                    <c:if test="${user.role == 'CR'}">
                        <div class="user-card">
                            <h4><c:out value="${user.name}"/></h4>
                            <p>ID: <c:out value="${user.studentId}"/> </p>
                            <p>Email: <c:out value="${user.email}"/> </p>
                        </div>
                    </c:if>
                </c:forEach>
            </c:if>
            <%-- Placeholder if no CR users found --%>
            <c:set var="hasCRUsers" value="false"/>
            <c:forEach var="userCheck" items="${allUsers}">
                <c:if test="${userCheck.role == 'CR'}">
                    <c:set var="hasCRUsers" value="true"/>
                </c:if>
            </c:forEach>
            <c:if test="${not hasCRUsers}">
                <p>No CR users found.</p>
            </c:if>
        </aside>

        <main class="main-content">
            <h2>Recent Posts</h2>

            <%-- Removed Table - Loop through posts and display as cards --%>
            <c:choose>
                <c:when test="${not empty allPosts}">
                    <c:forEach var="post" items="${allPosts}">
                        <div class="post-card">
                            <%-- Look up author using the userMap --%>
                            <c:set var="author" value="${userMap[post.userId]}"/>
                            <p class="author-name">
                                <c:choose>
                                    <c:when test="${not empty author}">
                                        <c:out value="${author.name}"/>
                                    </c:when>
                                    <c:otherwise>
                                        [Unknown Author]
                                    </c:otherwise>
                                </c:choose>
                            </p>
                            
                            <h3><c:out value="${post.title}"/></h3>

                            <div class="content-snippet">
                                <%-- Display first ~150 chars of content --%>
                                <c:set var="content" value="${post.content}"/>
                                <c:choose>
                                    <c:when test="${fn:length(content) > 150}">
                                        <c:out value="${fn:substring(content, 0, 150)}"/>...
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${content}"/>
                                    </c:otherwise>
                                </c:choose>
                                <%-- Optional: Add a link to view full post later --%>
                                <%-- <a href="${pageContext.request.contextPath}/posts/view?id=${post.postId}">Read More</a> --%>
                            </div>
                            
                            <p class="post-date">
                                <fmt:formatDate value="${post.createdAt}" pattern="yyyy-MM-dd HH:mm"/>
                            </p>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p>No posts found.</p>
                </c:otherwise>
            </c:choose>
        </main>
    </div>

</body>
</html> 