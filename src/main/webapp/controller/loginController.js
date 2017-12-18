//for using login 
var app = angular.module('ToDo');
app.controller('loginController', function($scope, loginService, $location,
		$state) {
	console.log("it came into login");
	$scope.loginClick = function() {
		var a = loginService.loginUser($scope.user);
		// when you password and email is correct
		a.then(function(response) {
			console.log(response.data.message);
			// localStorage.setItem('token',response.data.message);
			localStorage.setItem('jwt',response.data.message);
		
			console.log("loginsucessfull");
			// $location.path('homepage');
			$state.go('homepage');
		}, function(response) { // when password are wrong..
			console.log("when password is wrong.. ");
			$scope.error = response.data.message;

		});

	}
});
