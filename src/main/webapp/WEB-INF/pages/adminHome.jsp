<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
        <div class="container">
            <h1 class="text-center">
           		 <spring:message code="adminhome.controlPanel" />

            </h1>
            <div class="row">
                <div class="col-md-3 col-sm-6">
                    <div class="admin-box">
                        <a href="${pageContext.request.contextPath}/transport/transportList"><i class="glyphicon glyphicon-plane"></i> 
							<spring:message code="adminHome.transport" />
						</a>
                    </div>
                </div>
                <div class="col-md-3 col-sm-6">
                    <div class="admin-box">
                        <a href="${pageContext.request.contextPath}/countries/countryList"><i class="glyphicon glyphicon-globe"></i> 
                        <spring:message code="adminHome.country" />
                        </a>
                    </div>
                </div>
                <div class="col-md-3 col-sm-6">
                    <div class="admin-box">
                        <a href="${pageContext.request.contextPath}/cities/citiesList"><i class="glyphicon glyphicon-home"></i> 
                        <spring:message code="adminHome.cities" />
                        </a>
                    </div>
                </div>
                <div class="col-md-3 col-sm-6">
                    <div class="admin-box">
                        <a href="${pageContext.request.contextPath}/routeconnection"><i class="glyphicon glyphicon-road"></i>
                        <spring:message code="adminHome.connections" />
                        </a>
                    </div>
                </div>
                <div class="col-md-3 col-sm-6">
                    <div class="admin-box">
						<a href="${pageContext.request.contextPath}/routes/savedRoutesAdmin"><i class="glyphicon glyphicon-map-marker"></i>
						<spring:message code="adminHome.routes" /></a> 
					</div>
                </div>
                <div class="col-md-3 col-sm-6">
                    <div class="admin-box">
                        <a href="${pageContext.request.contextPath}/users/list"><i class="glyphicon glyphicon-user"></i>
						<spring:message code="adminHome.users" />
						</a>
                    </div>
                </div>
                <div class="col-md-3 col-sm-6">
                    <div class="admin-box">
                        <a href="${pageContext.request.contextPath}/securityRoles/roles"><i class="glyphicon glyphicon-star-empty"></i>
                        <spring:message code="adminHome.roles" />
                        </a>
                    </div>
                </div>
                <div class="col-md-3 col-sm-6">
                    <div class="admin-box">
                        <a href="profile"><i class="glyphicon glyphicon-cog"></i>
                        <spring:message code="adminhome.profile" />
                        </a>
                    </div>
                </div>
            </div>
        </div>
    
