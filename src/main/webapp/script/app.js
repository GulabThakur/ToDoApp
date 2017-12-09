var app = angular.module('ToDo', [ 'ui.router', 'ngMaterial']);

app.config(
		function($stateProvider, $urlRouterProvider) {
			$stateProvider
			.state('login', {
				url : '/login',
				//template: 'login',
				templateUrl : 'template/login.html',
				controller : 'loginController'
			})
			.state('registration', {
				url : '/registration',
				templateUrl : 'template/registration.html',
				//controller : 'registerController'
			})
			
			.state('home', {
				url : '/home',
				templateUrl : 'template/home.html',
				//controller : 'registerController'
			})
			$urlRouterProvider.otherwise('/login');
} );