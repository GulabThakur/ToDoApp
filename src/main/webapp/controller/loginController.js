var app=angular.module('ToDo');
app.controller('loginController',function ($scope,loginService,$location)
{
	console.log("it came into login");
	$scope.loginClick=function()
	{
		var a=loginService.loginUser($scope.user);
		
		a.then(function(response)
				{
					console.log(response.data.message);
					localStorage.setItem('token',response.data.message);
					console.log("loginsucessfull");
				}),function(response)
				{
					if(response.status=409)
					{
						$scope.error=response.data.message;
					}
					else{
						console.log("fail");
						$scope.error="Enter the valid data";
					}
				}
		
	}
});
