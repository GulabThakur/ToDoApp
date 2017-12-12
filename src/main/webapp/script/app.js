var app = angular.module('ToDo', [ 'ui.router', 'ngMaterial' ]);

app.config(function($stateProvider, $urlRouterProvider) {
	$stateProvider
	// this code will be use for login CrtL and load HtmL..
	.state('login', {
		url : '/login',
		// template: 'login',
		templateUrl : 'template/login.html',
		controller : 'loginController'
	})
	// this code will be use for register CrtL and load HtmL..
	.state('registration', {
		url : '/registration',
		templateUrl : 'template/registration.html',
		controller : 'registerController'
	})
	// this code will be use for forgot password CrtL and load HtmL..
	.state('forgotPassword', {
		url : '/forgotPassword',
		templateUrl : 'template/forgotPassword.html',
		controller : 'forgotPasswodCrtl'
	})
	// this code will be use for reset password CrtL and load HtmL..
	.state('resetpassword', {
		url : '/resetpassword',
		templateUrl : 'template/resetpassword.html',
	    controller:'resetPsdCrtl'
	})

	$urlRouterProvider.otherwise('/login');
});
