<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>
        <c:set var="type" value="${meal.id == null ? 'Add' : 'Update'}"/>
        <c:out value="${type}"/>
    </title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h1>${type} meal</h1>

<form method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <table>
        <input type="hidden" name="id" value="${meal.id}">
        <tr>
            <td>DateTime:</td>
            <td><input type="datetime-local" name="date" size=30 required value="${meal.dateTime}"></td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><input type="text" name="description" size=30 required value="${meal.description}"></td>
        </tr>
        <tr>
            <td>Calories:</td>
            <td><input type="number" name="calories" size=30 required value="${meal.calories}"></td>
        </tr>
    </table>
    <button type="submit">Сохранить</button>
    <button onclick="window.history.back()" type="button">Отменить</button>
</form>
</body>
</html>
