var app = angular.module('ToDo');
app.controller('resetPsdCrtl',
		function($scope, resetPasswordService, $location ) {
			$scope.SubmitClick = function() {
				var restPsd = resetPasswordService.resetPsdUser($scope.user);
				restPsd.then(function(response) {
					
					console.log("welcome error free");
				}, function(response) {
					console.log(response.data);
					$scope.errormessage = response.data.message;
					console.log(errormessage);
					
				});
			}
		});