<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script type="text/javascript" src="<c:url value="/resources/js/jQuery.js" />"></script>



<script>

function localiz(){
var dublicateErrLocal = '<spring:message code="routeconnectionPage.dublicateErrLocal"/>';
var confirmMessageLocal = '<spring:message code="routeconnectionPage.deleteConfirm" />';
var formatErrLocal = '<spring:message code="routeconnectionPage.formatErrLocal" />';
var emptyErrLocal = '<spring:message code="routeconnectionPage.emptyErrLocal" />';
var mass = [dublicateErrLocal,confirmMessageLocal,formatErrLocal,emptyErrLocal];
return mass;
}


</script>
<div class="container adminHome">
	<div id="route-connection">
		<div class="row">
			<div class="col-md-8">
				<div class="row">
					<div class="col-md-10 col-sm-10 col-xs-10 text-right">
						<span style="padding: 5px 0; display: inline-block;"><spring:message code="routeConnection.itemsPerPage" /></span>
					</div>
					<div class="col-md-2 colsm-2 col-xs-2">
						<select name="itemsPerPageRC" class="form-control input-sm"
							id="itemsPerPageRC">
							<option value="2" <c:if test="${perPage==2}">selected </c:if>>2</option>
							<option value="5" <c:if test="${perPage==5}">selected </c:if>>5</option>
							<option value="10" <c:if test="${perPage==10}">selected </c:if>>10</option>
							<option value="20" <c:if test="${perPage==20}">selected </c:if>>20</option>
							<option value="40" <c:if test="${perPage==40}">selected </c:if>>40</option>
							<option value="100" <c:if test="${perPage==100}">selected </c:if>>100</option>
						</select>
					</div>
				</div>
				<div class="table-responsive">
					<table class="table table-stripped" id='contentRC'>
						<thead>
							<tr>
								<th>
								<div class="input-group">
							      <input type="text" class="form-control input-sm col-md-2" id="filter_fromCity" placeholder="filter"/>
							      <span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></i></span>
							    </div>
								
								<br><spring:message code="routeConnection.from" />								
								</th>
								<th>
								<div class="input-group">
							      <input type="text" class="form-control input-sm" id="filter_toCity" placeholder="filter">
							      <span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></i></span>
							    </div>
								
								<br><spring:message code="routeConnection.to" /></th>
								<th><spring:message code="routeConnection.price" />
								</th>
								<th><spring:message code="routeConnection.time" />
								</th>
								<th><spring:message code="routeConnection.transport" /></th>
								<th><spring:message code="routeConnection.isBlocked" /></th>
								<th><spring:message code="routeConnection.options" /></th>
							</tr>
						</thead>
						<tbody>

							<c:forEach items="${routeConnectionList}" var="routeConnectionDto">
								<tr id="valuesId">
									<%-- <td>${routeConnectionDto.countryA}</td>
									<td>${routeConnectionDto.countryB}</td>
									<td>${routeConnectionDto.price}</td>
									<td>${routeConnectionDto.time}</td>
									<td>${routeConnectionDto.transport}</td> --%>
									<td id="firstCityId" >${routeConnectionDto.routePointAName}</td>
									<td id="secondCityId">${routeConnectionDto.routePointBName}</td>
									<td>${routeConnectionDto.price}</td>
									<td>${routeConnectionDto.time}</td>
									<td id="transportListId" >${routeConnectionDto.transportName}</td>
									<td><input type="checkbox" name="isBlocked"
										<c:if test="${routeConnectionDto.isBlocked==true}">checked </c:if>>
									</td>
									<%-- <td><a href="delete/${routeConnectionDto.id}">delete</a></td> --%>
									<td style="width:10%;">
										<span class="badge badge-warning">
											<a href="${pageContext.request.contextPath}/edit/${routeConnectionDto.id}">
												<i  class="glyphicon glyphicon-edit"></i>
											</a>
										</span> 
										<span class="badge badge-danger">
											<a class="deleteRCC" id="${pageContext.request.contextPath}/delete/${routeConnectionDto.id}"> <i class="glyphicon glyphicon-trash"></i></a>
										</span>
										
									</td>
								</tr>
							</c:forEach>

						</tbody>

					</table>
					<div id="paginationRC"></div>

				</div>
			</div>
			<div class="col-md-4">
				<h2 class="border-bottom-green">
					<i class="glyphicon glyphicon-ok"></i> <spring:message code="routeConnection.connectCities" />
				</h2>

				<!--  Begin form for RouteConnection -->

				<form:form 
					action="${pageContext.request.contextPath}/addRouteConnection"
					commandName="dto" method="POST" role="form">
					<div  id="inputagId"></div>
					<div id="hiddenId">
					<c:if test="${not empty successResult}">
						<div class="alert alert-success"><spring:message code="${successResult}" /></div>
					</c:if>
					<c:if test="${not empty dangerResult}">
						<div class="alert alert-danger"><spring:message code="${dangerResult}" /></div>
					</c:if>
					</div>
					<div class="row">
						<div class="col-md-6 col-sm-6">
							<div class="from-to"><spring:message code="routeConnection.from" /></div>
							<div class="form-group">

								<select name="countryA" class="form-control" id="fromCountry">
									<option value="">Select country</option>
									<c:forEach items="${countryList}" var="country">
										<option value="${country.id}"
											<c:if test="${countryAId==country.id}">selected </c:if>>${country.name}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<font color="red"><form:errors path="routePointA" /></font>
								 <select name="routePointA" class="form-control" id="fromCity">
									<c:forEach items="${routePointFrom}" var="routePoint">
										<option value="${routePoint.id}" id="${routePoint.name}"
											<c:if test="${pointAId==routePoint.id}">selected </c:if>>${routePoint.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-md-6 col-sm-6">
							<div class="from-to"><spring:message code="routeConnection.to" /></div>
							<div class="form-group">
								<select name="countryB" class="form-control" id="toCountry">
									<option value="">Select country</option>
									<c:forEach items="${countryList}" var="country">
										<option value="${country.id}"
											<c:if test="${countryBId==country.id}">selected </c:if>>${country.name}</option>
									</c:forEach>
								</select>
							</div>
							<font color="red"><form:errors path="routePointB" /></font>
							<form:select path="routePointB" class="form-control" id="toCity">
								<c:forEach items="${routePointTo}" var="routePoint">
									<option value="${routePoint.id}"
										<c:if test="${pointBId==routePoint.id}">selected</c:if>>${routePoint.name}</option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-md-6">
								<div class="title"><spring:message code="routeConnection.price" /></div>

								<font color="red"><form:errors path="price" /></font>
								<form:input id="priceId" class="form-control" path="price"
									placeholder="price" value="${price}"/>
							</div>
							<div class="col-md-6">
								<div class="title"><spring:message code="routeConnection.time" /></div>
								<font color="red"><form:errors path="time" /></font>
								<form:input id="timeId" class="form-control" path="time" placeholder="time" value="${time}"/>
							</div>
						</div>
					</div>
					<div class="form-group">
					<div class="title"><spring:message code="routeConnection.transport" /></div>
						<select name="transport" id="selTarnspId" class="form-control">
							<c:forEach items="${transportList}" var="transport">
								<option value="${transport.id}"
									<c:if test="${transportId==transport.id}">selected </c:if>>${transport.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">

						<div class="text-right">
							<button id="submitButtonId" type="submit" class="btn btn-success btn-sm" >
								<i class="glyphicon glyphicon-ok"></i> <spring:message code="routeConnection.add" />
							</button>
						</div>
					</div>
				</form:form>

				<!--  End form for RouteConnection -->

			</div>
		</div>
	</div>
</div>




