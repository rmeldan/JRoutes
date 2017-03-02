<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script type="text/javascript" src="<c:url value="/resources/js/jQuery.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/country.valid.js" />"></script>
<script>
/*$(document).ready(function(){
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
});*/
</script>


<div class="container adminHome">
    <div id="users">
    <h2 class="border-bottom-green">
					<i class="glyphicon glyphicon glyphicon-user"></i> 
					 <spring:message code="users.users" />
				</h2><br>
				<!--h3 id="ttt">default</h3-->
    <h3 id="mytext"><spring:message code="users.recordsPerPage" />: </h3>
    				<select class="combobox" id="userPagingPar">
 						 <option value="2">2</option>
  						 <option value="5">5</option>
 						 <option value="10">10</option>
					</select>
    <c:if test="${not empty infoMessage}">
    					<div class="alert alert-success">${infoMessage}</div>
					</c:if>
					<!--div class="alert alert-success" id="messageSucces"></div-->
					
        <div class="table-responsive">
            <table class="table" id="usersTable">
                <thead>
                <tr>
                    <th><spring:message code="users.email" /></th>
                    <th><spring:message code="users.fname" /></th>
                    <th><spring:message code="users.lname" /></th>
                    <th><spring:message code="users.lock" /></th>
                    <th><spring:message code="users.role" /></th>
                </tr>
                </thead>
                <tbody>
                
                <c:forEach var="user" items="${users}">
						<tr>
							<td>${user.email}</td>
							<td>${user.firstName}</td>
							<td>${user.lastName}</td>
							<td>
								<c:if test="${user.isBlocked == true}">   
    								<a href="unlock/${user.id}"><i class="glyphicon glyphicon-ok-circle"></i></a>
								</c:if>
								<c:if test="${user.isBlocked == false}">   
    								<a href="lock/${user.id}"><i class="glyphicon glyphicon-ban-circle red"></i></a>
								</c:if>
								
							</td>
							<td><a href="manageRoles/${user.id}"><i class="glyphicon glyphicon-user"></i></a>
							</td>
						</tr>
					</c:forEach>
                </tbody>
            </table>
            <!-- Modal -->
            <div class="modal fade" id="role" tabindex="-1" role="dialog" aria-labelledby="roleLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h3 class="modal-title" id="roleLabel">Всі ролі</h3>
                        </div>
                        <div class="modal-body">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox"> Admin
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" checked> User
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox"> Manager
                                </label>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Закрити</button>
                            <button type="button" class="btn btn-success">Зберегти зміни</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
       <!--h3>Сторінка для відображення: </h3>
    				<select class="combobox" id="userPageNumber">
						<option value="2">2</option>
  						 <option value="5">5</option>
 						 <option value="10">10</option>
					</select-->
					
					<div id="UserPagination"></div>
    	
    </div>
</div>
