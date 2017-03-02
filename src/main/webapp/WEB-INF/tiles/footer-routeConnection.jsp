<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
						var comboRC = document.getElementById('itemsPerPageRC');
						prpg = comboRC.options[comboRC.selectedIndex].value;
						Pag();

						//--------------------
						//Super dynamic filter for RouteConnection Page
						$('#filter_fromCity, #filter_toCity').on('keyup',function(event) {

											$.ajax({
														url : '${pageContext.request.contextPath}/filterCity.html',
														datatype : 'json',
														data : ({
															fromCity : $(this)
																	.val()
																	+ " "
																	+ $(this).attr('id')
														}),
														success : function(
																resultDo) {
															var response = eval('('
																	+ resultDo
																	+ ')');
															var table = $("#contentRC tbody");
															$(
																	"#contentRC tbody").empty();

															$.each(
																			response,
																			function(
																					i,
																					item) {
																				var isChecked = "";
																				if (item.isBlocked == true) {
																					isChecked = "checked";
																				}
																				var editLink = '${pageContext.request.contextPath}'
																						+ '/edit/'
																						+ item.id;
																				var deleteLink = '${pageContext.request.contextPath}'
																						+ '/delete/'
																						+ item.id
																						+ "";
																				//var d='dddddd';
																				var edit = '<span class="badge badge-warning"><a href="'+editLink+'" class="glyphicon glyphicon-edit"></a></span>';
																				//var deleteRC="<span class='deleteRCC' onClick='DeleteRC("+d+")' ><span class='badge badge-danger'> <i class='glyphicon glyphicon-trash'></i></span></span>";
																				var deleteRCC = '<span class="deleteRCC" onClick="DeleteRC(\''
																						+ deleteLink
																						+ '\')" ><span class="badge badge-danger"> <i class="glyphicon glyphicon-trash"></i></span></span>';

																				table.append("<tr><td>"
																								+ item.routePointAName
																								+ "</td><td>"
																								+ item.routePointBName
																								+ "</td><td>"
																								+ item.price
																								+ "</td><td>"
																								+ item.time
																								+ "</td><td>"
																								+ item.transportName
																								+ "</td>"
																								+ " <td><input type='checkbox' name='isBlocked'"
											+isChecked+">"
																								+ "</td><td>"
																								+ edit
																								+ "  "
																								+ deleteRCC
																								+ " </td></tr>");
																			});
															Pag();

														}
													});

											//------------------

											//}
										});


						$('.deleteRCC')
								.click(
										
										function(event) {
											event.preventDefault();
											var arr1 = localiz();
											var confirmation = confirm(arr1[1]);
											if (confirmation == true) {
												window.location = $(this).attr('id');
											}
										});

						$('#fromCountry').change(function() {
							SelectComboRC(this, 'fromCity');

						});
						$('#toCountry').change(function() {
							SelectComboRC(this, 'toCity');
						});

						$('#itemsPerPageRC')
								.change(
										function() {

											var comboRC = document
													.getElementById('itemsPerPageRC');
											var value = comboRC.options[comboRC.selectedIndex].value;

											window.location = '${pageContext.request.contextPath}'
													+ '/changeElementsPerPage/'
													+ value;

										});

						function SelectComboRC(combo1, combo2) {
							comboBox2 = document.getElementById(combo2);
							ClearComboRC(comboBox2);

							$
									.ajax({
										url : '${pageContext.request.contextPath}/getCities.html',
										datatype : 'json',
										data : ({
											sendValue : combo1.options[combo1.selectedIndex].value
										}),
										success : function(resultDo) {
											var myObject = eval('(' + resultDo
													+ ')');
											FillComboRC(myObject, comboBox2);
											if (combo1.options[0].value == "") {
												combo1.remove(0);
											}
										}
									});
						}

						function ClearComboRC(combo) {
							while (combo.length > 0) {
								combo.remove(combo.length - 1);
							}
						}

						function FillComboRC(json, combo) {
							for (var i = 0; i < json.length; i++) {
								combo.options[combo.length] = new Option(
										json[i].name, json[i].id);
							}
						}

						/*
						 for route.jsp
						 */
						$('#countryComboId').change(function() {
							SelectCombo(this, 'cityComboId');
						});
						function SelectCombo(combo1, combo2) {
							comboBox2 = document.getElementById(combo2);
							ClearCombo(comboBox2);
							if (combo1.options[combo1.selectedIndex].value != "") {
								$
										.ajax({
											url : 'doMethod.html',
											datatype : 'json',
											data : ({
												sendValue : combo1.options[combo1.selectedIndex].value
											}),
											success : function(resultDo) {
												var myObject = eval('('
														+ resultDo + ')');
												FillCombo(myObject, comboBox2);
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
							combo.options[0] = new Option('Виберіть місто', '');
							for (var i = 0; i < json.length; i++) {
								if (json[i].transportId == null) {
									combo.options[combo.length] = new Option(
											json[i].routePointBId.name,
											json[i].id);
								} else {
									combo.options[combo.length] = new Option(
											json[i].routePointBId.name + " ("
													+ json[i].transportId.name
													+ ")", json[i].id);
								}
							}
						}

						//----------RouteConnection pagination

						function Pag() {
						var items = $("#contentRC tbody tr");
						var numItems = items.length;

						var comboRC = document.getElementById('itemsPerPageRC');
						prpg = comboRC.options[comboRC.selectedIndex].value;
						perPage = prpg;

						items.slice(perPage).hide();
						$("#paginationRC").pagination({
						items : numItems,
						itemsOnPage : perPage,
						cssStyle : "compact-theme",
						onPageClick : function(pageNumber) {

						var showFrom = perPage * (pageNumber - 1);
						var showTo = showFrom +${perPage};
						items.hide().slice(showFrom, showTo).show();

						}
						});
						}

						function userPagination() {	
						var uCount = 0;
						var req1 = $.ajax({
						url : '${pageContext.request.contextPath}/users/list/getUserCount.html',
						success : function(userCount) {
						uCount = $.parseJSON(userCount);
						}
						});
						$.when(req1).done( function() {
						$("#UserPagination").pagination({
						items : uCount,
						itemsOnPage : $("#userPagingPar").val(),
						cssStyle : "compact-theme",
						onPageClick : function(pageNumber) {
						updateUsersOnPage(pageNumber - 1);
						}
						});
						});
						}

						//----------------------------------


</script>
	

	
	
<script>
$(document).ready(function(){

	var arr = localiz();
	$('#submitButtonId').click(function(){ 
    $('#hiddenId').empty();
    

   		/*empty fields validation*/
		if($("#priceId").val()==""||$("#selTarnspId").val()==""
			||$("#toCountry").val()==""||$("#fromCountry").val()==""
			||$("#fromCity").val()==""||$("#toCity").val()==""
	        ||$("#timeId").val()==""){
			
			{
				
				$('#first').remove();
				var div1 = document.createElement ('span');
				
				var str= '<div id="first" class="alert alert-danger">'+arr[3]+'</div>' ;	
				div1.innerHTML=str;
				var cont = document.getElementById ('inputagId');
				 cont.appendChild (div1);	
		}
			return false;
		};
		
		 /*wrong price format validation*/
		 var c = $('#priceId').val();
			var priceValid =/^([0-9]){1,6}$/;
			var zz = $('#first').remove();
			
				  if( (priceValid.test(c)==false)
					||(  ($("#fromCity").val() == $("#toCity").val())
					&& ($("#toCountry").val()==$("#fromCountry").val())   )    ){
				  	
				  	var div1 = document.createElement ('span');
				  	var str= '<div id="first" class="alert alert-danger">'+arr[2]+'</div>' ;
				  	div1.innerHTML =str;
				  	var cont = document.getElementById ('inputagId');
				  	cont.appendChild (div1);
				    return false;
				  		  		 }
	    
		/*validation for unique*/
		var x=isDuplicate(); 
		if(x==1)  return false; 
		});
		
		function isDuplicate(){
		
			var firstPoitList = $('#contentRC #valuesId #firstCityId'); 
			var secondPoitList = $('#contentRC #valuesId #secondCityId'); 
			var transportList = $('#contentRC #valuesId #transportListId');

			var selectFromCity =$('#fromCity :selected').text();
			var selectToCity =$('#toCity :selected').text();
			var selectTransport =$('#selTarnspId :selected').text();
			var mass =[];
			var temp=0;
			for(var i=0; i<firstPoitList.length; i++){
			obj = {firstC:firstPoitList[i].innerHTML, secondC:secondPoitList[i].innerHTML, transport:transportList[i].innerHTML};
			mass[i]=obj;

				if( (((mass[i].firstC == selectFromCity) && (mass[i].secondC == selectToCity)) 
					|| ((mass[i].firstC == selectToCity) && (mass[i].secondC == selectFromCity))) 
					&&  (mass[i].transport == selectTransport ) ){			
				temp++;
				};
				
				}
			
			if(temp==1){
				var div1 = document.createElement ('span');
				
				var str= '<div id="first" class="alert alert-danger">'+arr[0]+'</div>' ;
			  	div1.innerHTML =str;
				$('#first').remove();
				
				var cont = document.getElementById ('inputagId');
				 cont.appendChild (div1);
				
			};
			 
			return temp;
			};
		
});
</script>


	