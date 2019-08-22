<%@include file="common/header.jspf" %>
<%@include file="common/navigation.jspf" %>

<div class="container">

    <a type="button" class="btn btn-info" href="/add-measure">Add New Measure</a>


    <h1>Your measures: </h1>
    <table class="table table-striped">
        <%--<caption>Measures</caption>--%>
        <thead>
        <tr>
            <th>Date</th>
            <th>Weight</th>
            <th>Notes</th>
            <th></th>
            <th></th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${measures}" var="measure">
            <tr>
                <td><fmt:formatDate value="${measure.date}" pattern="dd/MM/yyyy"/></td>
                <td><b>${measure.weight}</b> kg</td>
                <td><i>${measure.notes}</i></td>
                <td><a type="button" class="btn btn-success" href="/update-measure?id=${measure.id}">Update</a></td>
                <td><a type="button" class="btn btn-warning" href="/delete-measure?id=${measure.id}">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br/>
    <br/>

</div>

<%@include file="common/footer.jspf" %>
