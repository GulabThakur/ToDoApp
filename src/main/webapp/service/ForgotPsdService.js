var app=angular.module('ToDo');
app.factory("forgotPsdService",function($http,$location)
		{
			var forgot={};
			forgot.forgotUser=function(user)
			{
				return $http({
					method :"post",
					url:"test",
					data: user,
					//urlPath:"reset.html"
				})
			}
			return forgot;
		});