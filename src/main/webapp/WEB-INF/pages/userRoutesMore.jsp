<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="container">
	<h3 class="border-bottom-green"><spring:message code="findRouteMore.title" /></h3>
	<div class="table-responsive">
		<table class="table table-stripped">
			<thead>
				<tr>
					<th class="text-center">№</th>
					<th><spring:message code="findRouteMore.departure" /></th>
					<th><spring:message code="findRouteMore.arrival" /></th>
					<th class="text-center"><spring:message code="findRouteMore.duration" /></th>
					<th class="text-center"><spring:message code="findRouteMore.price" /></th>
					<th><spring:message code="findRouteMore.transport" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${routeList}" var="routeList">
					<tr>
						<td class="text-center">${routeList.id}</td>
						<td>${routeList.routePointAId.name}</td>
						<td>${routeList.routePointBId.name}</td>
						<td class="text-center">${routeList.time}</td>
						<td class="text-center">${routeList.price}</td>
						<td>${routeList.transportId.name}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<c:set var="routeList" value="${routeList}" />
	<!-- <a onClick=history.back()> <i
		class="glyphicon glyphicon-chevron-left">Back</i>
	</a> -->
	<button type="button" class="btn btn-default btn-sm btn-danger"
		data-dismiss="modal" onClick=history.back()>
		<i class="glyphicon glyphicon-arrow-left"></i> <spring:message code="findRouteMore.back" />
	</button>
</div>
