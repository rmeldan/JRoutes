<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div id="tooltip"></div>
<div class="container adminHome">
    <div id="routes">
        <div class="panel-group" id="accordion">
            <div class="panel panel-default">
                <div class="panel-heading text-center">
                    <h2 class="panel-title">
                     	<i class="glyphicon glyphicon-map-marker"></i> 
                    	<a data-toggle="collapse" data-parent="#accordion" id="collapseRoute">          
                            <c:if test="${savedRouteEdit.id == null}">
                            	<spring:message code="routes.buildRoute"/>
                            </c:if>
                            <c:if test="${savedRouteEdit.id != null}">
                            <spring:message code="routes.editingRoute"/> "${savedRouteEdit.name}"
                            </c:if>
                    	</a>
                    </h2>
                </div>
                <div id="collapseOne" class="panel-collapse ${unwrap}"> 
                    <div class="panel-body">
                        <div class="box border-bottom-green">
                            <div class="row">
                                <div class="col-md-6">
                                	<form id="addConnectForm" class="form-horizontal" method="get" action="${pageContext.request.contextPath}/routes/addingRoute">
                               			<div class="form-group">
                                			<div class="col-sm-9">
                                				<div class="from-to"><spring:message code="routes.choosePoint"/></div>
                                        		<select class="form-control" id="countryComboId">
                                        			<option value="-1"><spring:message code="routes.chooseCountry"/></option>
                                            		<c:forEach items="${countries}" var="country">
                                            			<option value="${country.id}">${country.name}</option>
                                            		</c:forEach>
                                        		</select>
                                 			</div>
                                 		</div>                                                             	                                                               
                                 		<div class="form-group">
                                 			<div class="col-sm-9">
                                 				<select name="id" class="form-control" id="cityComboId">
                                 					<option id="chooseCity" value="-1"><spring:message code="routes.chooseCity"/></option>
                                    				<c:forEach items="${rCpDtoList}" var="rCpDto">
                                    	    		<c:if test="${rCpDto.transportId == null}">																						
                                    	    			<option value="${rCpDto.id}">${rCpDto.routePointBId.name}</option>                
                                            		</c:if>
                                            		<c:if test="${rCpDto.transportId != null}">
                                            			<option value="${rCpDto.id}">${rCpDto.routePointBId.name} (${rCpDto.transportId.name})</option>   
                                            		</c:if>
                                        			</c:forEach>
                                       			</select>
                                       		</div>
                                    	</div> 
                                    	<div class="form-group">
                                    		<div class="col-sm-9 text-left">
                                        		<button id="buttonAddCity" type="submit" class="btn btn-success btn-sm" data-toggle="modal" data-target=""><spring:message code="routes.add"/></button>
                                    		</div>
                                		</div>                                                          	
                                	</form>                                                                                                         
                                </div>
                                <div class="col-md-6">    
                                	<div class="from-to"><spring:message code="routes.detailsOfTheRouteBuilt"/></div>                                        
                                	<div class="table-responsive">
										<table class="table table-stripped">
											<thead>
												<tr>
													<th>№</th>
													<th><spring:message code="routes.from"/></th>
													<th><spring:message code="routes.to"/></th>
													<th class="text-center"><spring:message code="routes.price"/></th>
													<th class="text-center"><spring:message code="routes.time"/></th>
													<th class="text-center"><spring:message code="routes.transport"/></th>
													<th class="text-center"><spring:message code="routes.delete"/></th>
												</tr>
											</thead>
											<tbody id="myFirst">
												<c:forEach items="${rCpDtoListResult}" var="rCpDtoResult">												
												<c:if test="${rCpDtoResult.id != 0 && rCpDtoResult.routePointAId != null}">																						
												<tr id ="${rCpDtoResult.id}">
													<td id="idOfDto">${rCpDtoResult.id}</td>
													<td>${rCpDtoResult.routePointAId.name}</td>
													<td>${rCpDtoResult.routePointBId.name}</td>
													<td class="text-center">${rCpDtoResult.price}</td>
													<td class="text-center">${rCpDtoResult.time}</td>
													<td class="text-center">${rCpDtoResult.transportId.name}</td>
													<td class="text-center"><span class="badge badge-danger">
														<a href="${pageContext.request.contextPath}/routes/resetingRoute/${rCpDtoResult.id}">
															<i class="glyphicon glyphicon-trash" data-tooltip="<spring:message code="routes.deleteRouteConnection"/>"></i>
														</a>
													</span></td>
												</tr>
												</c:if>
												</c:forEach>
											</tbody>
										</table>
									</div>
                            	</div>                                                     
                            </div>
                            <h3><spring:message code="routes.youBuiltRoute"/></h3>              
                            <ul class="list-group">
                            	<li id="detailsOfRoute" class="list-group-item-route">                            	                         		  	 
                            		<c:forEach items="${rCpDtoListResult}" var="rCpDtoResult">
                            			<c:if test="${rCpDtoResult.transportId == null}">
                            				<span class="badge badge-danger"><a href="${pageContext.request.contextPath}/routes/resetingRoute/${rCpDtoResult.id}">
                            					<i class="glyphicon glyphicon-trash" data-tooltip="<spring:message code="routes.deleteBuiltRoute"/>"></i>
                            				</a></span>
                           					<b><font size="5" color=green face="Comic Sans MS"><span>${rCpDtoResult.routePointBId.name}</span></font></b> 					
                            			</c:if>
                            			<c:if test="${rCpDtoResult.transportId != null}">
                            				<img src="<c:url value="/resources/images/arrow_left.ico"/>">
                            				<b><font size="5" color=green face="Comic Sans MS"><span>${rCpDtoResult.routePointBId.name}</span></font></b>
                            			</c:if>
                            		</c:forEach>
                            	</li>
                    		</ul>                                          
                        </div>
                        <form:form id="saveRouteForm" class="form-horizontal" method="get" action="${pageContext.request.contextPath}/routes/savingRoute" 
                        		commandName="savedRouteEdit" role="form"> 
                       		<div class="box dark"> 
                       			<c:if test="${error == true}">
                       				<div class="alert alert-danger">
                       					<button type="button" class="close" data-dismiss="alert">&times;</button>
										<div class="alert-link text-center">
											<form:errors path="name"/>
										</div>
									</div>  
								</c:if>   
                                <div class="row">
                                    <div class="col-md-2 col-sm-10 col-xs-10 text-right">
										<span style="padding: 7px 0; display: inline-block; font-weight: bold;"><spring:message code="routes.theNameOfTheRoute"/></span>
									</div>
									<div class="col-md-9 colsm-2 col-xs-2">
                                    	<input id="idOfSavedRouteInput" type="text" class="form-control" hidden="true" name="id" value="${savedRouteEdit.id}">
                                    </div>
                                    <div class="col-md-3 colsm-2 col-xs-2 text-center">  
                                    	<div hidden="true" id="divHideValidator"><span id="validatorInfo" class="hint"></span></div>
                                    	<form:input id="nameOfSavedRoute" type="text" class="form-control" path="name" name="name" 
                                    			placeholder="" value="${savedRouteEdit.name}"/>							
                                    </div>
                                    <div class="col-md-3 col-sm-10 col-xs-10">
                                    	<span style="padding: 7px 0; display: inline-block; font-weight: bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <spring:message code="routes.routeCompany"/> &nbsp;
                                    	<c:if test="${savedRouteEdit.isCompanyRoute == true}">
                                    		<input type="checkbox" name="isCompanyRoute" checked>
                                    	</c:if>
                                    	<c:if test="${savedRouteEdit.isCompanyRoute != true}">
                                    		<input type="checkbox" name="isCompanyRoute">
                                    	</c:if>
                                    	</span>
                                    </div> 
                                    <div class="col-md-4">
                                    	<button id="saveRoute" type="button" class="btn btn-success btn-sm" data-toggle="modal" data-target="#modalSave">
                                    		<spring:message code="routes.saveRoute"/>
                                    	</button> 
                                    	<button id="clearForm" type="button" class="btn btn-danger btn-sm" hidden="true"><spring:message code="routes.cancelEditing"/></button> 
                                    </div>                                                            
                                </div>                                
                                <!-- Modal for saving of SavedRoute-->				   
								<div class="modal fade" id="modalSave" tabindex="-1">
     								<div class="modal-dialog-info">
     					 				<div class="modal-content">
     					 					<div class="modal-header">
        										<button type="button" class="close" data-dismiss="modal">&times;</button>
        										<p>
       										</div>       										
       										<div id="message" class="modal-body" align="center"><spring:message code="routes.reallySaveRoute"/></div>       										
      										<div class="modal-footer-info">
        										<button id="buttonModalSave" value="${savedRouteEdit.id}" type="submit" class="btn btn-success btn-sm" data-dismiss="">
        											<spring:message code="routes.ok"/>
        										</button>
       										</div>
      									</div>
     								</div>
    							</div>
                        	</div>
                    	</form:form> 
					</div>					
                </div>
           	</div>
        </div>
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
								<th id="companyOfSR" class="text-center"><spring:message code="routes.routeCompany"/> <span class="glyphicon glyphicon-sort" data-tooltip="<spring:message code="routes.sort"/>"></span></th>
								<th class="text-center"><spring:message code="routes.edit"/></th>
								<th class="text-center"><spring:message code="routes.delete"/></th>
							</tr>
						</thead>						
						<tbody id="change">							
							<c:forEach items="${sRDtoList}" var="sRDto">
								<tr style="" id="${sRDto.id}" height="${sRDto.name}" background="${sRDto.price}" onblur="${sRDto.time}" onabort="${sRDto.modificationTime}" 
										lang="${sRDto.startPoint.name} - ${sRDto.finishPoint.name}" align="${sRDto.finishPoint.name} - ${sRDto.startPoint.name}"
										oncanplay="${sRDto.isCompanyRoute}">
									<td id="infoOfSavedRoute" align="${sRDto.name}" lang="${sRDto.id}">${sRDto.name}</td>
									<td id="pointsOfRoute" >${sRDto.startPoint.name} - ${sRDto.finishPoint.name}<i id="${sRDto.finishPoint.name} - ${sRDto.startPoint.name}"></i> 
										<i id="${sRDto.id}" lang="${sRDto.startPoint.id}" onblur=true class="glyphicon glyphicon-zoom-in moreAboutSavedRoute green" 
												data-toggle="modal" data-target="#modalMore" data-tooltip="<spring:message code="routes.viewDetailsRoute"/>">											
										</i>
									</td>
									<td class="text-center">${sRDto.price}</td>
									<td class="text-center">${sRDto.time}</td>
									<td class="text-center">${sRDto.modificationTime}</td>									
									<td class="text-center">
                                    	<c:if test="${sRDto.isCompanyRoute}">
                                           	<i id="${sRDto.id}" class="change glyphicon glyphicon-ok-circle green" data-tooltip="<spring:message code="routes.removeDestination"/>"></i>
                                        </c:if>
                                        <c:if test="${!sRDto.isCompanyRoute}">
                                        	<i id="${sRDto.id}" class="change glyphicon glyphicon-ban-circle red" data-tooltip="<spring:message code="routes.assignRouteCompany"/>"></i>
                                        </c:if>
                                    </td>                                    
									<td class="text-center"><span class="badge badge-warning">
										<a id="${sRDto.id}" lang="${sRDto.name}" tabindex="${sRDto.startPoint.id}" class="editSavedRoute" data-tooltip="<spring:message code="routes.editRoute"/>">
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
           						<h3 class="modal-title" id="DeleteTransport"><spring:message code="routes.deleting"/></h3>
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
<i id="deleteBuiltRoute" data-tooltip="<spring:message code="routes.deleteBuiltRoute"/>"></i>
<i id="deleteRouteConnection" data-tooltip="<spring:message code="routes.deleteRouteConnection"/>"></i>
<i id="editingRoute" data-tooltip="<spring:message code="routes.editingRoute"/>"></i>
<i id="alreadyEditingRoute" data-tooltip="<spring:message code="routes.alreadyEditingRoute"/>"></i>
<i id="buildRoute" data-tooltip="<spring:message code="routes.buildRoute"/>"></i>
<i id="editRoute" data-tooltip="<spring:message code="routes.editRoute"/>"></i>
<i id="partsSavedRoute" data-tooltip="<spring:message code="routes.partsSavedRoute"/>"></i>
<i id="reallyDeleteSaveRoute" data-tooltip="<spring:message code="routes.reallyDeleteSaveRoute"/>"></i>
<i id="assignRouteCompany" data-tooltip="<spring:message code="routes.assignRouteCompany"/>"></i>
<i id="removeDestination" data-tooltip="<spring:message code="routes.removeDestination"/>"></i>
<i id="validationNotExistRoute" data-tooltip="<spring:message code="routes.validationNotExistRoute"/>"></i>
<i id="validationEnterName" data-tooltip="<spring:message code="routes.validationEnterName"/>"></i>
<i id="validationSymbolNumbers" data-tooltip="<spring:message code="routes.validationSymbolNumbers"/>"></i>
<i id="validationUpperLetter" data-tooltip="<spring:message code="routes.validationUpperLetter"/>"></i>
<i id="validationErrorEnter" data-tooltip="<spring:message code="routes.validationErrorEnter"/>"></i>
<i id="validationNonUnique" data-tooltip="<spring:message code="routes.validationNonUnique"/>"></i>
<i id="reallySaveRoute" data-tooltip="<spring:message code="routes.reallySaveRoute"/>"></i>
<i id="previous" data-tooltip="<spring:message code="routes.previous"/>"></i>
<i id="next" data-tooltip="<spring:message code="routes.next"/>"></i>
<i id="sort" data-tooltip="<spring:message code="routes.sort"/>"></i>
<i id="sortDescending" data-tooltip="<spring:message code="routes.sortDescending"/>"></i>
<i id="sortAscending" data-tooltip="<spring:message code="routes.sortAscending"/>"></i>
<i id="enterNameOfRoute" data-tooltip="<spring:message code="routes.enterNameOfRoute"/>"></i>