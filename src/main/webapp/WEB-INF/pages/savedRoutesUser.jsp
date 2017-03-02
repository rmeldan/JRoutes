<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div id="tooltip"></div>
<div class="container adminHome">
    <div id="routes">
        <br>
        <div class="form-group">         
        	<div class="from-to "><h2 class="panel-title"><spring:message code="routes.savedRoutes"/></h2></div>        	
        		<div class="row"> 		
        			<div class="col-md-2 colsm-2 col-xs-2 text-center">
        				<i class="glyphicon glyphicon-search "></i> <spring:message code="routes.searchByName"/>
        				<span class="badge-success badge"><input type="text" id="findOnName" class="form-control"></span>
        			</div>  
        			<div class="col-md-2 colsm-2 col-xs-2 text-center">
        				<i class="glyphicon glyphicon-search "></i> <spring:message code="routes.searchByRoute"/>
        				<span class="badge-success badge"><input type="text" id="findOnRoute" class="form-control"></span>
        			</div>
        			<form:form id="saveEditedRoute" class="form-horizontal" method="get" action="${pageContext.request.contextPath}/saveEditedRoute" 
                        			commandName="editedRoute" role="form">	
            			<c:if test="${error == true}">        			
        					<div class="col-md-8 colsm-2 col-xs-2">
        						<i>&nbsp;</i>
                    			<div class="alert user alert-danger">                       				
                       				<button type="button" class="close" data-dismiss="alert">&times;</button>                       					
									<div class="alert-link text-center">
										<form:errors path="name"/>											
									</div>
								</div> 
        					</div>
        				</c:if> 
    					<!-- Modal for editing of SavedRoute-->
    					<div class="modal fade" id="modalEdit" tabindex="-1">
            				<div class="modal-dialog">
            					<div class="modal-content">
                    				<div class="modal-header">
                        				<button type="button" class="close" data-dismiss="modal">&times;</button>
                            			<h3 class="modal-title"><spring:message code="routes.edit"/></h3>
                        			</div>
                        			<div class="modal-body">  
                        				<div class="error text-center"><font size="3" color=red face="Comic Sans MS"></font></div>	                       	
                        				<br>                        		
                            			<div class="form-group"> 
                                			<label for="inputEditName" class="col-sm-6 control-label"><spring:message code="saveRoute.routeName"/></label>                                	                                   
                                    		<div class="col-sm-6">                                    	                            		
                                    			<input id="inputEditId" name="id" hidden="true" value="">
                                    			<form:input id="inputEditName" type="text" class="form-control" path="name" name="name" value=""/>
                                    		</div>
                                		</div>
                                		<div class="form-group">
                                			<div class="col-sm-offset-2 col-sm-10 text-right">
                                				<button type="submit" class="btn btn-success btn-sm"><i class="glyphicon glyphicon-ok"></i> <spring:message code="saveRoute.save"/></button>
                                    			<button type="button" class="btn btn-default btn-sm btn-danger" data-dismiss="modal"><i class="glyphicon glyphicon-remove"></i> <spring:message code="saveRoute.cancel"/></button>                                        
                                			</div>
                            			</div>            	
									</div>
								</div>
							</div>
						</div>
					</form:form>
        		</div>	        			
        		</div> 	
        		<p>        		
				<div class="table-responsive">
					<table class="table table-stripped">
						<thead>
							<tr id="titleTable">								
								<th id="nameOfSR"><spring:message code="routes.name"/> <span class="glyphicon glyphicon-sort" data-tooltip="<spring:message code="routes.sort"/>"></span></th> 
								<th id="routesOfSR"><spring:message code="routes.route"/> <span class="glyphicon glyphicon-sort" data-tooltip="<spring:message code="routes.sort"/>"></span></th>																
								<th id="priceOfSR" class="text-center"><spring:message code="routes.price"/> <span class="glyphicon glyphicon-sort" data-tooltip="<spring:message code="routes.sort"/>"></span></th>
								<th id="timeOfSR" class="text-center"><spring:message code="routes.time"/> <span class="glyphicon glyphicon-sort" data-tooltip="<spring:message code="routes.sort"/>"></span></th>
								<th id="modificationOfSR" class="text-center"><spring:message code="routes.modificationTime"/> <span class="glyphicon glyphicon-sort" data-tooltip="<spring:message code="routes.sort"/>"></span></th>
								<th class="text-center"><spring:message code="routes.edit"/></th>
								<th class="text-center"><spring:message code="routes.delete"/></th>
							</tr>
						</thead>						
						<tbody id="change">							
							<c:forEach items="${sRDtoList}" var="sRDto">
								<tr style="" id="${sRDto.id}" height="${sRDto.name}" background="${sRDto.price}" onblur="${sRDto.time}" onabort="${sRDto.modificationTime}" 
										lang="${sRDto.startPoint.name} - ${sRDto.finishPoint.name}" align="${sRDto.finishPoint.name} - ${sRDto.startPoint.name}">
									<td id="infoOfSavedRoute" align="${sRDto.name}" lang="${sRDto.id}">${sRDto.name}</td>
									<td id="pointsOfRoute" >${sRDto.startPoint.name} - ${sRDto.finishPoint.name}<i id="${sRDto.finishPoint.name} - ${sRDto.startPoint.name}"></i> 
										<i id="${sRDto.id}" lang="${sRDto.startPoint.id}" onblur=true class="glyphicon glyphicon-zoom-in moreAboutSavedRoute green" 
												data-toggle="modal" data-target="#modalMore" data-tooltip="<spring:message code="routes.viewDetailsRoute"/>">											
										</i>
									</td>
									<td class="text-center">${sRDto.price}</td>
									<td class="text-center">${sRDto.time}</td>
									<td class="text-center">${sRDto.modificationTime}</td>
									<td class="text-center"><span class="badge badge-warning">
										<a id="${sRDto.id}" lang="${sRDto.name}" class="editSavedRouteUser" data-toggle="modal" data-target="#modalEdit" data-tooltip="<spring:message code="routes.editRoute"/>">
											<i class="glyphicon glyphicon-edit"></i>
										</a>
									</span></td>
									<td class="text-center"><span class="badge badge-danger">
										<a id="${sRDto.id}" lang="${sRDto.name}" class="deleteSavedRoute" data-toggle="modal" data-target="#modalDelete" data-tooltip="<spring:message code="routes.deleteRoute"/>">
											<i class="glyphicon glyphicon-trash"></i>
										</a>
									</span></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>					
					<div class="row">
						<div class="col-md-9 colsm-2 col-xs-2">	
							<div id="paginationSRoute"></div>						
						</div>
						<div class="col-md-2 col-sm-10 col-xs-10 text-right">
							<span style="padding: 7px 0; display: inline-block;"><spring:message code="routes.itemsPerPage"/></span>
						</div>
						<div class="col-md-1 colsm-2 col-xs-2">					
							<select id="itemsPerPageSRoute" class="form-control">
								<option value="1">1</option>
								<option value="5" selected>5</option>
								<option value="10">10</option>
								<option value="20">20</option>
								<option value="50">50</option>
							</select>
						</div>
					</div>							
				</div>		
				<!-- Modal for details about of SavedRoute-->
				<div class="modal fade" id="modalMore" tabindex="-1">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h3 class="modal-title"></h3>
							</div>
							<div class="modal-body">		
								<div class="table-responsive">
									<table class="table table-stripped">
										<thead>
											<tr>
												<th class="text-center">№</th>
												<th><spring:message code="routes.from"/></th>
												<th><spring:message code="routes.to"/></th>
												<th class="text-center"><spring:message code="routes.price"/></th>
												<th class="text-center"><spring:message code="routes.time"/></th>
												<th class="text-center"><spring:message code="routes.transport"/></th>
											</tr>
										</thead>
										<tbody id="moreSR">									
										</tbody>
									</table>										
								</div>													
								<button type="button" class="btn btn-success btn-sm" data-dismiss="modal"><spring:message code="routes.close"/></button>
							</div>
						</div>
					</div>		
				</div>					
				<!-- Modal for deleting of SavedRoute-->				   
				<div class="modal fade" id="modalDelete" tabindex="-1">
     				<div class="modal-dialog">
     					<div class="modal-content">
       						<div class="modal-header">
        						<button type="button" class="close" data-dismiss="modal">&times;</button>
           						<h3 class="modal-title"><spring:message code="routes.deleting"/></h3>
       						</div>
       						<div id="question" class="modal-body"></div>
      						<div class="modal-footer">
      							<a id="deleteSavedRoute" lang="" class="btn btn-success btn-sm" data-dismiss="modal"><i class="glyphicon glyphicon-ok"></i> <spring:message code="routes.delete"/></a>
        						<button type="button" class="btn btn-danger btn-sm" data-dismiss="modal"><i class="glyphicon glyphicon-remove"></i> <spring:message code="saveRoute.cancel"/></button>           						       						</div>
      					</div>
     				</div>
    			</div>    			    			  
    		</div> 
	</div>
