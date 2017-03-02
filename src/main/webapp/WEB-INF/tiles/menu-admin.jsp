<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<nav class="navbar navbar-inverse" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <h1>
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/adminHome"><i class="glyphicon glyphicon-th"></i>
                        <span class="green">J</span>Routes
                    </a>
                </h1>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
					<li class="${actTransport}"><a href="${pageContext.request.contextPath}/transport/transportList">
						<spring:message code="adminHome.transport" /></a>
					</li>
                    <li class="${actCountries}"><a href="${pageContext.request.contextPath}/countries/countryList">
                    <spring:message code="adminHome.country" /></a>
                    </li>
                    <li class="${actCities}"><a href="${pageContext.request.contextPath}/cities/citiesList">
                     <spring:message code="adminHome.cities" /></a>
					</li>
                 	<li class="${actRouteConnection}"><a href="${pageContext.request.contextPath}/routeconnection">
                 	<spring:message code="adminHome.connections" /></a>
                 	</li>
                    <li class="${actRoutes}"><a href="${pageContext.request.contextPath}/routes/savedRoutesAdmin">
					<spring:message code="adminHome.routes" /></a>
					</li>
                    <li class="${actUsers}"><a href="${pageContext.request.contextPath}/users/list">
                    <spring:message code="adminHome.users" /></a>
                    </li>
                    <li class="${actRoles}"><a href="${pageContext.request.contextPath}/securityRoles/roles">
                     <spring:message code="adminHome.roles" /></a>
                     </li>
                 </sec:authorize>
                 <sec:authorize access="hasRole('ROLE_USER')">
                 	<li class="${actSavedRoutes}"><a href="${pageContext.request.contextPath}/savedRoutesUser">
						<spring:message code="routes.savedRoutes" />
					</a></li>
                 </sec:authorize>                  
                 <li class="${actProfile}"><a href="${pageContext.request.contextPath}/profile">
                 <spring:message code="adminhome.profile" /></a>
                 </li>                
                </ul>
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
                    <li>
                        <a href="${pageContext.request.contextPath}/profile">
                            <i class="glyphicon glyphicon-user"></i> <security:authentication property="principal.username" /> 
                        </a>
                    </li>
                    <li>
                        <a href="/jroutes/" target="_blank">
                            <i class="glyphicon glyphicon-share"></i>
                             <spring:message code="menuAdmin.homePage" />
                        </a>
                    </li>
                    <li>
                        <a href="/jroutes/j_spring_security_logout">
                        <i class="glyphicon glyphicon-off"></i> 
						<spring:message code="menuAdmin.exit" />
                        </a>
                    </li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </nav>