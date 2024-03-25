<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<%@page import="fr.but3.ctp.model.Question"%>
<%@page import="fr.but3.ctp.model.Choix"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, shrink-to-fit=no">
    <title>Page de vote</title>

    <link rel="stylesheet" href="/webjars/bootstrap/4.6.0/css/bootstrap.min.css">

</head>
<body>

<a href="/public/voter"> Voter </a> </br>

<a href="/private/activer"> Activer </a>
<a href="/private/voir"> Voir </a> </br>
<a href="/logout"> Logout </a> 

<div class="container">

<div class="mx-auto"">
		<h1  class="mt-0">Bienvenue ${username}</h1></br>
		
		<img src="https://orcid-france.fr/wp-content/uploads/2021/05/logo-universite-lille.svg" class="img-thumbnail" width="200px" class="mr-3"></br>
	

<fieldset>
  
	<%
		
			String aVote =  request.getAttribute("activation")!=null ? (String) request.getAttribute("activation") : null;
			if(aVote==null){
				String question =  request.getAttribute("question")!=null ? (String) request.getAttribute("question") : null;
				
				List<Choix> lesChoix = request.getAttribute("choix")!=null ? (List<Choix>) request.getAttribute("choix") : null;
				if(question !=null && lesChoix!=null){
				%>
					
					<legend>${question}</br></legend>
					<form method="post" action="${pageContext.request.contextPath}/public/voter">
				<%
				for(Choix choix : lesChoix){
					%>
			<div>
				
			   <input type="radio" name="choix" value="<%=choix.getId()%>" 
			   />
			   <label for="<%=choix.getId()%>"><%=choix.getLibChoix()%></label>
			   
			   	<input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}"/>
    			
			</div>
					<%
				}
					%>
					<button type="submit" >Envoyer votre choix</button> </br></br>
					</form>
					<%
					
					
				}else{%>
					Aucune question n'est disponible :)
			<%	}
			}
			else{%>
				<label> ${activation} </label>
		<%	}
	
	%>
</fieldset>


    
    </form>
</div>
</div>
</div>
</body>
</html>