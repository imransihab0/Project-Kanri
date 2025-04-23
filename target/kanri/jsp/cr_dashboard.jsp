<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> <%-- For date formatting --%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CR Dashboard - Kanri Blog</title>
    <style>
        body { font-family: sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }
        header { background-color: #333; color: white; padding: 1em; display: flex; justify-content: space-between; align-items: center; }
        header h1 { margin: 0; }
        header a { color: white; text-decoration: none; margin-left: 1em; }
        .container { display: flex; margin: 1em; }
        .sidebar { width: 250px; background: #fff; padding: 1em; border-radius: 5px; margin-right: 1em; box-shadow: 0 0 5px rgba(0,0,0,0.1); height: fit-content; }
        .main-content { flex-grow: 1; background: #fff; padding: 1em; border-radius: 5px; box-shadow: 0 0 5px rgba(0,0,0,0.1); }
        .user-card { margin-bottom: 0.5em; padding: 0.5em; border-bottom: 1px solid #eee; }
        .user-card:last-child { border-bottom: none; }
        .user-card h4 { margin: 0 0 0.2em 0; }
        .user-card p { margin: 0; font-size: 0.9em; color: #555; }
        .post-list table { width: 100%; border-collapse: collapse; margin-top: 1em; }
        .post-list th, .post-list td { border: 1px solid #ddd; padding: 0.8em; text-align: left; }
        .post-list th { background-color: #f2f2f2; }
        .actions a, .actions button { margin-right: 5px; padding: 3px 6px; font-size: 0.9em; text-decoration: none; cursor: pointer; border-radius: 3px; }
        .actions .edit-btn { background-color: #ffc107; color: black; border: none; }
        .actions .delete-btn { background-color: #dc3545; color: white; border: none; }
        .add-post-btn { display: inline-block; padding: 0.7em 1.5em; background-color: #28a745; color: white; border: none; border-radius: 3px; cursor: pointer; text-decoration: none; margin-bottom: 1em;}
        .add-post-btn:hover { background-color: #218838; }
    </style>
</head>
<body>

    <header>
        <h1>Kanri Blog - CR Dashboard</h1>
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
        </aside>

        <main class="main-content">
            <h2>Manage Posts</h2>

            <a href="${pageContext.request.contextPath}/posts/new" class="add-post-btn">Add New Post</a>

            <div class="post-list">
                <table>
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Created At</th>
                            <th>Updated At</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty allPosts}">
                                <c:forEach var="post" items="${allPosts}">
                                    <tr>
                                        <td><c:out value="${post.title}"/></td>
                                        <td><fmt:formatDate value="${post.createdAt}" pattern="yyyy-MM-dd HH:mm"/></td>
                                        <td><fmt:formatDate value="${post.updatedAt}" pattern="yyyy-MM-dd HH:mm"/></td>
                                        <td class="actions">
                                            <%-- We'll need a PostServlet to handle these actions --%>
                                            <a href="${pageContext.request.contextPath}/posts/edit?id=${post.postId}" class="edit-btn">Edit</a>
                                            <%-- Delete usually needs a form/POST or JS confirmation --%>
                                            <form action="${pageContext.request.contextPath}/posts/delete" method="post" style="display: inline;">
                                                <input type="hidden" name="postId" value="${post.postId}">
                                                <button type="submit" class="delete-btn" onclick="return confirm('Are you sure you want to delete this post?');">Delete</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="4">No posts found.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </main>
    </div>

</body>
</html> 