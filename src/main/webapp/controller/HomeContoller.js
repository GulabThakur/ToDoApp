var app = angular.module('ToDo');
app.controller('homepageCrt', function($scope, homeService, $location,
		$state) {
	$scope.addNode = function() {
		var a = homeService.nodeCreate($scope.note);
		// when you password and email is correct
		a.then(function(response) {
			console.log("response will come");
			console.log(ab);
		}, function(response) { // when password are wrong..
			$scope.error = response.data.message;

		});

	}
});