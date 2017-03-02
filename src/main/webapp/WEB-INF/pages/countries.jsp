<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script type="text/javascript" src="<c:url value="/resources/js/jQuery.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/country.valid.js" />"></script>
<script>
$(document).ready(function(){
	
	$("#submit_id").click(function(){
		$('#validMessageId').empty();
		});
	
	var confirmMessageLocal = '<spring:message code="countriesPage.deleteConfirm" />';
	var formatErrLocal = '<spring:message code="countriesPage.formatErrLocal" />';
	var caseErrLocal = '<spring:message code="countriesPage.caseErrLocal" />';
	var dublicateErrLocal = '<spring:message code="countriesPage.dublicateErrLocal" />';
	var lengthErrLocal = '<spring:message code="countriesPage.lengthErrLocal" />';
	var emptyErrLocal = '<spring:message code="countriesPage.emptyErrLocal" />';
	var x = [confirmMessageLocal,formatErrLocal,caseErrLocal,
	         dublicateErrLocal,lengthErrLocal,emptyErrLocal];
	fLocaliz(x);
});
</script>

<div class="container adminHome">
    <div id="countries">
        <div class="row">
            <div class="col-md-8">
            <h2 class="border-bottom-green"><i class="glyphicon glyphicon-globe"></i>
           		<spring:message  code="countries.countryListTitle" />
            </h2>
            <div class="countries">
            <form:form id="validMessageId">
            <c:if  test="${isValid}">  
                <div class="alert alert-success">
                	<spring:message code='${message}' /> 
                </div>     
            </c:if> 
             <c:if  test="${isValid==false}">  
                <div class="alert alert-danger">
                	<spring:message code='${message}' /> 
                </div>     
            </c:if> 
           </form:form>
           <div style="overflow:auto;  height:450px; ">
                <ul class="list-group">
                <c:forEach items="${countyList}" var="country">
                    <li  class="list-group-item">  <span class="badge badge-danger"> 
                    <a class="del-a"  id="${country.id}" name="${country.name}"   href="#" >                     
                    <i class="glyphicon glyphicon-trash"></i></a></span>
                    <span class="badge badge-warning"><a href="editingCountry/${country.id}" ><i class="glyphicon glyphicon-edit"></i></a> </span>                      
                        ${country.name}
                    </li>
                    </c:forEach>
                </ul>
            </div>    
            </div>
            <!-- Modal -->
            <div class="modal fade" id="editCountry" tabindex="-1" role="dialog" aria-labelledby="CountryLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h3 class="modal-title" id="CountryLabel">
                           		 <spring:message code="countries.edit" />
                            </h3>
                        </div>
                        <div class="modal-body">
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <label id="locId" for="inputCountry" class="col-sm-6 control-label">
                                   		 <spring:message code="countries.countryName" />
                                    </label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" id="inputCountry" value="Україна">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10 text-right">
                                        <button type="button" class="btn btn-default btn-sm btn-danger" data-dismiss="modal"><i class="glyphicon glyphicon-remove"></i> Відмінити</button>
                                        <button type="submit" class="btn btn-success btn-sm"><i class="glyphicon glyphicon-ok"></i> 
                                        	<spring:message code="countries.countrySave" />
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <h2 class="border-bottom-green"><i class="glyphicon glyphicon-ok"></i>
             <spring:message code="countries.countryAdd" />
             </h2> 
            <form:form id="main_form" class="form-horizontal" method="post" action="addingCountry" commandName="country">
			<div>
				<div class="form-group">
				<form:label for="inputCountry" class="col-sm-3 control-label" path="name">
				<spring:message code="countries.countryName" />
				</form:label>
					<div class="col-sm-9">
						<form:input id="textbox_id" class="form-control" placeholder="name" path="name" />
					</div>
				</div>	
				<div id="outError">
			 
			 	</div>
			</div>
		<div>
			<div class="col-sm-offset-2 col-sm-10 text-right">
                <button type="submit" id="submit_id" class="btn btn-success btn-sm"><i class="glyphicon glyphicon-ok"></i>
               		  <spring:message code="countries.countrySave" />
                </button>
            </div>
		</div>
			</form:form>
            </div>
        </div>
    </div>
</div>

<h1 id="confirmEnId"></h1>
<h1 id="confirmUaId"></h1>    