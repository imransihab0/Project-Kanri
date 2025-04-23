<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%-- Dynamic Title based on whether we are editing or creating --%>
    <title><c:choose><c:when test="${post.postId > 0}">Edit Post</c:when><c:otherwise>Add New Post</c:otherwise></c:choose> - Kanri Blog</title>
    <style>
        body { font-family: sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }
        header { background-color: #333; color: white; padding: 1em; display: flex; justify-content: space-between; align-items: center; }
        header h1 { margin: 0; }
        header a { color: white; text-decoration: none; margin-left: 1em; }
        .container { max-width: 800px; margin: 2em auto; padding: 2em; background: #fff; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .form-group { margin-bottom: 1.5em; }
        label { display: block; margin-bottom: 0.5em; font-weight: bold; }
        input[type="text"], textarea {
            width: 100%;
            padding: 0.8em;
            border: 1px solid #ccc;
            box-sizing: border-box;
            border-radius: 3px;
        }
        textarea { min-height: 200px; resize: vertical; }
        button { padding: 0.8em 1.5em; background-color: #007bff; color: white; border: none; border-radius: 3px; cursor: pointer; font-size: 1em; }
        button:hover { background-color: #0056b3; }
        a.cancel-btn { display: inline-block; margin-left: 1em; color: #6c757d; text-decoration: none; }
        .error { color: red; margin-bottom: 1em; background-color: #f8d7da; border: 1px solid #f5c6cb; padding: 0.75em; border-radius: 3px; }
    </style>
</head>
<body>

    <header>
        <h1>Kanri Blog - Post Editor</h1>
        <div>
            <%-- Assuming currentUser is available in session, but might not be needed here --%>
            <%-- Welcome, <c:out value="${sessionScope.loggedInUser.name}"/>! --%>
            <a href="${pageContext.request.contextPath}/cr/dashboard">Back to Dashboard</a>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </div>
    </header>

    <div class="container">
        <h2><c:choose><c:when test="${post.postId > 0}">Edit Post</c:when><c:otherwise>Add New Post</c:otherwise></c:choose></h2>

        <%-- Display error message if present --%>
        <c:if test="${not empty errorMessage}">
            <p class="error">${errorMessage}</p>
        </c:if>

        <%-- Post Form --%>
        <%-- The action attribute points to the save URL handled by PostServlet --%>
        <form action="${pageContext.request.contextPath}/posts/save" method="post">

            <%-- Include post ID only if editing (for the update logic) --%>
            <c:if test="${post.postId > 0}">
                <input type="hidden" name="postId" value="<c:out value='${post.postId}'/>">
            </c:if>

            <div class="form-group">
                <label for="title">Title:</label>
                <input type="text" id="title" name="title" value="<c:out value='${post.title}'/>" required>
            </div>

            <div class="form-group">
                <label for="content">Content:</label>
                <textarea id="content" name="content" required><c:out value='${post.content}'/></textarea>
            </div>

            <button type="submit">Save Post</button>
            <a href="${pageContext.request.contextPath}/cr/dashboard" class="cancel-btn">Cancel</a>
        </form>
    </div>

</body>
</html> 