<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<HTML>
<HEAD>
    <title>index</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <script src="script.js"></script>
</HEAD>
<BODY>

<div class="row">
<form id="add_new">
    <label for="form_description">Add new item</label>
    <input type="text" class="form-control" id="form_description" name="description">
    <button type="submit" class="btn btn-primary" onclick='addNewItem("<c:out value="${sessionScope.user.id}"/>")'>Submit</button>
</form>
    <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">
            <c:choose>
                <c:when test="${sessionScope.user != null}">
                    <c:out value="${sessionScope.user.name}"/> | Сменить пользователя
                </c:when>
                <c:otherwise>Войти</c:otherwise>
            </c:choose>
        </a>
    </li>
</div>
<form id="show_done">
    <label for="check_done">Show done</label>
    <input type="checkbox" class="form-check" id="check_done" onchange="loadItems(checked)">
</form>

<table id="items-table" class="table table-dark">
    <thead>
    <tr>
        <th>ID</th>
        <th>DESCRIPTION</th>
        <th>AUTHOR</th>
        <th>CREATED</th>
        <th>IS_DONE</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>
</BODY>
</HTML>
