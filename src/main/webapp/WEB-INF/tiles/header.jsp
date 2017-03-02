<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href='<c:url value="/resources/css/bootstrap.css" />'
	rel="stylesheet">
<link href='<c:url value="/resources/css/component.css" />'
	rel="stylesheet">
<link href='<c:url value="/resources/css/normalize.css" />'
	rel="stylesheet">
<link href='<c:url value="/resources/css/style.css" />' rel="stylesheet">
<link href='<c:url value="/resources/fonts/fonts.css" />'
	rel="stylesheet">
<script src="<c:url value="/resources/js/jQuery.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.js" />"></script>

	<link href='<c:url value="/resources/css/simplePagination.css" />'
	rel="stylesheet">

<script src="<c:url value="/resources/js/jQuery.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.js" />"></script>
<script src="<c:url value="/resources/js/confirmDeleteRecord.js" />"></script>
<!-- Скріпт для перших двох комбобоксів "звідки" на сторінці index -->
<script type="text/javascript">
	$(document)
			.ready(
					function First() {
						$('#departCountryId').change(function() {
							SelectCombo(this, 'departCityId');
						});
						function SelectCombo(combo1, combo2) {
							comboBox2 = document.getElementById(combo2);
							ClearCombo(comboBox2);
							if (combo1.options[combo1.selectedIndex].value != "") {
								comboBox2.disabled = true;

								$
										.ajax({
											url : 'ajaxFirstTwoCombos.html',
											//type : 'get',
											datatype : 'json',
											data : ({
												sendValue : combo1.options[combo1.selectedIndex].value
											}),
											success : function(resultDo) {
												var myObject = eval('('
														+ resultDo + ')');
												FillCombo(myObject, comboBox2);
												comboBox2.disabled = false;
											}
										});
							}
						}
						function ClearCombo(combo) {
							while (combo.length > 0) {
								combo.remove(combo.length - 1);
							}
						}
						function FillCombo(json, combo) {
							combo.options[0] = new Option('Оберіть пункт', '0');
							for (var i = 0; i < json.length; i++) {
								combo.options[combo.length] = new Option(
										json[i].name, json[i].id);
							}
						}
					});
</script>

<script type="text/javascript">
	$(document)
			.ready(
					function First() {
						$('#transferCountryId').change(function() {
							SelectCombo(this, 'transferCityId');
						});
						function SelectCombo(combo1, combo2) {
							comboBox2 = document.getElementById(combo2);
							ClearCombo(comboBox2);
							if (combo1.options[combo1.selectedIndex].value != "") {
								comboBox2.disabled = true;
								$
										.ajax({
											url : 'ajaxFirstTwoCombos.html',
											//type : 'get',
											datatype : 'json',
											data : ({
												sendValue : combo1.options[combo1.selectedIndex].value
											}),
											success : function(resultDo) {
												var myObject = eval('('
														+ resultDo + ')');
												FillCombo(myObject, comboBox2);
												comboBox2.disabled = false;
											}
										});
							}
						}
						function ClearCombo(combo) {
							while (combo.length > 0) {
								combo.remove(combo.length - 1);
							}
						}
						function FillCombo(json, combo) {
							combo.options[0] = new Option('Оберіть пункт', '0');
							for (var i = 0; i < json.length; i++) {
								combo.options[combo.length] = new Option(
										json[i].name, json[i].id);
							}
						}
					});
</script>

