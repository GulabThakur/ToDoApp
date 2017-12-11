var app=angular.module('ToDo');
app.factory("loginService",function($http,$location)
		{
			var login={};
			login.forgotUser=function(user)
			{
				return $http({
					method :"post",
					url:"login",
					data: user
				})
			}
			return login;
		});