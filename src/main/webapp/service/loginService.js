var app=angular.module('ToDo');
app.factory("loginService",
	
	function($http,$location)
		{
	console.log("response will come");
			var login={};
			login.loginUser=function(user)
			{
				return $http({
					method :"post",
					url:"login",
					data: user
				})
			}
			return login;
			
			login.getToken=function(){
				return $http({
					method:"get",
					url:'getToken'
				});
			}
			
		return login;
		
		});