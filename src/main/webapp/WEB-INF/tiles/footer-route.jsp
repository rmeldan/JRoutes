<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="footer">
	<div class="container">
		<div class="text-center copyright">© Copyright 2014 Java-109</div>
	</div>
</div>

<script src="<c:url value="/resources/js/jQuery.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.js" />"></script>
<script src="<c:url value="/resources/js/jquery.simplePagination.js" />"></script>
<script src="<c:url value="/resources/js/jquery.autocomplete.min.js" />"></script>
<script>
	$(document).ready(function() {
		
		//додавання і видалення відрізків маршруту
		$('#addConnectForm').submit(function(){
			$('div.alert.alert-danger').attr({'hidden':true});
			$(this).find('.error').remove();
			if ($(this).find('select[name=id]').val() == '-1') {
				var chooseCity = $(this).find('option[id=chooseCity]').text();
				$(this).find('select[name=id]').before(
						'<div class="error red"><font size="3" color=red face="Comic Sans MS"> ' + chooseCity + ' !</font></div>');
			    return false;
			}
			$.ajax({
				url : $(this).attr('action'), // посилання куди відправляємо дані
				datatype : 'json',
				data : $(this).serialize(), // дані форми
				success : function(resultAdd) {
					var data = eval(resultAdd);
					if(data[0].transportId != null) {
						addingRoute(data[0]);			
					} else {
						var $span = $('<span>').attr({'class':'badge badge-danger'});
						var $a = $('<a>').attr({'href':'${pageContext.request.contextPath}/routes/resetingRoute/' + data[0].id});
						var deleteBuiltRoute = $("#deleteBuiltRoute").attr('data-tooltip');						
						var $i = $('<i>').attr({'class':'glyphicon glyphicon-trash', 'data-tooltip':deleteBuiltRoute});						
						$($span).appendTo("#detailsOfRoute");	
						$($a).appendTo("#detailsOfRoute > span");
						$($i).appendTo("#detailsOfRoute > span > a");
						tip();						
						$('#detailsOfRoute span a').click(function(){
							$.ajax({
								url : $(this).attr('href') + '/Ajax',
								datatype : 'json',
								success : function(resultRem) {
									var dataRem = eval(resultRem);
									countryComboBox = document.getElementById('countryComboId');
									clearCombo(countryComboBox);
									fillComboCountry(dataRem[2], countryComboBox);
									cityComboBox = document.getElementById('cityComboId');
									clearCombo(cityComboBox);
									fillComboCity(dataRem[1], cityComboBox);
									$('#myFirst').empty();
									$('#detailsOfRoute').empty();
									$("#tooltip").hide();
								}
							});
							return false;
						});
					}
					var $city = $('<font>').attr({'size':'5', 'color':'green', 'face':'Comic Sans MS'})
							.html("<b> " + data[0].routePointBId.name + " </b>");
					$($city).appendTo("#detailsOfRoute");
					countryComboBox = document.getElementById('countryComboId');
					clearCombo(countryComboBox);
					fillComboCountry(data[2], countryComboBox);
					cityComboBox = document.getElementById('cityComboId');
					clearCombo(cityComboBox);
					fillComboCity(data[1], cityComboBox);									
				}
			});
			return false;
		});		
		function addingRoute(route) {
			var $arrow = $('<img>').attr({'src': '<c:url value="/resources/images/arrow_left.ico"/>'});
			$($arrow).appendTo("#detailsOfRoute");	
			var $tr = $('<tr>').attr({'id':route.id});
			var $tdId = $('<td>').attr({'id':'idOfDto'}).text(route.id);
			var $tdA = $('<td>').text(route.routePointAId.name);
			var $tdB = $('<td>').text(route.routePointBId.name);
			var $tdPrice = $('<td>').attr({'class':'text-center'}).text(route.price);
			var $tdTime = $('<td>').attr({'class':'text-center'}).text(route.time);
			var $tdTransport = $('<td>').attr({'class':'text-center'}).text(route.transportId.name);
			var $tdReset = $('<td>').attr({'class':'text-center'});
			var $span = $('<span>').attr({'class':'badge badge-danger'});
			var $a = $('<a>').attr({'href':'${pageContext.request.contextPath}/routes/resetingRoute/' + route.id});
			var deleteRouteConnection = $("#deleteRouteConnection").attr('data-tooltip');
			var $i = $('<i>').attr({'class':'glyphicon glyphicon-trash', 'data-tooltip':deleteRouteConnection});			
			$($tr).appendTo("#myFirst");
			$($tdId).appendTo("#myFirst tr:last");
			$($tdA).appendTo("#myFirst tr:last");
			$($tdB).appendTo("#myFirst tr:last");
			$($tdPrice).appendTo("#myFirst tr:last");
			$($tdTime).appendTo("#myFirst tr:last");
			$($tdTransport).appendTo("#myFirst tr:last");
			$($tdReset).appendTo("#myFirst tr:last");
			$($span).appendTo("#myFirst tr:last td:last");	
			$($a).appendTo("#myFirst tr:last td:last span");
			$($i).appendTo("#myFirst tr:last td:last span a");
			tip();
			$('#myFirst tr#'+ route.id + ' td span a').click(function(){
				$.ajax({
					url : $(this).attr('href') + '/Ajax',
					datatype : 'json',
					success : function(resultRem) {						
						var dataRem = eval(resultRem);						
						countryComboBox = document.getElementById('countryComboId');
						clearCombo(countryComboBox);
						fillComboCountry(dataRem[2], countryComboBox);
						cityComboBox = document.getElementById('cityComboId');
						clearCombo(cityComboBox);
						fillComboCity(dataRem[1], cityComboBox);
						for (var i = 0; route.id <= $('#myFirst tr:last').attr('id'); i++) {
							$('#myFirst tr:last').remove();
							$('#detailsOfRoute b:last').remove();
							$('#detailsOfRoute img:last').remove();
						}
						$("#tooltip").hide();
					}
				});
				return false;
			});	
		}
		
		//редагування збереженого маршруту
		$('a.editSavedRoute').click(function(){
			$('#addConnectForm').find('.error').remove();
			$('div.alert.alert-danger').attr({'hidden': true});
			$('#myFirst').empty();
			$('#detailsOfRoute').empty();
			$("#collapseOne").removeClass("collapse");
			var nameOfSavedRoute = $(this).prop('lang');
			var idOfSavedRoute = $(this).prop('id');			
			var startPoint = $(this).prop('tabindex');
			var editingRoute = $("#editingRoute").attr('data-tooltip');	
			$('#change tr').attr('style', '');					
			$('#change tr#' + idOfSavedRoute).attr('style', 'background-color: rgba(129,208,177,.3)');
		    $('#collapseRoute').text(editingRoute + ' \"'+ nameOfSavedRoute  + '\"');
		    $('#nameOfSavedRoute').prop('value', nameOfSavedRoute);
		    $('#buttonModalSave').prop('value', idOfSavedRoute);
		    $('#idOfSavedRouteInput').prop('value', idOfSavedRoute);
		    $('#clearForm').prop('hidden', false);	
		    var editRoute = $("#editRoute").attr('data-tooltip');
		    $('a.editSavedRoute').each(function() {
		    	 $(this).attr('data-tooltip',editRoute);
		    });
		    var alreadyEditingRoute = $("#alreadyEditingRoute").attr('data-tooltip');
		    $(this).attr('data-tooltip',alreadyEditingRoute);
		    $("#tooltip").hide();
		    if($("i#" + idOfSavedRoute + ".change").hasClass("glyphicon-ok-circle")) {
		    	$('input[name=isCompanyRoute]').prop('checked', true);				
			} else {				
				$('input[name=isCompanyRoute]').prop('checked', false);				
			}
			$.ajax({
				url : 'editingSavedRoute',
				datatype : 'json',
				data : ({
					id : idOfSavedRoute,
					rpId : startPoint
				}),
				success : function(resultAdd) {
					var data = eval(resultAdd);
					var routes = data[0];
					var $span = $('<span>').attr({'class':'badge badge-danger'});
					var $a = $('<a>').attr({'href':'${pageContext.request.contextPath}/routes/resetingRoute/' + routes[0].id});					
				    var deleteBuiltRoute = $("#deleteBuiltRoute").attr('data-tooltip');
					var $i = $('<i>').attr({'class':'glyphicon glyphicon-trash', 'data-tooltip':deleteBuiltRoute});
					tip();
					$($span).appendTo("#detailsOfRoute");	
					$($a).appendTo("#detailsOfRoute > span");
					$($i).appendTo("#detailsOfRoute > span > a");
					$('#detailsOfRoute span a').click(function(){
						$.ajax({
							url : $(this).attr('href') + '/Ajax',
							datatype : 'json',
							success : function(resultRem) {
								var dataRem = eval(resultRem);
								countryComboBox = document.getElementById('countryComboId');
								clearCombo(countryComboBox);
								fillComboCountry(dataRem[2], countryComboBox);
								cityComboBox = document.getElementById('cityComboId');
								clearCombo(cityComboBox);
								fillComboCity(dataRem[1], cityComboBox);
								$('#myFirst').empty();
								$('#detailsOfRoute').empty();
								$("#tooltip").hide();
							}
						});
						return false;
					});
					var $city = $('<font>').attr({'size':'5', 'color':'green', 'face':'Comic Sans MS'})
							.html("<b> " + routes[0].routePointBId.name + " </b>");
					$($city).appendTo("#detailsOfRoute");
					for (var i = 1; i < routes.length; i++) {
						route = routes[i];
						addingRoute(route);
						var $city = $('<font>').attr({'size':'5', 'color':'green', 'face':'Comic Sans MS'})
							.html("<b> " + route.routePointBId.name + " </b>");
						$($city).appendTo("#detailsOfRoute");
					}					
					countryComboBox = document.getElementById('countryComboId');
					clearCombo(countryComboBox);
					fillComboCountry(data[2], countryComboBox);
					cityComboBox = document.getElementById('cityComboId');
					clearCombo(cityComboBox);
					fillComboCity(data[1], cityComboBox);
				}
			});
		});
		
		//очищення форми
		$('#clearForm').click(function(){
			clearForm();
		});		
		function clearForm() {
			$('#addConnectForm').find('.error').remove();
			$('#change tr').attr('style', '');
			$('div.alert.alert-danger').attr({'hidden':true});			
			var buildRoute = $("#buildRoute").attr('data-tooltip');
			$('#collapseRoute').text(buildRoute);
			$('#nameOfSavedRoute').prop('value', '');
			$('#buttonModalSave').prop('value', '');
			$('#idOfSavedRouteInput').prop('value', '');
			$('#clearForm').prop('hidden', true);
			$('input[name=isCompanyRoute]').prop('checked', false);
			var editRoute = $("#editRoute").attr('data-tooltip');
			$('a.editSavedRoute').each(function() {
		    	 $(this).attr('data-tooltip',editRoute);
		    });
			$.ajax({
				url : $('#detailsOfRoute span a').attr('href') + '/clearForm',
				datatype : 'json',
				success : function(resultRem) {
					var dataRem = eval(resultRem);
					countryComboBox = document.getElementById('countryComboId');
					clearCombo(countryComboBox);
					fillComboCountry(dataRem[2], countryComboBox);
					cityComboBox = document.getElementById('cityComboId');
					clearCombo(cityComboBox);
					fillComboCity(dataRem[1], cityComboBox);
					$('#myFirst').empty();
					$('#detailsOfRoute').empty();
				}
			});
		}		
		
		//для заповнення модального вікна деталей збереженого маршруту
		$('i.moreAboutSavedRoute').click(function(){
			$("#tooltip").hide();
			$("#moreSR").empty();
			$('div.alert.alert-danger').attr({'hidden': true});
		    $.ajax({
				url : 'detailsOfSavedRoute',
				datatype : 'json',
				data : ({
					id : $(this).prop('id'),
					rpId : $(this).prop('lang'),
					isInBase : $(this).attr('onblur')
				}),
				success : function(resultDetails) {					
					var dataDetails = eval(resultDetails);					
				    var partsSavedRoute = $("#partsSavedRoute").attr('data-tooltip');
					$("#modalMore .modal-title").text(partsSavedRoute + " \"" + dataDetails[1] + "\"");
					var routeConnectionList = dataDetails[0];
					for (var i = 0; i < routeConnectionList.length; i++) {					
						var $tr = $('<tr>');
						var $tdId = $('<td>').attr({'class':'text-center'}).text(routeConnectionList[i].id);
						var $tdA = $('<td>').text(routeConnectionList[i].routePointAId.name);
						var $tdB = $('<td>').text(routeConnectionList[i].routePointBId.name);
						var $tdPrice = $('<td>').attr({'class':'text-center'}).text(routeConnectionList[i].price);
						var $tdTime = $('<td>').attr({'class':'text-center'}).text(routeConnectionList[i].time);
						var $tdTransport = $('<td>').attr({'class':'text-center'}).text(routeConnectionList[i].transportId.name);
						$($tr).appendTo("#moreSR");
						$($tdId).appendTo("#moreSR tr:last");
						$($tdA).appendTo("#moreSR tr:last");
						$($tdB).appendTo("#moreSR tr:last");
						$($tdPrice).appendTo("#moreSR tr:last");
						$($tdTime).appendTo("#moreSR tr:last");
						$($tdTransport).appendTo("#moreSR tr:last");						
					}
				}
			}); 
		});	
		
		//для заповнення модального вікна видалення збереженого маршруту
		$('a.deleteSavedRoute').click(function(){
			$("#tooltip").hide();
			$('div.alert.alert-danger').attr({'hidden': true});
		    var indexOfSavedRoute = $(this).prop('id');
		    var nameOfSavedRoute = $(this).prop('lang');
		    $("#deleteSavedRoute").prop('lang', indexOfSavedRoute);		    
			var reallyDeleteSaveRoute = $("#reallyDeleteSaveRoute").attr('data-tooltip');
		    $("#question").html(reallyDeleteSaveRoute + " <b>\"" + nameOfSavedRoute + "\"</b> ?");	
		});
		
		//видалення збереженого маршруту
		$itemsClone = null;		
		$('#deleteSavedRoute').click(function(){
			setTimeout(deletingSavedRoute, 200);
		});
		function deletingSavedRoute() {			
			var idOfSavedroute = $('#deleteSavedRoute').attr('lang');
			var idOfEditedSavedroute = $('#idOfSavedRouteInput').prop('value');
			$('#change #' + idOfSavedroute).remove();
			if(idOfSavedroute == idOfEditedSavedroute) {
				clearForm();
			}			
			$.ajax({
				url : 'deletingSavedRoute',
				datatype : 'json',
				data: ({
					id : idOfSavedroute
				})
			});
			$itemsClone = $("#change tr").clone(true);
			var perPage = parseInt($('#itemsPerPageSRoute').val());
	    	itemsPerPage(perPage);
		}
		
		//для заповнення модального вікна редагування збереженого маршруту
		$('a.editSavedRouteUser').click(function(){
			$("#tooltip").hide();
			$('#saveEditedRoute').find('div.error font').text('');
		    var indexOfSavedRoute = $(this).prop('id');
		    var nameOfSavedRoute = $(this).prop('lang');
		    $("#inputEditId").prop('value', indexOfSavedRoute);
		    $("#inputEditName").prop('value', nameOfSavedRoute);
		    $.ajax({
				url : 'editSavedRoute',
				datatype : 'json',
				data : ({
					id : indexOfSavedRoute
				})
			});
		});
		
		//для збереження редагованого маршруту
		$('#saveEditedRoute').submit(function(){
			$(this).find('div.error font').text('');			 
			var name = $(this).find('input[name=name]').val();			
			if ($(this).find('input[name=name]').val() == '') {
				var validationEnterName = $("#validationEnterName").attr('data-tooltip');
				$(this).find('div.error font').text(validationEnterName);
			    return false;
			}
			var savedRouteValid = /^[a-zA-Z0-9 ']{1,}$/;
			if (name[0] == name[0].toLowerCase()) {
				var validationUpperLetter = $("#validationUpperLetter").attr('data-tooltip');
				$(this).find('div.error font').text(validationUpperLetter);
				return false;
			}
			if (name.length > 30 || name.length < 3) {				
				var validationSymbolNumbers = $("#validationSymbolNumbers").attr('data-tooltip');
				$(this).find('div.error font').text(validationSymbolNumbers);
				return false;
			}
			if (savedRouteValid.test(name) == false) {
				var validationErrorEnter = $("#validationErrorEnter").attr('data-tooltip');
				$(this).find('div.error font').text(validationErrorEnter);
				return false;	
			}
			var isDuplicate = false;
			var list = $('#change tr').find('td[id=infoOfSavedRoute]');
			list.each(function(i, value) {
				if (value.align.toLowerCase() == name.toLowerCase() && value.lang != $("#inputEditId").prop('value')) {					
					isDuplicate = true;
				}										
			});
			if(isDuplicate) {
				var validationNonUnique = $("#validationNonUnique").attr('data-tooltip');
				$(this).find('div.error font').text(validationNonUnique);
				return false;
			}
		});
		
		//зміна типу маршруту як маршруту компанії
		$("#change tr td i.change").click(function(){	
			var changeId = $(this).prop('id');
			var savedRouteEditId = $("#idOfSavedRouteInput").prop('value');
			if($("i#" + changeId + ".change").hasClass("glyphicon-ok-circle green")) {
				$("i#" + changeId + ".change").removeClass("glyphicon-ok-circle green")
					.addClass("glyphicon-ban-circle red");				
				var assignRouteCompany = $("#assignRouteCompany").attr('data-tooltip');
				$(this).attr('data-tooltip', assignRouteCompany);
				$("#tooltip").text(assignRouteCompany).show(100);
				if(changeId == savedRouteEditId) {
					$('input[name=isCompanyRoute]').prop('checked', false);
				}
			} else {
				$("#" + changeId + ".change").removeClass("glyphicon-ban-circle red")
					.addClass("glyphicon-ok-circle green");
				var removeDestination = $("#removeDestination").attr('data-tooltip');
				$(this).attr('data-tooltip', removeDestination);
				$("#tooltip").text(removeDestination).show(100);				
				if(changeId == savedRouteEditId) {
					$('input[name=isCompanyRoute]').prop('checked', true);
				}
			}
			$.ajax({
				url : 'changingCompany',
				datatype : 'json',
				data : ({
					sendValue : changeId
				})
			});
		});
		
		//для заповнення випадалки
		$('#countryComboId').change(function() {
			selectCombo(this, 'cityComboId');								
		});
		function selectCombo(combo1, combo2) {								
			comboBox2 = document.getElementById(combo2);
			clearCombo(comboBox2);
			$.ajax({
					url : 'fillComboBox',
					datatype : 'json',
					data : ({
						sendValue : combo1.options[combo1.selectedIndex].value
					}),
					success : function(resultDo) {
						var myObject = eval(resultDo);											
						fillComboCity(myObject, comboBox2);
					}
			});		
		}
		function clearCombo(combo) {
			while (combo.length > 1) {
				combo.remove(combo.length - 1);
			}
		}
		function fillComboCity(json, combo) {			
			for (var i = 0; i < json.length; i++) {
				if(json[i].transportId == null) {
					combo.options[combo.length] = new Option(
							json[i].routePointBId.name, json[i].id);										
				} else {
					combo.options[combo.length] = new Option(
							json[i].routePointBId.name + " (" + json[i].transportId.name + ")", json[i].id);
				}
			}
		}
		function fillComboCountry(json, combo) {
			for (var i = 0; i < json.length; i++) {
				combo.options[combo.length] = new Option(
						json[i].name, json[i].id);
			}
		}	
		
		//підказки при введенні назви маршруту		
		addLoadEvent(prepareInputsForHints);							
		function addLoadEvent(func) {
			var oldonload = window.onload;
			if (typeof window.onload != 'function') {
				window.onload = func;
			} else {
				window.onload = function() {
					oldonload();
					func();
				};
			}
		}
		function prepareInputsForHints() {
			var inputs = document.getElementsByTagName("input");
			for (var i = 0; i < inputs.length; i++) {
				// test to see if the hint span exists first
				if (inputs[i].parentNode.getElementsByTagName("span")[0]) {
					// the span exists!  on focus, show the hint
					inputs[i].onfocus = function() {
						this.parentNode.getElementsByTagName("span")[0].style.display = "inline";
					};
					// when the cursor moves away from the field, hide the hint
					inputs[i].onblur = function() {
						this.parentNode.getElementsByTagName("span")[0].style.display = "none";
					};
				}
			}
		}
		$('#nameOfSavedRoute').autocomplete({
			serviceUrl: 'hintsForNameOfSavedRoute',
			paramName: "enter",
			delimiter: ",",
		    transformResult: function(response) {	
		    	addLoadEvent(prepareInputsForHints);
		    	var validationInfo = null;
		    	if(response == "") {	
		    		$("#divHideValidator").prop('hidden', true);
		    	} else 
		    		$("#divHideValidator").prop('hidden', false);
		    		if(response == "1") {
						validationInfo = $("#validationErrorEnter").attr('data-tooltip');
		    		} else if(response == "2") {
						validationInfo = $("#validationUpperLetter").attr('data-tooltip');
		    		} else if(response == "3") {
						validationInfo = $("#validationSymbolNumbers").attr('data-tooltip');
		    		} else if(response == "4") {	
						validationInfo = $("#validationNonUnique").attr('data-tooltip');
		    		}	    		
		    	$("#validatorInfo").html(validationInfo);
		    }
		});		
		
		//валідація для введення назви маршруту
		$('#saveRoute').click(function() {
			if($('#idOfDto').val() == null) {
				$("#buttonModalSave").attr('data-dismiss', 'modal');				
				var validationNotExistRoute = $("#validationNotExistRoute").attr('data-tooltip');
			    $("#message").html(validationNotExistRoute);						
			} else {
				if ($('#nameOfSavedRoute').val() == "") {
					$("#buttonModalSave").attr('data-dismiss', 'modal');
					var validationEnterName = $("#validationEnterName").attr('data-tooltip');
				    $("#message").html(validationEnterName);
				} else {
					var isValid = true;
					var message = "";
					var c = $('#nameOfSavedRoute').val();
					var savedRouteValid = /^[a-zA-Z0-9 ']{1,}$/;
					if ($('#nameOfSavedRoute').val()[0] == $('#nameOfSavedRoute').val()[0].toLowerCase()) {
						var validationUpperLetter = $("#validationUpperLetter").attr('data-tooltip');
						message += "<p>" + validationUpperLetter + "</p>";
						isValid = false;
					}
					if (($('#nameOfSavedRoute').val().length) > 30 || ($('#nameOfSavedRoute').val().length) < 3) {
						var validationSymbolNumbers = $("#validationSymbolNumbers").attr('data-tooltip');
						message += "<p>" + validationSymbolNumbers + "</p>";
						isValid = false;
					}
					if (savedRouteValid.test(c) == false) {
						var validationErrorEnter = $("#validationErrorEnter").attr('data-tooltip');
						message += "<p>" + validationErrorEnter + "</p>";
						isValid = false;
					}
					if(!isValid) {
						$("#buttonModalSave").attr('data-dismiss', 'modal');
					    $("#message").html(message);
					} else {
						var isDuplicate = false;
						var list = $('#change tr').find('td[id=infoOfSavedRoute]');
						list.each(function(i, value) {
							if (value.align.toLowerCase() == ($('#nameOfSavedRoute').val().toLowerCase())
									&& value.lang != $('#buttonModalSave').attr('value')) {		
								$("#buttonModalSave").attr('data-dismiss', 'modal');
								var validationNonUnique = $("#validationNonUnique").attr('data-tooltip');
						    	$("#message").html(validationNonUnique);
						    	isDuplicate = true;
							}											
						});
						if(!isDuplicate) {
							$("#buttonModalSave").attr('data-dismiss', '');	
							var reallySaveRoute = $("#reallySaveRoute").attr('data-tooltip');
				    		$("#message").html(reallySaveRoute);
						}
					}							
				}							
			}
		});
		
		//для роботи розгортального вікна
		$("#collapseRoute").click(function(){
			if($("#collapseOne").hasClass("collapse")) {
				$("#collapseOne").removeClass("collapse");
			} else {
				$("#collapseOne").addClass("collapse");
			}
		});
		
		//пейджинг
		$(function() {
			itemsPerPage(5);	
			enterNameOfRoute = $("#enterNameOfRoute").attr('data-tooltip');
			$("#nameOfSavedRoute").attr('placeholder', enterNameOfRoute);
		});
		$('#itemsPerPageSRoute').change(function() {
			var perPage = parseInt($(this).val());
			itemsPerPage(perPage);
			$('div.alert.alert-danger').attr({'hidden': true});
		});
		function itemsPerPage(perPage) {
			var items = $("#change tr");
			items.slice(perPage).hide();
			items.hide().slice(0, perPage).show();
			$("#paginationSRoute").pagination({
				items : items.length,
				itemsOnPage : perPage,
				prevText: '<< ' + $("#previous").attr('data-tooltip'),
				nextText: $("#next").attr('data-tooltip') + ' >>',
				cssStyle : "compact-theme",
				onPageClick : function(pageNumber) {
					var showFrom = perPage * (pageNumber-1);
					var showTo = perPage + showFrom;
					items.hide().slice(showFrom, showTo).show();
				}
			});			
		}	
		
		//пошук по назві маршруту
		var nameOfSRoute = "";
		var pointsOfSRoute = "";  
		$itemsClone = $("#change tr").clone(true, true);
		$('#findOnName').autocomplete({	
		    transformResult: function(data, value) {
				$('div.alert.alert-danger').attr({'hidden': true});
		    	nameOfSRoute = value;
		    	var items = $("#change tr");
		    	items.detach();		    	
		    	$itemsClone.each(function() {
		    		var name = $(this).find('td[id=infoOfSavedRoute]').text();
		    		var pointsOfRouteA = $(this).find('td[id=pointsOfRoute]').text();
		    		var pointsOfRouteB = $(this).find('td[id=pointsOfRoute]').find('i').attr('id');
		    		if (name.toLowerCase().indexOf(value.toLowerCase()) !=-1
		    				&& (pointsOfRouteA.toLowerCase().indexOf(pointsOfSRoute.toLowerCase()) !=-1 
		    						|| pointsOfRouteB.toLowerCase().indexOf(pointsOfSRoute.toLowerCase()) !=-1)) {
		    			$(this).appendTo("#change");
			    	}
		    	});
		    	var perPage = parseInt($('#itemsPerPageSRoute').val());
		    	itemsPerPage(perPage);
		    	$("#tooltip").hide();
		    	tip();
		    }
		});		
		
		//пошук по пунктах маршруту
		$('#findOnRoute').autocomplete({	
		    transformResult: function(data, value) {
				$('div.alert.alert-danger').attr({'hidden': true});
		    	pointsOfSRoute = value;
		    	var items = $("#change tr");
		    	items.detach();		    	
		    	$itemsClone.each(function() {
		    		var name = $(this).find('td[id=infoOfSavedRoute]').text();
		    		var pointsOfRouteA = $(this).find('td[id=pointsOfRoute]').text();
		    		var pointsOfRouteB = $(this).find('td[id=pointsOfRoute]').find('i').attr('id');
		    		if ((pointsOfRouteA.toLowerCase().indexOf(value.toLowerCase()) !=-1 
		    				|| pointsOfRouteB.toLowerCase().indexOf(value.toLowerCase()) !=-1) 
		    				&& name.toLowerCase().indexOf(nameOfSRoute.toLowerCase()) !=-1) {
		    			$(this).appendTo("#change");
			    	}
		    	});
		    	var perPage = parseInt($('#itemsPerPageSRoute').val());
		    	itemsPerPage(perPage);
		    	$("#tooltip").hide();
		    	tip();
		    }
		});			
		
		var sort = $("#sort").attr('data-tooltip');
		var sortDescending = $("#sortDescending").attr('data-tooltip');
		var sortAscending = $("#sortAscending").attr('data-tooltip');
		//сортування по назві
		$("#nameOfSR span").click(function() {
			$('div.alert.alert-danger').attr({'hidden': true});
			if($(this).hasClass('glyphicon-sort-by-attributes')) {
				$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes-alt');
				$(this).attr('data-tooltip', sortAscending);
				$("#tooltip").text(sortAscending).show();
				SORTER.sort('#change', 'height', 'desc');
			} else if($(this).hasClass('glyphicon-sort-by-attributes-alt')) {
				$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes');
				$(this).attr('data-tooltip', sortDescending);
				$("#tooltip").text(sortDescending).show();
				SORTER.sort('#change', 'height');
			} else {
				$('#titleTable th span').each(function() {
					$(this).attr('class', 'glyphicon glyphicon-sort');
					$(this).attr('data-tooltip', sort);
				});
				$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes');
				$(this).attr('data-tooltip', sortDescending);
				$("#tooltip").text(sortDescending).show();				
				SORTER.sort('#change', 'height');
			}
		});
		
		//сортування по пунктах маршруту
		$("#routesOfSR span").click(function() {
			$('div.alert.alert-danger').attr({'hidden': true});
			if($(this).hasClass('glyphicon-sort-by-attributes')) {
				$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes-alt');	
				$(this).attr('data-tooltip', sortAscending);
				$("#tooltip").text(sortAscending).show();
				SORTER.sort('#change', 'lang', 'desc');
			} else if($(this).hasClass('glyphicon-sort-by-attributes-alt')) {
				$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes');
				$(this).attr('data-tooltip', sortDescending);
				$("#tooltip").text(sortDescending).show();
				SORTER.sort('#change', 'lang');
			} else {
				$('#titleTable th span').each(function() {
					$(this).attr('class', 'glyphicon glyphicon-sort');
					$(this).attr('data-tooltip', sort);
				});
				$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes');	
				$(this).attr('data-tooltip', sortDescending);
				$("#tooltip").text(sortDescending).show();
				SORTER.sort('#change', 'lang');
			}
		});
		
		//сортування по вартості
		$("#priceOfSR span").click(function() {
			$('div.alert.alert-danger').attr({'hidden': true});
			if($(this).hasClass('glyphicon-sort-by-attributes')) {
				$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes-alt');
				$(this).attr('data-tooltip', sortAscending);
				$("#tooltip").text(sortAscending).show();
				SORTER_N.sort('#change', 'background', 'desc');
			} else if($(this).hasClass('glyphicon-sort-by-attributes-alt')) {
				$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes');	
				$(this).attr('data-tooltip', sortDescending);
				$("#tooltip").text(sortDescending).show();
				SORTER_N.sort('#change', 'background');
			} else {
				$('#titleTable th span').each(function() {
					$(this).attr('class', 'glyphicon glyphicon-sort');
					$(this).attr('data-tooltip', sort);
				});
				$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes');
				$(this).attr('data-tooltip', sortDescending);
				$("#tooltip").text(sortDescending).show();
				SORTER_N.sort('#change', 'background');
			}
		});
		
		//сортування по тривалості
		$("#timeOfSR span").click(function() {
			$('div.alert.alert-danger').attr({'hidden': true});
			if($(this).hasClass('glyphicon-sort-by-attributes')) {
				$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes-alt');
				$(this).attr('data-tooltip', sortAscending);
				$("#tooltip").text(sortAscending).show();
				SORTER_T.sort('#change', 'onblur', 'desc');
			} else if($(this).hasClass('glyphicon-sort-by-attributes-alt')) {
				$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes');
				$(this).attr('data-tooltip', sortDescending);
				$("#tooltip").text(sortDescending).show();
				SORTER_T.sort('#change', 'onblur');
			} else {
				$('#titleTable th span').each(function() {
					$(this).attr('class', 'glyphicon glyphicon-sort');
					$(this).attr('data-tooltip', sort);
				});
				$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes');
				$(this).attr('data-tooltip', sortDescending);
				$("#tooltip").text(sortDescending).show();
				SORTER_T.sort('#change', 'onblur');
			}
		});
		
		//сортування по часу модифікації
		$("#modificationOfSR span").click(function() {
			$('div.alert.alert-danger').attr({'hidden': true});
			if($(this).hasClass('glyphicon-sort-by-attributes')) {
				$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes-alt');
				$(this).attr('data-tooltip', sortAscending);
				$("#tooltip").text(sortAscending).show();
				SORTER.sort('#change', 'onabort', 'desc');
			} else if($(this).hasClass('glyphicon-sort-by-attributes-alt')) {
				$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes');
				$(this).attr('data-tooltip', sortDescending);
				$("#tooltip").text(sortDescending).show();
				SORTER.sort('#change', 'onabort');
			} else {
				$('#titleTable th span').each(function() {
					$(this).attr('class', 'glyphicon glyphicon-sort');
					$(this).attr('data-tooltip', sort);
				});
				$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes');
				$(this).attr('data-tooltip', sortDescending);
				$("#tooltip").text(sortDescending).show();
				SORTER.sort('#change', 'onabort');
			}
		});
		
		//сортування по маршруту компанії
		$("#companyOfSR span").click(function() {
			$('div.alert.alert-danger').attr({'hidden': true});
			if($(this).hasClass('glyphicon-sort-by-attributes')) {
				$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes-alt');
				$(this).attr('data-tooltip', sortAscending);
				$("#tooltip").text(sortAscending).show();
				SORTER.sort('#change', 'oncanplay', 'desc');
			} else if($(this).hasClass('glyphicon-sort-by-attributes-alt')) {
				$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes');
				$(this).attr('data-tooltip', sortDescending);
				$("#tooltip").text(sortDescending).show();
				SORTER.sort('#change', 'oncanplay');
			} else {
				$('#titleTable th span').each(function() {
					$(this).attr('class', 'glyphicon glyphicon-sort');
					$(this).attr('data-tooltip', sort);
				});
				$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes');
				$(this).attr('data-tooltip', sortDescending);
				$("#tooltip").text(sortDescending).show();
				SORTER.sort('#change', 'oncanplay');
			}
		});
		
		//функція сортування для тексту
		var SORTER = {};
		SORTER.sort = function(which, attr, dir) {
			SORTER.dir = (dir == "desc") ? -1 : 1;
			$(which).each(function() {
		    	var sorted = $(this).find("> tr").sort(function(a, b) {
		    		return $(a).attr(attr).toLowerCase() > $(b).attr(attr).toLowerCase() ? SORTER.dir : -SORTER.dir;
		    	});
		    	$(this).append(sorted);
			});
			var perPage = parseInt($('#itemsPerPageSRoute').val());
	    	itemsPerPage(perPage);
		};
		

		//функція сортування для чисел
		var SORTER_N = {};
		SORTER_N.sort = function(which, attr, dir) {
			SORTER_N.dir = (dir == "desc") ? -1 : 1;
			$(which).each(function() {
		    	var sorted = $(this).find("> tr").sort(function(a, b) {
		    		return parseInt($(a).attr(attr)) > parseInt($(b).attr(attr)) ? SORTER_N.dir : -SORTER_N.dir;
		    	});
		    	$(this).append(sorted);
			});
			var perPage = parseInt($('#itemsPerPageSRoute').val());
	    	itemsPerPage(perPage);
		};		

		//функція сортування для часу
		var SORTER_T = {};
		SORTER_T.sort = function(which, attr, dir) {
			SORTER_T.dir = (dir == "desc") ? -1 : 1;
			$(which).each(function() {
		    	var sorted = $(this).find("> tr").sort(function(a, b) {		    		
		    		a = $(a).attr(attr);
		    		b = $(b).attr(attr);
		    		if(a.length == 4) {
		    			a = '00' + a;
		    		}
					if(b.length == 4) {					
		    			b = '00' + b;
		    		}
					if(a.length == 5) {
		    			a = '0' + a;
		    		}
					if(b.length == 5) {					
		    			b = '0' + b;
		    		}
		    		return a > b ? SORTER_T.dir : -SORTER_T.dir;
		    	});
		    	$(this).append(sorted);
			});
			var perPage = parseInt($('#itemsPerPageSRoute').val());
	    	itemsPerPage(perPage);
		};	
		
		//спливаюче вікно з підказками
		$(function() {
			tip();					
		});		
		function tip() {
			$("[data-tooltip]").mousemove(function (eventObject) {
				$data_tooltip = $(this).attr("data-tooltip");
				$("#tooltip").text($data_tooltip).css({
					"top":eventObject.pageY+5,
					"left":eventObject.pageX+5}).show();
			}).mouseout(function(){
				$("#tooltip").hide().text("").css({
					"top":0,
					"left":0
				});			
			});	
		}
		
	});
</script>