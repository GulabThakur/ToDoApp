<html>
<head>
<!-- All css files are here -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<link rel="stylesheet"
	href="bower_components/node_modules/angular-material/angular-material.min.css" />
<link rel="stylesheet"
	href="bower_components/angular-material-datetimepicker/dist/material-datetimepicker.min.css"/>


<link rel="stylesheet" ; href="css/navar.css"/>
<link rel="stylesheet" ; href="css/sidebar.css"/>
<link rel="stylesheet" ; href="css/login.css" />
<!-- <link re="stylesheet" ;  href="css/registration.css"/> -->
<link rel="stylesheet"; href="css/colorPicker.css"/>

<!-- <link rel="stylesheet" ; href="css/forgotpassword.css"> -->
<link rel="stylesheet" ; href="css/deshboard.css"/>


<!-- <script src = "bower_components/angular/angular.js"></script> -->
<script src="https://code.jquery.com/jquery-1.6.1.min.js"></script>	

<!-- <script src="bower_components/bower_components/angular/angular.js"></script> -->

	<!-- =====================================this changes by sir========================== -->
<script type="text/javascript" src="bower_components/bower_components/angular/angular.min.js"></script>
<script
	src="bower_components/bower_components/angular-ui-router/release/angular-ui-router.js"></script>
<script src="bower_components/angular-material-datetimepicker/dist/angular-material-datetimepicker.min.js"></script>
<script src="bower_components/bower_components/ng-file-upload/ng-file-upload.js"></script> 
<script type="text/javascript" src="bower_components/bower_components/ng-file-upload/ng-file-upload.min.js"></script>
<script src="bower_components/node_modules/angular-animate/angular-animate.min.js"></script>
 
<script src="bower_components/node_modules/angular-messages/angular-messages.min.js"></script>
<script src="bower_components/moment/moment.js"></script> 

<script src="bower_components/node_modules/angular-aria/angular-aria.min.js"></script>
<script src="bower_components/node_modules/angular-material/angular-material.min.js"></script>
<!-- for uploading  image   -->
<script src="https://connect.facebook.net/enUS/all.js"></script>


<script src="bower_components/node_modules/angular-img-cropper/src/angular-img-cropper.js"></script>
<script src="bower_components/node_modules/satellizer/dist/satellizer.js"></script>
<script src="bower_components/node_modules/satellizer/dist/satellizer.min.js"></script>
<!-- <script src = "bower_components/angular-messages/angular-messages.min.js"></script> -->
<!--  for link maker-->

<script src="bower_components/node_modules/angular-sanitize/angular-sanitize.js"></script> 
<script src="bower_components/node_modules/angular-sanitize/angular-sanitize.min.js"></script>




<!-- All script folders file is here -->

<script type="text/javascript" src="config/app.js"></script>

<script type="text/javascript" src="directive/navbar.js"></script>
<script type="text/javascript" src="directive/dashbar.js"></script>
<script type="text/javascript" src="directive/sidebar.js"></script>
<script type="text/javascript" src="directive/TrashBoard.js"></script>
<script type="text/javascript" src="directive/Archive.js"></script>
<script type="text/javascript" src="directive/ColorPicker.js"></script>
<script type="text/javascript" src="directive/CustomCard.js"></script>
<script type="text/javascript" src="directive/Reminder.js"></script>
<!-- <script type="text/javascript" src="directive/colourePicker.js"></script> -->

<!-- for color picker    -->
<!-- <script type="text/javascript" src="ColourePicker/colourePicker.js"></script> -->

<!-- All Services are here -->
<script type="text/javascript" src="service/registerService.js"></script>
<script type="text/javascript" src="service/loginService.js"></script>
<script type="text/javascript" src="service/ForgotPsdService.js"></script>
<script type="text/javascript" src="service/ResetService.js"></script>
<script type="text/javascript" src="service/HomeService.js"></script>
<script type="text/javascript" src="service/DummyService.js"></script>


<!-- All Controllers are here -->
<script type="text/javascript" src="controller/loginController.js"></script>
<script type="text/javascript" src="controller/RegisterController.js"></script>
<script type="text/javascript" src="controller/ForgotPassword.js"></script>
<script type="text/javascript" src="controller/ResetPasswordController.js"></script>
<script type="text/javascript" src="controller/HomeContoller.js"></script>
<script type="text/javascript" src="controller/DummyHome.js"></script>

</head>
<body ng-app='ToDo'>

	<div ui-view></div>
</body>

</html>