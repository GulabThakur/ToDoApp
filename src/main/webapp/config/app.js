/*******************************************************************************
 * @author ThakurGulab
 * @description
 * @created
 */

var app = angular.module('ToDo', [ 'ui.router','ngSanitize','angular-img-cropper','ngMaterial' ,'satellizer','tb-color-picker','ngMaterialDatePicker','ngFileUpload' ]);

var skipIfAuthenticated=function($q, $state, $auth,$timeout) {
	  var defer = $q.defer();
  if($auth.isAuthenticated()) {
	    $timeout(function () {
	    	$state.go('homepage'); 
	    });
	    //defer.reject(); 
	  } else {
	    defer.resolve();
	  }
	  return defer.promise;
	}
	
 var redirectIfNotAuthenticated =function($q, $state, $auth ,$timeout) {
	
		  var defer = $q.defer();
	  if($auth.isAuthenticated()) {
	    defer.resolve();
		  } else {
		    $timeout(function () {
		      $state.go('login'); 
		    });
		   // defer.reject();
		  }
		  return defer.promise;
	  
		}
app.config( function($stateProvider, $urlRouterProvider ,$authProvider) {
	$authProvider.tokenName = 'token';
	$stateProvider
	// this code will be use for login CrtL and load HtmL..
	.state('login', {
		url : '/login',
		// template: 'login',
		templateUrl : 'template/login.html',
			resolve: {
			        skipIfAuthenticated: skipIfAuthenticated
			      },
		controller : 'loginController'
	})
	// this code will be use for register CrtL and load HtmL..
	.state('registration', {
		url : '/registration',
		templateUrl : 'template/registration.html',
		controller : 'registerController'
	})
	// this code will be use for forgot password CrtL and load HtmL..
	.state('forgotPassword', {
		url : '/forgotPassword',
		templateUrl : 'template/forgotPassword.html',
		controller : 'forgotPasswodCrtl'
	})
	// this code will be use for reset password CrtL and load HtmL..
	.state('resetpassword', {
		url : '/resetpassword',
		templateUrl : 'template/resetpassword.html',
		controller : 'resetPsdCrtl'
	})
	// this code will come for home page
	.state('homepage', {
		url : "/homepage",
		templateUrl : 'template/homepage.html',
			/*resolve: {
	        redirectIfNotAuthenticated: redirectIfNotAuthenticated
	      },*/
		controller : 'homepageCrt'
	})

	// this use for load TrashHtml file...
	.state('TrashLoad', {
		url : "/TrashLoad",
		templateUrl : 'template/TrashLoad.html',
		controller : 'homepageCrt'
	})
	// this use for load ArchiveHtml file
	.state('ArchiveLoad', {
		url : "/ArchiveLoad",
		templateUrl : 'template/ArchiveLoad.html',
		controller : 'homepageCrt'
	})
	
	// this user for Dummy Home
	.state('DummyHome',{
		url :"/DummyHome",
		templateUrl :'template/DummyHome.html',
		controller :'dummyHomeCrt'
	})
	
	.state('Search',{
		url:"/Search",
		templateUrl :'template/Search.html',
		controller :'homepageCrt'
	})
	// THIS FUNCTION ....
	.state('Reminder',{
		url:"/Reminder",
		templateUrl :'template/ReminderLoad.html',
		controller :'homepageCrt'
	})
	
	
	
	$urlRouterProvider.otherwise('/login');
});
