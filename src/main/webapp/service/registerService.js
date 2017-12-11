var app =angular.module('ToDo');
app.factory('registrationService',function($http,$location)
{
	var register={};
	register.registerUser=function(user)
	{
		return $http({
			method :"post",
		    url:"register",
		    data:user
		   
		})
	}
	return register;
});
