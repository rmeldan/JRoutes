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
<script src="<c:url value="/resources/js/jquery.validation.js" />"></script>
<script src="<c:url value="/resources/js/custom.js" />"></script>
<script src="<c:url value="/resources/js/confirmDeleteRecord.js" />"></script>
<script src="<c:url value="/resources/js/userRolesManagement.js" />"></script>

<script>

/*
 * Change user lock 
 */
$(document).on('click', '.usergl', function() {
	var userId = $(this).attr('id');
	$.ajax({
		url : '${pageContext.request.contextPath}/users/list/changeLock.html',
		datatype : 'json',
		data : ({
			userId : $(this).attr('id')
		}),
		success : function(userLock) {
			var response = $.parseJSON(userLock);
			if (response == true) {
				$("#" + userId).find("i").removeClass("glyphicon glyphicon-ban-circle red");
				$("#" + userId).find("i").addClass("glyphicon glyphicon-ok-circle");
			} else {
				$("#" + userId).find("i").removeClass("glyphicon glyphicon-ok-circle");
				$("#" + userId).find("i").addClass("glyphicon glyphicon-ban-circle red");
			}
		}
	});
});

$(document).ready(function() {
	
	var usersusers = '<spring:message code="users.users" />';
	var usersrecordsperpage = '<spring:message code="users.recordsPerPage" />';
	var usersemail = '<spring:message code="users.email" />';
	var usersfname = '<spring:message code="users.fname" />';
	var userslname = '<spring:message code="users.lname" />';
	var userslock = '<spring:message code="users.lock" />';
	var usersrole = '<spring:message code="users.role" />';

	var usersLocVar = [usersusers, usersrecordsperpage, usersemail, 
		                  usersfname, userslname, userslock, usersrole];
	fLocaliz(usersLocVar);
	

	userPagination();
	
	updateUsersOnPage(0);
	
	$("#userPagingPar").change(function() {
		userPagination();
		updateUsersOnPage(0);
	});

	$('.list-group li.list-group-item a.del-role').click(function() {
		confirmRoleDelete(this.id, this.name);
	});

	$("#userRolesManagmentResult").hide();

	$("#removeRole").click(function() {
		removeUserRole();
	});
	$("#addRole").click(function() {
		addUserRole();
	});

	$("#reff").click(function() {
		roles = "";
		$('#haveRoles option').each(function() {
			roles = roles + this.text;
			roles = roles + ',';
		});
		roles = roles + "stub.";
		$.ajax({
			url : '${pageContext.request.contextPath}/users/manageRoles/editRoles',
			datatype : 'json',
			data : ({
				sendingRoles : roles,
				userID : $("#userid").text()
			}),
			success : function(result) {
				$("#userRolesManagmentResult").show();
				$("#userRolesManagmentResult").text("User roles were successfully edited");
				//$("#userRolesManagmentResult").text(usersLocVar[0]);
			},
		});
	});
});

function fillTable(uList) {
	var response = $.parseJSON(uList);
	var trHTML = '';
	$.each(response, function(i, item) {
		locked = 'lock';
		if (item.isBlocked == true) {
			locked = '<div class="usergl" id="' + item.id + '"><i class="glyphicon glyphicon-ok-circle"></i></div>';
		} else {
			locked = '<div class="usergl" id="' + item.id + '"><i class="glyphicon glyphicon-ban-circle red"></i></div>';
		}
		rolesManagment = '<a href="manageRoles/' + item.id + '"><i class="glyphicon glyphicon-user"></i></a>';

		trHTML += '<tr><td>' + item.email + '</td><td>'
		+ item.firstName + '</td><td>'
		+ item.lastName + '</td><td>' + locked
		+ '</td><td>' + rolesManagment
		+ '</td></tr>';
	});
	$('#usersTable tbody').remove();
	$('#usersTable').append(trHTML);
}

/*
 * On page change / items per page change
 */
function updateUsersOnPage(pageNum) {
	var req1 = $.ajax({
		url : '${pageContext.request.contextPath}/users/list/getUserCount.html',
		success : function(userCount) {
			uCount = $.parseJSON(userCount);
		}
	});

	$.when(req1).done(function() {
		$.ajax({
			url : '${pageContext.request.contextPath}/users/list/getUserList.html',
			datatype : 'json',
			data : ({
				sendingValue : $("#userPagingPar").val(),
				pageNumber : pageNum
			}),
			success : function(userList) {
				fillTable(userList);
			}
		});
	});
};

function userPagination() {
	var uCount = 0;
	var req1 = $.ajax({
		url : '${pageContext.request.contextPath}/users/list/getUserCount.html',
		success : function(userCount) {
			uCount = $.parseJSON(userCount);
		}
	});
		
	$.when(req1).done(function() {
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

</script>