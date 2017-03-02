<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script src="<c:url value="/resources/js/jQuery.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/citiesValidation.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/citiesAjax.js" />"></script>

<script>

$(document).ready(function(){

		$('#find').keyup(function() {
							
			var text = $(this).val();
			
			var filter = $('#search').val();
			//alert(filter);
			$.ajaxSetup({cache: false});
			$
			.ajax({
				url : 'getElement',
				datatype : 'json',
				data : ({
					values :text+" "+ filter
				}),
				success : function(resultDo) {
					//alert(resultDo);
					var response = eval('('
							+ resultDo
							+ ')');
					resultDo.empty;
					var table = $("#rpTable tbody");
					$("#rpTable tbody").empty();
					if(table==null)
						alert("error");
					
$.each(response,function(i,item){
			
						var isChecked = "";
																				if (item.isBlocked == true) {
																					isChecked = "checked";
																				}
																				var editLink = '/jroutes/cities/editForm/'
																						+ item.id;
																				
																				var edit = '<span class="badge badge-warning"><a href="'+editLink+'" class="glyphicon glyphicon-edit"></a></span>';
																				var deleteRoutePoint = '<span class="badge badge-danger"> <a class="del-a"  id="'+item.id+'" name="'+item.name+'" href="#" ><i class="glyphicon glyphicon-trash"></i></a></span>';
							
			
							
						//alert(item.name+" "+item.country+" "+item.isBlocked);

					table.append(
						
					
							"<tr><td>"
							+ item.name+
						 	"</td><td>"
							  +item.country+
							"</td>"
							+ " <td><input type='checkbox' name='isBlocked'"+isChecked+">"
							+ "</td><td>"
							
							+ edit
							
							+ deleteRoutePoint+
							
							
							
							
							"</td></tr>");
					
					
					});
					
				}
				
					
								
				});
			
			
		});
});

</script>

<div class="container adminHome">
	<div id="cities">
		<div class="row">
			<div class="col-md-8">
				<div class="row margin-bottom-10">
					<div class="col-md-6">
						<form action="find" method="POST" role="form">
							<input type="text" id="find" class="form-control" name="name" placeholder="Пошук">	
						</form>
					</div>
					<div class="col-md-6">
						<select name="search" class="form-control" id="search">
							<option value="City" 
								<c:if test="${perPage==5}">selected </c:if>>City</option>
							<option value="Country" 
								<c:if test="${perPage==10}">selected </c:if>>Country</option>
						</select>
					</div>
				</div>
				<div class="table-responsive">
					<table class="table table-stripped"  id='rpTable'>
						<thead>
							<tr>
								<th><spring:message code="cities.city" /></th>
								<th><spring:message code="cities.country" /></th>
								<th><spring:message code="cities.block" /></th>
								<th></th>
							</tr>
						</thead>
						<tbody>

							<c:forEach items="${routePointList}" var="routePoint">
								<tr>
									<td>${routePoint.name}</td>
									<td>${routePoint.country}</td>
									<td>
										<input type="checkbox" name="isBlocked"
										<c:if test="${routePoint.isBlocked == true}">checked
										</c:if>
										<c:if test="${routePoint.isBlocked == false}">unchecked
										</c:if>
										/>
									</td>
									<td>
										<span class="badge badge-warning">
											<a id = "${routePoint.id}" name = "${routePoint.name}" href="editForm/${routePoint.id}"><i class="glyphicon glyphicon-edit"></i></a></span>
										<span class="badge badge-danger"> 
                    						<a class="del-a"  id="${routePoint.id}" name="${routePoint.name}"   href="#" ><i class="glyphicon glyphicon-trash"></i></a></span>
									</td>
								</tr>
							</c:forEach>
						
						</tbody>
						
				</table>
				
				</div>
			</div>
			<div class="col-md-4">

				<h2 class="border-bottom-green">
					<i class="glyphicon glyphicon-ok"></i><spring:message code="cities.addCity" />
				</h2>

	

				<form action="addingRoutePoint" method="POST" role="form">
					<c:if test="${not empty result}">
						<div class="alert alert-success">${result}</div>
					</c:if>
					
					<c:if test="${not empty error}">
    					<div class="alert alert-danger">${error}</div>
					</c:if>

					<div class="form-group">
						<div class="col-md-12 col-sm-6">
							<div class="title"><spring:message code="cities.enterCity" /></div>
							<input type="text" id="textbox_id" class="form-control" name="name"
								placeholder="Назва">



							<div class="title"><spring:message code="cities.selectCountry" /></div>

							<select name="country" class="form-control">
								<c:forEach items="${countryList}" var="country">
									<option value="${country.id}">${country.name}</option>
									
								</c:forEach>
								
							</select>

						</div>

					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10 text-right">
							<button type="submit"  id="submit_id" class="btn btn-success btn-sm">
								<i class="glyphicon glyphicon-ok"></i>
								<spring:message code="cities.addCity" />
							</button>
						</div>
					</div>

				</form>


			</div>
		</div>
	</div>
</div>
