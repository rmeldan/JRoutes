<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<nav class="navbar navbar-inverse">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<h1>
				<a class="navbar-brand" href="${pageContext.request.contextPath}">
					<i class="glyphicon glyphicon-th"></i> <span class="green">J</span>Routes
				</a>
			</h1>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class="active"><a href="${pageContext.request.contextPath}"><spring:message
							code="home.main" /></a></li>
			</ul>
			<sec:authorize access="hasRole('ROLE_ANONYMOUS')">
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <i class="glyphicon glyphicon-flag"></i>
							<b class="caret"></b>
					</a>
						<ul class="dropdown-menu" role="menu">
							<li class="flag"><a href="?lang=en"> <img
									src="<c:url value="/resources/images/UnitedKingdom.png" />"
									alt="UnitedKingdom" class="img-responsive" /> EN
							</a></li>
							<li class="flag"><a href="?lang=ua"> <img
									src="<c:url value="/resources/images/Ukraine.png" />"
									alt="Ukraine" class="img-responsive" /> UA
							</a></li>
						</ul></li>
					<!--li><a href="#" data-toggle="modal"
					data-target="#myModal-login"> <span
						class="glyphicon glyphicon-arrow-right"></span> <spring:message code="home.login" />
				</a></li-->
					<li><a href="admin"> <span
							class="glyphicon glyphicon-arrow-right"></span> <spring:message
								code="home.login" />
					</a></li>
					<li><a href="registration"> <spring:message
								code="home.registration" />
					</a></li>
				</ul>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
				<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <i class="glyphicon glyphicon-flag"></i>
							<b class="caret"></b>
					</a>
						<ul class="dropdown-menu" role="menu">
							<li class="flag"><a href="?lang=en"> <img
									src="<c:url value="/resources/images/UnitedKingdom.png" />"
									alt="UnitedKingdom" class="img-responsive" /> EN
							</a></li>
							<li class="flag"><a href="?lang=ua"> <img
									src="<c:url value="/resources/images/Ukraine.png" />"
									alt="Ukraine" class="img-responsive" /> UA
							</a></li>
						</ul></li>
					<li><a href="profile"> Hello <i
							class="glyphicon glyphicon-user"></i> <security:authentication
								property="principal.username" />
					</a></li>
					
					<li><a href="/jroutes/j_spring_security_logout"> <i
							class="glyphicon glyphicon-off"></i> Exit
					</a></li>
				</ul>
			</sec:authorize>
			
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>