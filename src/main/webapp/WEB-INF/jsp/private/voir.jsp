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
    <title>Résultat</title>

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
	

	<%
				int nombreVote = 0;
				String question =  request.getAttribute("question")!=null ? (String) request.getAttribute("question") : null;
				
				List<Choix> lesChoix = request.getAttribute("lesChoix")!=null ? (List<Choix>) request.getAttribute("lesChoix") : null;
				
				if(question !=null && lesChoix!=null){
				
				for(Choix choix : lesChoix){
					nombreVote = nombreVote + choix.getNbChoix();
				}
				%>
					
					<legend>${question}</br></legend>
				<ul>
				<%
				
				for(Choix choix : lesChoix){
					%>
			<li>
			   <label> <%=choix.getLibChoix()%> : </label>
			   <label> <%=choix.getNbChoix()%> </label></br>
			   
			   		<%
			   		if(nombreVote!=0){
			   		%>
			   <div class="progress">
  					<div class="progress-bar" role="progressbar" style="width: <%=(double)(choix.getNbChoix())/nombreVote*100%>%;" aria-valuenow="<%=choix.getNbChoix()%>.0" aria-valuemin="0" aria-valuemax="<%=nombreVote%>"><%=(double)(choix.getNbChoix())/nombreVote*100%>%</div>
				</div>
					<%
					}else{
				%>	0%
				<%	} %>
				
				
			</li>
					<%
					
				}%>
				</ul>
				<label> Total de vote : <%=nombreVote%></label>
			<%	}else{%>
					Aucune question n'est disponible :)
			<%	}
	
	%>


</div>
</div>
