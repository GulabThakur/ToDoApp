var app = angular.module('ToDo');
app.factory('resetPasswordService', function($http, $location) {

	var resetPsd = {};
	resetPsd.resetPsdUser = function(user) {
		return $http({
			method : "put",
			url : "reset",
			data : user
		})
	}
	return resetPsd;
})