</div>
<i id="reallyDeleteSaveRoute" data-tooltip="<spring:message code="routes.reallyDeleteSaveRoute"/>"></i>
<i id="partsSavedRoute" data-tooltip="<spring:message code="routes.partsSavedRoute"/>"></i>
<i id="previous" data-tooltip="<spring:message code="routes.previous"/>"></i>
<i id="next" data-tooltip="<spring:message code="routes.next"/>"></i>
<i id="sort" data-tooltip="<spring:message code="routes.sort"/>"></i>
<i id="sortDescending" data-tooltip="<spring:message code="routes.sortDescending"/>"></i>
<i id="sortAscending" data-tooltip="<spring:message code="routes.sortAscending"/>"></i>
<i id="validationEnterName" data-tooltip="<spring:message code="routes.validationEnterName"/>"></i>
<i id="validationSymbolNumbers" data-tooltip="<spring:message code="routes.validationSymbolNumbers"/>"></i>
<i id="validationUpperLetter" data-tooltip="<spring:message code="routes.validationUpperLetter"/>"></i>
<i id="validationErrorEnter" data-tooltip="<spring:message code="routes.validationErrorEnter"/>"></i>
<i id="validationNonUnique" data-tooltip="<spring:message code="routes.validationNonUnique"/>"></i>