<!-- Скріпт для других двох комбобоксів "куди" на сторінці index -->
<script type="text/javascript">
	$(document)
			.ready(
					function Second() {
						$('#arriveCountryId').change(function() {
							SelectCombo(this, 'arriveCityId');
						});
						function SelectCombo(combo1, combo2) {
							comboBox2 = document.getElementById(combo2);
							ClearCombo(comboBox2);
							if (combo1.options[combo1.selectedIndex].value != "") {
								comboBox2.disabled = true;

								$
										.ajax({
											url : 'ajaxFirstTwoCombos.html',
											//type : 'get',
											datatype : 'json',
											data : ({
												sendValue : combo1.options[combo1.selectedIndex].value
											}),
											success : function(resultDo) {
												var myObject = eval('('
														+ resultDo + ')');
												FillCombo(myObject, comboBox2);
												comboBox2.disabled = false;
											}
										});
							}
						}
						function ClearCombo(combo) {
							while (combo.length > 0) {
								combo.remove(combo.length - 1);
							}
						}
						function FillCombo(json, combo) {
							combo.options[0] = new Option('Оберіть пункт', '0');
							for (var i = 0; i < json.length; i++) {
								combo.options[combo.length] = new Option(
										json[i].name, json[i].id);
							}
						}
					}
					);
	
	$(document).ready($(function() {
	//сортування по вартості
	$("#valueSortId span").click(function() {
		if($(this).hasClass('glyphicon-sort-by-attributes')) {
			$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes-alt');						
			SORTER_N.sort('#change', 'background', 'desc');
		} else if($(this).hasClass('glyphicon-sort-by-attributes-alt')) {
			$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes');	
			SORTER_N.sort('#change', 'background');
		} else {
			$("#durationSortId span").attr('class', 'glyphicon glyphicon-sort');
			$("#transportSortId span").attr('class', 'glyphicon glyphicon-sort');
			$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes');
			SORTER_N.sort('#change', 'background');
		}
	});
	
	//сортування по тривалості
	$("#durationSortId span").click(function() {
		if($(this).hasClass('glyphicon-sort-by-attributes')) {
			$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes-alt');						
			SORTER_T.sort('#change', 'onblur', 'desc');
		} else if($(this).hasClass('glyphicon-sort-by-attributes-alt')) {
			$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes');	
			SORTER_T.sort('#change', 'onblur');
		} else {
			$("#valueSortId span").attr('class', 'glyphicon glyphicon-sort');
			$("#transportSortId span").attr('class', 'glyphicon glyphicon-sort');
			$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes');
			SORTER_T.sort('#change', 'onblur');
		}
	});
	//сортування по назві
	$("#transportSortId span").click(function() {
		if($(this).hasClass('glyphicon-sort-by-attributes')) {
			$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes-alt');					
			SORTER.sort('#change', 'title', 'desc');
		} else if($(this).hasClass('glyphicon-sort-by-attributes-alt')) {
			$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes');
			SORTER.sort('#change', 'title');
		} else {
			$("#durationSortId span").attr('class', 'glyphicon glyphicon-sort');
			$("#valueSortId span").attr('class', 'glyphicon glyphicon-sort');
			$(this).attr('class', 'glyphicon glyphicon-sort-by-attributes');
			SORTER.sort('#change', 'title');
		}
	});
	
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
    	itemsPerPage(6);
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
    	itemsPerPage(6);
	};
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
    	itemsPerPage(6);
	};
	$(function() {
		itemsPerPage(6);					
	});
	function itemsPerPage(perPage) {
		var items = $("#contentRoute tbody tr");
		items.slice(perPage).hide();
		items.hide().slice(0, perPage).show();
		$("#paginationRoute").pagination({
			items : items.length,
			itemsOnPage : perPage,
			prevText: '<< Попередня',
			nextText: 'Наступна >>',
			cssStyle : "compact-theme",
			onPageClick : function(pageNumber) {
				var showFrom = perPage * (pageNumber-1);
				var showTo = perPage + showFrom;
				items.hide().slice(showFrom, showTo).show();
			}
		});			
	}	
	
	//для заповнення модального вікна деталей збереженого маршруту
	$('i.moreAboutSavedRoute').click(function(){
		$("#tooltip").hide();
		$("#moreSR").empty();
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
	
	}));

	
</script>

<!--[if lt IE 9]>
	    <script src="<c:url value="/resources/js/html5shiv.js" />"></script>
	    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->