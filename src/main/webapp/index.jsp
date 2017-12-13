<html>
<head>
<!-- All css files are here -->

<link rel="stylesheet" ; href="css/login.css" />
<link re="stylesheet" ; href="css/registration.css">
<link rel="stylesheet" ;href="css/forgotpassword.css">
<link rel="stylesheet"
	href="bower_components/angular-material/angular-material.min.css" />
<!-- <script src = "bower_components/angular/angular.js"></script> -->
<script src="bower_components/angular/angular.js"></script>
<script src="bower_components/angular-animate/angular-animate.min.js"></script>
<script src="bower_components/angular-aria/angular-aria.min.js"></script>

<!-- <script src = "bower_components/angular-messages/angular-messages.min.js"></script> -->

<script src="bower_components/angular-material/angular-material.min.js"></script>
<script
	src="bower_components/angular-ui-router/release/angular-ui-router.js"></script>

<!-- All script folders file is here -->

<script type="text/javascript" src="script/app.js"></script>

<script type="text/javascript" src="directive/navbar.js"></script>
<!-- All Services are here -->

<script type="text/javascript" src="service/registerService.js"></script>
<script type="text/javascript" src="service/loginService.js"></script>
<script type="text/javascript" src="service/ForgotPsdService.js"></script>
<script type="text/javascript" src="service/ResetService.js"></script>
<!-- All Controllers are here -->
<script type="text/javascript" src="controller/loginController.js"></script>
<script type="text/javascript" src="controller/RegisterController.js"></script>
<script type="text/javascript" src="controller/ForgotPassword.js"></script>
<script type="text/javascript" src="controller/ResetPasswordController.js"></script>




 

</head>
<body ng-app='ToDo'>

	<div ui-view></div>
</body>

</html>