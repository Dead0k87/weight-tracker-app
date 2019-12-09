<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>


<%--add java script files--%>
<div class="container">
    <h2> ${name} </br></br> ADD NEW MEASURE:</h2>
   

 <form:form method="POST" modelAttribute="measure">
        <form:hidden path="id"/>
        <fieldset class="form-group">
                <%--path is a name of the field in Measure bean--%>
            <form:label path="weight">Weight: </form:label>
            <form:input path="weight" placeholder="0.0" type="number" step="0.1" required="required"/>
                <%--min="0.1"--%>
            <form:label path="notes">Note: </form:label>
            <form:input path="notes" type="text"/>
                <%--type="number" placeholder="0.0" step="0.10"--%>
            <form:errors path="weight" cssClass="text-warning"/>
        </fieldset>

        <fieldset class="form-group">
                <%--path is a name of the field in Measure bean--%>
            <form:label path="date">Date: </form:label>
            <form:input path="date" required="required" cssClass="datepicker"/>
            <form:errors path="weight" cssClass="text-warning"/>
        </fieldset>
        <button type="submit" class="btn btn-success">Set Measure</button>
    </form:form>
</div>


<%@include file="common/footer.jspf" %>