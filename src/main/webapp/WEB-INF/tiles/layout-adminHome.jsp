<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<tiles:insertAttribute name="header-admin" />
</head>
<body>
<div id="wrap" class="admin-panel">
	<div id="main" class="clearfix">
		<div class="displayNone"><tiles:insertAttribute name="title" /></div>
		<tiles:insertAttribute name="menu-admin" />
		<tiles:insertAttribute name="body" />
	</div>
</div>
<tiles:insertAttribute name="footer-admin" />

</body>
</html>