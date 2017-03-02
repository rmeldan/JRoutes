<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script src="<c:url value="/resources/js/jQuery.js" />"></script>

<script>
	$(document)
			.ready(
					function start() {
						document.getElementById("validDepartCountry").style.display = "none";
						document.getElementById("validDepartCity").style.display = "none";
						document.getElementById("validArriveCountry").style.display = "none";
						document.getElementById("validArriveCity").style.display = "none";
						transfer = document.getElementById('optionsRadios2');
						if (transfer.checked == true) {
							transferDiv = document
									.getElementById('routeTransferId');
							transferDiv.style.display = 'none';
						} else {
							transferDiv = document
									.getElementById('routeTransferId');
							transferDiv.style.display = 'block';
						}
						$('#findButtonId')
								.click(
										function() {
											combo1 = document
													.getElementById('departCountryId');
											if (combo1.options[combo1.selectedIndex].value == "0") {
												showLabel("validDepartCountry");
											}
											combo2 = document
													.getElementById('departCityId');
											if (combo2.options[combo2.selectedIndex].value == "0") {
												showLabel("validDepartCity");
											}
											combo3 = document
													.getElementById('arriveCountryId');
											if (combo3.options[combo3.selectedIndex].value == "0") {
												showLabel("validArriveCountry");
											}
											combo4 = document
													.getElementById('arriveCityId');
											if (combo4.options[combo4.selectedIndex].value == "0") {
												showLabel("validArriveCity");
											}
											if (combo1.options[combo1.selectedIndex].value == "0"
													|| combo2.options[combo2.selectedIndex].value == "0"
													|| combo3.options[combo3.selectedIndex].value == "0"
													|| combo4.options[combo4.selectedIndex].value == "0") {
												return false;
											}

										});
						function showLabel(label) {
							document.getElementById(label).style.display = "block";
							setTimeout(function() {
								hideLabel(label)
							}, 3000);
						}
						function hideLabel(label) {
							document.getElementById(label).style.display = "none";
						}
					});
	$(document).ready(function() {
		$('#optionsRadios2').click(function() {
			transferDiv = document.getElementById('routeTransferId');
			comboCountry = document.getElementById('transferCountryId');
			comboCity = document.getElementById('transferCityId');
			comboCountry.selectedIndex = 0;
			//ClearCombo(comboCountry);
			ClearCombo(comboCity);
			transferDiv.style.display = 'none';
		})
		function ClearCombo(combo) {
			while (combo.length > 0) {
				combo.remove(combo.length - 1);
			}
			combo.options[0] = new Option('Оберіть пункт', '0');
		}
		$('#optionsRadios1').click(function() {
			transferDiv = document.getElementById('routeTransferId');
			transferDiv.style.display = 'block';
		})
	});
</script>

<div id="carousel-generic" class="carousel slide" data-ride="carousel">
	<!-- Indicators -->
	<ol class="carousel-indicators">
		<li data-target="#carousel-example-generic" data-slide-to="0"
			class="active"></li>
		<li data-target="#carousel-example-generic" data-slide-to="1"></li>
		<li data-target="#carousel-example-generic" data-slide-to="2"></li>
	</ol>

	<!-- Wrapper for slides -->
	<div class="carousel-inner">
		<div class="item active">
			<img src="<c:url value="/resources/images/train.jpg" />" alt="train"
				class="img-responsive">
		</div>
		<div class="item">
			<img src="<c:url value="/resources/images/bus.jpg" />" alt="bus"
				class="img-responsive">
		</div>
		<div class="item">
			<img src="<c:url value="/resources/images/plane.jpg" />" alt="plane"
				class="img-responsive">
		</div>
	</div>
	<!-- Controls -->
	<a class="left carousel-control" href="#carousel-generic"
		data-slide="prev"> <span class="glyphicon glyphicon-chevron-left"></span>
	</a> <a class="right carousel-control" href="#carousel-generic"
		data-slide="next"> <span class="glyphicon glyphicon-chevron-right"></span>
	</a>
</div>

