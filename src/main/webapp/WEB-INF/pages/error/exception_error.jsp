<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

<link rel="stylesheet" href='<c:url value="/resources/css/404.css" />'
	media="screen, projection" />
<link rel="stylesheet" type="text/css" media="all"
	href='<c:url value="/resources/css/tripsy.css" />' />
<link rel="stylesheet" type="text/css" media="all"
	href='<c:url value="/resources/css/fonts.css" />' />

<!--[if lt IE 8]>
	<link rel="stylesheet" type="text/css" href='<c:url value="/resources/css/ie7.css" />' />
<![endif]-->

<script src='<c:url value="/resources/js/jquery-1.7.2.min.js" />'></script>
<!-- jQuery implementation -->
<script src='<c:url value="/resources/js/custom-scripts.js" />'></script>
<!-- All of my custom scripts -->
<script src='<c:url value="/resources/js/jquery.tipsy.js" />'></script>
<!-- Tipsy -->

<script type="text/javascript">
	$(document).ready(function() {

		universalPreloader();

	});

	$(window).load(function() {

		//remove Universal Preloader
		universalPreloaderRemove();

		rotate();
		dogRun();
		dogTalk();

		//Tipsy implementation
		$('.with-tooltip').tipsy({
			gravity : $.fn.tipsy.autoNS
		});

	});
</script>
</head>

<body>

	<div id="wrapper">
		<!-- 404 graphic -->
		<div class="graphic">
			<h2 class="not-found-text">Було виявлено помилку, <br/>ми стараємось її вирішити</h2>
		</div>
		<!-- 404 graphic -->

		<!-- Text, search form and menu -->
		<div class="top-left">
			<!-- Not found text -->
			<div class="not-found-text">
				
					<h1 class="not-found-text">OOOPS...Try to:</h1>
				

			</div>
			<!-- Not found text -->


			<!-- top menu -->
			<div class="top-menu">
				<a href="${pageContext.request.contextPath}" class="with-tooltip"
					title="Return to the home page">Return to HOME Page</a>
					<br/>
				<a href="#" onclick="history.go(-1);return false;">Return to preview Page</a>
			</div>
			<!-- top menu -->
		</div>
		<!-- Text, search form and menu -->

		<!-- planet at the bottom -->
		<div class="planet">
			<div class="dog-wrapper">
				<!-- dog running -->
				<div class="dog"></div>
				<!-- dog running -->

				<!-- dog bubble talking -->
				<div class="dog-bubble"></div>

				<!-- The dog bubble rotates these -->
				<div class="bubble-options">
					<p class="dog-bubble">Are you lost, bud? No worries, I'm an
						excellent guide!</p>
					<p class="dog-bubble">
						<br /> Arf! Arf!
					</p>
					<p class="dog-bubble">
						<br /> Don't worry! I'm on it!
					</p>
					<p class="dog-bubble">
						I wish I had a cookie<br />
						<img style="margin-top: 8px"
							src='<c:url value="/resources/images/cookie.png" />' alt="cookie" />
					</p>
					<p class="dog-bubble">
						<br /> Geez! This is pretty tiresome!
					</p>
					<p class="dog-bubble">
						<br /> Am I getting close?
					</p>
					<p class="dog-bubble">Or am I just going in circles? Nah...</p>
					<p class="dog-bubble">
						<br /> OK, I'm officially lost now...
					</p>
					<p class="dog-bubble">
						I think I saw a <br />
						<img style="margin-top: 8px"
							src='<c:url value="/resources/images/cat.png" />' alt="cat" />
					</p>
					<p class="dog-bubble">What are we supposed to be looking for,
						anyway? @_@</p>
				</div>
				<!-- The dog bubble rotates these -->
				<!-- dog bubble talking -->
			</div>

			<!-- planet image -->
			<img src='<c:url value="/resources/images/planet.png" />'
				alt="planet" />
			<!-- planet image -->
		</div>
		<!-- planet at the bottom -->
	</div>

</body>
</html>
