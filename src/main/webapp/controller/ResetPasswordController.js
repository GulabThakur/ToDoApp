//this function call when you want to reset password
var app = angular.module('ToDo');
app.controller('resetPsdCrtl', function($scope, resetPasswordService,
		$location, $state) {
	// when click submit button this function call..
	$scope.SubmitClick = function() {
		var restPsd = resetPasswordService.resetPsdUser($scope.user);
		restPsd.then(function(response) {
			console.log("your password changed");
			$state.go('login');
			// if you are enter wrong password then this function will
			// call
		}, function(response) {
			console.log(response.data);
			$scope.errormessage = response.data.message;
			console.log(errormessage);

		});
	}
});