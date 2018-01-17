var app =angular.module('ToDo');
app.factory('dummyService',function($http,$auth)
		{
	console.log("coming servive");
			var token ={};
			token.getToken=function()
			{
				return $http({
					method : 'GET',
					url : 'getToken'
				})
			}
			return token;
		});


