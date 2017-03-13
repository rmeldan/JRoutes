<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script>
$( document ).ready(function() {
	$.validator.addMethod("lettersonly", function(value, element) {
		  return this.optional(element) || /^[a-z]+$/i.test(value);
		}, '<spring:message code="transport.error" />'); 
	$("#form-transport").validate({
		rules:{
			name:{
				required:true,
				minlength:2,
				lettersonly: true
			}
			
		},
		messages:{
			name:{
				required:'<spring:message code="countriesPage.emptyErrLocal" />',
				minlength: '<spring:message code="transport.errorSymbols" />'

			}
		}
		
	});
});
</script>

<div class="container adminHome">
    <div id="transport">
        <div class="row">
            <div class="col-md-8">
                <h2 class="border-bottom-green"><i class="glyphicon glyphicon-plane"></i> <spring:message code="transport.Title" /></h2>
                <div class="transport">
                    <ul class="list-group">
                       <c:forEach items="${transportList}" var="transport">
		                    <li class="list-group-item">
		                    <span class="badge badge-danger"> 
		                    	<a data-toggle="modal" data-target="#${transport.id}_transport"> 
		                    		<i class="glyphicon glyphicon-trash"></i>
		                    	</a>
		                   	</span>
		                   	<!-- Modal -->
							<div class="modal fade" id="${transport.id}_transport" tabindex="-1" role="dialog" aria-labelledby="DeleteTransport" aria-hidden="true">
							  <div class="modal-dialog">
							    <div class="modal-content">
							      <div class="modal-header">
							        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							        <h3 class="modal-title" id="DeleteTransport"><spring:message code="transport.deleting" /></h3>
							      </div>
							      <div class="modal-body">
							       		<spring:message code="transport.deletingQuestion" />
							       		<b>${transport.name}</b> ?
							      </div>
							      <div class="modal-footer">
							        <button type="button" class="btn btn-danger btn-sm" data-dismiss="modal"><spring:message code="transport.close" /></button>
							        <a href="deletingTransport/${transport.id}" class="btn btn-success btn-sm">
			                    		<spring:message code="transport.delete" />
			                    	</a>
							      </div>
							    </div>
							  </div>
							</div>
		                    <span class="badge badge-warning">
		                    	<a href="editingTransport/${transport.id}" >
		                    		<i class="glyphicon glyphicon-edit"></i>
		                    	</a> 
		                    </span>                      
		                        ${transport.name}
		                    </li>
	                    </c:forEach>
                    </ul>
                </div>
                
            </div>
            <div class="col-md-4">
                <h2 class="border-bottom-green"><i class="glyphicon glyphicon-ok"></i> <spring:message code="transport.add" /></h2>
				<c:if test="${not empty infoMessage}">
    					<div class="alert alert-success">
	    					<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
	    					${infoMessage}
    					</div>
					</c:if>
					<c:if test="${not empty errorMessage}">
    					<div class="alert alert-danger">
    						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
    						${errorMessage}
    					</div>
					</c:if>
                <form class="form-horizontal" role="form" action="addingTransport" method="POST" id="form-transport">
                    <div class="form-group">
                        <label for="inputTransport" class="col-sm-3 control-label"><spring:message code="transport.name" /></label>

                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="inputTransport" name="name" placeholder='<spring:message code="transport.enterName" />'>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10 text-right">
                            <button type="submit" class="btn btn-success btn-sm"><i class="glyphicon glyphicon-ok"></i>
                                <spring:message code="transport.save" />
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
