<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
            integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="forum.css">
    <title>Форум job4j</title>
</head>
<body>
<div class="container mt-3">
    <div class="row">
        <h4 id="header">
            <a href="/">Форум job4j</a>
        </h4>
    </div>
    <div class="row">
        <div class="offset-md-9">
            <c:choose>
                <c:when test="${not empty username}">
                    <button type="button" class="btn btn-info" onclick="window.location.href='logout';">
                            ${username} | Выйти
                    </button>
                </c:when>
                <c:otherwise>
                    <button type="button" class="btn btn-info" onclick="window.location.href='login';">
                        Войти
                    </button>
                </c:otherwise>
            </c:choose>

            <button type="button" class="btn btn-info" onclick="window.location.href='addPost';">
                Создать пост
            </button>
        </div>
    </div>
    <br>
    <div class="row">
        <table class="table">
            <thead class="thead-light">
            <tr>
                <th scope="col" class="col-md-3">Тема</th>
                <th scope="col" class="col-md-7">Описание</th>
                <th scope="col" class="col-md-2">Дата создания</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${posts}" var="post">
                <tr onclick="document.location.href = '/post?id=${post.id}';" style="cursor: pointer;">
                        <td><c:out value="${post.name}"/></td>
                        <td><c:out value="${post.desc}"/></td>
                        <td><fmt:formatDate type="both" dateStyle="short" value="${post.created.time}" /></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>