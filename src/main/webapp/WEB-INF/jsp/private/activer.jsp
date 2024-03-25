<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<%@page import="fr.but3.ctp.model.Question"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, shrink-to-fit=no">
    <title>Activation des questions</title>

    <link rel="stylesheet" href="/webjars/bootstrap/4.6.0/css/bootstrap.min.css">

</head>
</body>

<a href="/public/voter"> Voter </a> </br>

<a href="/private/activer"> Activer </a>
<a href="/private/voir"> Voir </a> </br>
<a href="/logout"> Logout </a> 

<div class="container">

		
	<div class="mx-auto"">
		<h1  class="mt-0">Bienvenue ${username}</h1></br>
		
		<img src="https://orcid-france.fr/wp-content/uploads/2021/05/logo-universite-lille.svg" class="img-thumbnail" width="200px" class="mr-3"></br>
	
		<form method="post" action="${pageContext.request.contextPath}/private/activer">
		<div class="form-group">
		  <legend class="mt-1">Selectionnez une question:</legend>
			<%
				
					List<Question> questions = (request.getAttribute("questions")!=null ? (List<Question>)request.getAttribute("questions") : null);
					if(questions!=null){
					for(Question question : questions){
						%>
				
				<div class="input-group" style="display: block;">
  <div class="input-group-prepend">
    <div class="input-group-text">
				   <input type="radio" name="question" value="<%=question.getId()%>" 
				   <% if(question.isActive()){
					   %>
					   checked
					   <%
					
				   }%>
				   class="mb-0" />
				   
      
    </div>
  </div>
					<div class="form-control" style="display: table; width: auto;">
				   <label><%=question.getLibquest()%></label> 
				   </div>
				   </div>
						<%
						
						
						
					}
				}
				
			
			
			%>
		</div>
			<input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}"/>
		    <button type="submit" class="btn btn-secondary btn-sm">Choisir la question</button> </br></br>
		    
		    </form>
		    
		    	<label> ${activation} </label>
		    	
    </div>
</div>
</body>
</html>
