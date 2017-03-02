<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="container">
	<h3 class="border-botton-green"><spring:message code="findRoute.choose" /></h3>
	<div class="table-responsive">
		<table class="table table-stripped" id="contentRoute">
			<thead>
				<tr>
					<th><spring:message code="findRoute.departure" /></th>
					<th><spring:message code="findRoute.arrival" /></th>
					<th id="transportSortId"><spring:message code="findRoute.transport" /> <span
						class="glyphicon glyphicon-sort"></span>
					</th>
					<th id="durationSortId"><spring:message code="findRoute.duration" /> <span
						class="glyphicon glyphicon-sort"></span></th>
					<th id="valueSortId"><spring:message code="findRoute.price" /> <span
						class="glyphicon glyphicon-sort"></span></th>
					<th><spring:message code="findRoute.details" /></th>
					<th><spring:message code="findRoute.save" /></th>
				</tr>
			</thead>
			<tbody id="change">
				<c:forEach items="${routeList}" var="routeList">
					<tr id="${routeList.id}" title="${routeList.transportId.name}"
						background="${routeList.price}" onblur="${routeList.time}">
						<td>${depart.name}</td>
						<td>${arrive.name}</td>
						<td>${routeList.transportId.name}</td>
						<td>${routeList.time}</td>
						<td>${routeList.price}</td>
						<c:choose>
							<c:when test="${routeList.fromRoute=='true'}">
								<td class="more">										
									<i id="${routeList.savedRouteId.id}" lang="${routeList.routePointAId.id}" onblur=true class="glyphicon glyphicon-zoom-in moreAboutSavedRoute" 
										data-toggle="modal" data-target="#modalMore"></i>
								</td>
								<td><span class="badge badge-danger"><a
										href="${pageContext.request.contextPath}/findRoute/saveRoute/${routeList.fromRoute}/${routeList.savedRouteId.id}/nameIt"><i
											class="glyphicon glyphicon-floppy-disk"></i></a></span></td>
							</c:when>

							<c:otherwise>
								<td class="more"><a><i
										class="glyphicon glyphicon-ban-circle red"></i></a></td>

								<td><span class="badge badge-danger"><a
										href="${pageContext.request.contextPath}/findRoute/saveRoute/${routeList.fromRoute}/${routeList.routeConnectionId.id}/nameIt"><i
											class="glyphicon glyphicon-floppy-disk"></i></a></span></td>
							</c:otherwise>
						</c:choose>

					</tr>
				</c:forEach>
				<c:forEach items="${sRDtoList}" var="sRDto">
					<tr id="${sRDto.id}Search" title="different" background="${sRDto.price}" onblur="${sRDto.time}">
						<td>${sRDto.startPoint.name}</td>
						<td>${sRDto.finishPoint.name}</td>
						<td>different</td>						
						<td>${sRDto.time}</td>
						<td>${sRDto.price}</td>
						<td id="pointsOfRoute" >
							<i id="${sRDto.id}" lang="${sRDto.startPoint.id}" onblur=false class="glyphicon glyphicon-zoom-in moreAboutSavedRoute green"
								 data-toggle="modal" data-target="#modalMore"></i>
						</td>
						<td><span class="badge badge-danger"><a href="${pageContext.request.contextPath}/foundRouteSave/${sRDto.id}/">
							<i class="glyphicon glyphicon-floppy-disk"></i></a></span>
						</td>									
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="paginationRoute"></div>
	<a href="${pageContext.request.contextPath}">
		<button type="button" class="btn btn-default btn-sm btn-danger"
			data-dismiss="modal">
			<i class="glyphicon glyphicon-remove"></i> <spring:message code="findRoute.back" />
		</button>
	</a>
</div>

<!-- Modal for details about of SavedRoute-->
<div class="modal fade" id="modalMore" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h3 class="modal-title"><spring:message code="routes.partsSavedRoute"/></h3>
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