<form action="findRoute" method="GET" role="form">
	<div class="cart-search col-lg-6 col-md-6 col-sm-8 col-xs-8">
		<h2 class="route">
			<i class="glyphicon glyphicon-map-marker"></i>
			<spring:message code="index.findRoute" />
		</h2>
		<div class="row">
			<div class="col-md-6 col-sm-6">
				<div class="from-to">
					<spring:message code="index.departure" />
				</div>
				<div class="form-group">
					<font id="validDepartCountry" size="2" color="red"> <spring:message
							code="index.validator" />
					</font> <select name="departCountry" class="form-control"
						id="departCountryId" >
						<option value="0"><spring:message
								code="index.chooseCountry" /></option>
						<c:forEach items="${countries}" var="country">
							<option value="${country.id}">${country.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<font id="validDepartCity" size="2" color="red"><spring:message
							code="index.validator" /></font> <select name="departCity"
						class="form-control" id="departCityId">
						<option value="0"><spring:message code="index.chooseCity" /></option>
					</select>
				</div>
			</div>
			<div class="col-md-6 col-sm-6">
				<div class="from-to">
					<spring:message code="index.arrival" />
				</div>
				<div class="form-group">
					<font id="validArriveCountry" size="2" color="red"><spring:message
							code="index.validator" /></font> <select name="arriveCountry"
						class="form-control" id="arriveCountryId">
						<option value="0"><spring:message
								code="index.chooseCountry" /></option>
						<c:forEach items="${countries}" var="country">
							<option value="${country.id}">${country.name}</option>
						</c:forEach>
					</select>
				</div>
				<font id="validArriveCity" size="2" color="red"><spring:message
							code="index.validator" /></font> <select name="arriveCity" class="form-control"
					id="arriveCityId">
					<option value="0"><spring:message
							code="index.chooseCity" /></option>
				</select>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6 col-sm-6">
				<div class="title"><spring:message
							code="index.onlyByTransport" /></div>
				<c:forEach items="${transportList}" var="transportList"
					varStatus="status">
					<div class="checkbox">
						<label> <input name="transport${status.index}"
							type="checkbox" value='${transportList.name}'>
							${transportList.name}
						</label>
					</div>
				</c:forEach>
			</div>
			<div class="col-md-6 col-sm-6">
				<div class="title"><spring:message
							code="index.price" /></div>
				<select name="price" class="form-control">
					<option value="0"><spring:message
							code="index.priceAny" /></option>
					<option value="200"><spring:message
							code="index.priceB200" /></option>
					<option value="500"><spring:message
							code="index.priceB500" /></option>
					<option value="1000"><spring:message
							code="index.priceB1000" /></option>
					<option value="2000"><spring:message
							code="index.priceB2000" /></option>
					<option value="0"><spring:message
							code="index.priceM2000" /></option>
				</select>
			</div>
			<div class="col-md-6 col-sm-6">
				<div class="title"><spring:message
							code="index.duration" /></div>
				<select name="time" class="form-control">
					<option value="0"><spring:message
							code="index.priceAny" /></option>
					<option value="120"><spring:message
							code="index.durationL2" /></option>
					<option value="360"><spring:message
							code="index.durationL6" /></option>
					<option value="720"><spring:message
							code="index.durationL12" /></option>
					<option value="1440"><spring:message
							code="index.durationL24" /></option>
					<option value="0"><spring:message
							code="index.durationM24" /></option>
				</select>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6 col-sm-6">
				<div class="title"><spring:message
							code="index.layovers" /></div>
				<div class="radio">
					<label> <input type="radio" path="includeLayout" value ="true" name="includeLayout"
						id="optionsRadios1" > <spring:message
							code="index.includeStops" />
					</label>
				</div>
				<div class="radio">
					<label> <input type="radio" name="includeLayout"
						id="optionsRadios2" value="false" checked>  <spring:message
							code="index.nonStop" />
					</label>
				</div>
			</div>
			<div class="col-md-6 col-sm-6" id="routeTransferId"
				style="display: none">
				<div class="form-group">
					<div class="title"><spring:message
							code="index.layover" /></div>
					<select name="transferCountry" class="form-control"
						id="transferCountryId">
						<option value="0"><spring:message
							code="index.chooseCountry" /></option>
						<c:forEach items="${countries}" var="country">
							<option value="${country.id}">${country.name}</option>
						</c:forEach>
					</select>
				</div>
				<select name="transferCity" class="form-control" id="transferCityId">
					<option value="0"><spring:message
							code="index.chooseCity" /></option>
				</select>
			</div>
		</div>
		<div>
			<!--  <a href="${pageContext.request.contextPath}/showFilteredRoutes"
				type="submit" class="btn btn-success btn-sm"><i
				class="glyphicon glyphicon-check"></i> Розрахувати</a> -->
			<button type="submit" id="findButtonId"
				class="btn btn-success btn-sm">
				<i class="glyphicon glyphicon-check"></i> <spring:message
							code="index.find" />
			</button>
			<button type="reset" class="btn btn-danger btn-sm">
				<i class="glyphicon glyphicon-refresh"></i> <spring:message
							code="index.clean" />
			</button>
		</div>
	</div>
</form>
<div id="grey-back">
	<div class="container">
	<h1 class="text-center">JRoutes - <spring:message code="index.labelH1" /></h1>
	<div class="row">
		<div class="col-md-4">
			<div class="row">
				<div class="col-sm-5 col-xs-5">
					<div class="text-right">
						<i class="glyphicon glyphicon-user font-size"></i>
					</div>
				</div>
				<div class="col-sm-7 col-xs-7">
					<p class="grey"><spring:message code="index.needRegister" /></p>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="row">
				<div class="col-sm-5 col-xs-5">
					<div class="text-right">
						<i class="glyphicon glyphicon-map-marker font-size"></i>
					</div>
				</div>
				<div class="col-sm-7 col-xs-7">
					<p class="grey"><spring:message code="index.look" /></p>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="row">
				<div class="col-sm-5 col-xs-5">
					<div class="text-right">
						<i class="glyphicon glyphicon-home font-size"></i>
					</div>
				</div>
				<div class="col-sm-7 col-xs-7">
					<p class="grey"><spring:message code="index.keep" /></p>
				</div>
			</div>
		</div>
	</div>
</div>
</div>


<div class="modal fade" id="myModal-login" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h2 class="modal-title" id="myModalLabel">Увійти</h2>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<label for="login" class="col-sm-2 control-label">E-mail</label>
						<div class="col-sm-10">
							<input type="email" class="form-control" id="E-mail"
								placeholder="E-mail">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword-login" class="col-sm-2 control-label">Пароль</label>
						<div class="col-sm-10">
							<input type="password" class="form-control"
								id="inputPassword-login" placeholder="Пароль">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<div class="checkbox">
								<label> <input type="checkbox"> Запам'ятати мене
								</label>
							</div>
							<a href="#">Нагадати пароль</a>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<a href="user.html" class="btn btn-success">Увійти</a>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="myModal-register" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel-register"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h2 class="modal-title" id="myModalLabel-register">Реєстрація</h2>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<label for="inputEmail" class="col-sm-2 control-label">Email</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="inputEmail"
								placeholder="Email">
						</div>
					</div>
					<div class="form-group">
						<label for="inputName" class="col-sm-2 control-label">Ім'я</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="inputName"
								placeholder="Ім'я">
						</div>
					</div>
					<div class="form-group">
						<label for="inputLastName" class="col-sm-2 control-label">Прізвище</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="inputLastName"
								placeholder="Прізвище">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-sm-2 control-label">Пароль</label>
						<div class="col-sm-10">
							<input type="password" class="form-control" id="inputPassword"
								placeholder="Пароль">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPasswordConfirm" class="col-sm-2 control-label">Повторіть
							пароль</label>
						<div class="col-sm-10">
							<input type="password" class="form-control"
								id="inputPasswordConfirm" placeholder="Пароль">
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-danger">Відмінити</button>
				<button type="button" class="btn btn-success">Зареєструватись</button>
			</div>
		</div>
	</div>
</div>

