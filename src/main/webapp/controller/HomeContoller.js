/**
 * @author ThakurGulab
 * @Decription home controller
 * 
 */
// this this my home controller ....
var app = angular.module('ToDo');
app.controller('homepageCrt', function($scope, homeService, $location, $state,
		$window, $mdToast, $document, mdcDateTimeDialog, $filter, $interval,
		$mdDialog) {

	
	
	/*================================================================================================================*/
	
	var userData = null;
	$scope.showAlert = function(event, note) {
		var parentEl = angular.element(document.body);
		/*$scope.owner(note);*/
		$scope.getUser(note);
		$mdDialog
				.show({
					parent : parentEl,
					targetEvent : event,
					templateUrl : 'template/collaboratore.html',
					locals : {
						data : note,
						owner : $scope.ownerdetails,
						userListData:$scope.userList
					},
					clickOutsideToClose : true,
					controller : function($scope, data, owner, homeService) {
						$scope.note = data;
						$scope.user = owner
						
						/* console.log(user); */
						$scope.closeDialog = function(note,email) {
							var token = localStorage.getItem('jwt');
							var nodes = homeService.getAllnode("notesShare/"+email+"/"+note.id+"", "post", token, null);
							console.log(note.id);
							console.log(email);
							console.log("close dilog box");
							 $mdDialog.hide();
						}
						$scope.removeCollab=function(note,email){
							 $mdDialog.hide();
							var token =localStorage.getItem('jwt');
							var deleteUser = homeService.getAllnode("deleteUser/"+email+"", "post", token, note);
							deleteUser.then(function(response){
								console.log("Remove User");
							},function(response){
								console.log("helllo "+response.data.message)
							});
							
						}
					}
				});
		console.log("comint complete show alert");
	}
	
	/*================================================================================================================*/
	
	$scope.removeUser=function(note,email){
		var token =localStorage.getItem('jwt');
		var deleteUser = homeService.getAllnode("deleteUser/"+email+"", "post", token, note);
		deleteUser.then(function(response){
			console.log("Remove User");
		},function(response){
			console.log("helllo "+response.data.message)
		});
	}

	/*================================================================================================================*/
	
	$scope.getUser=function(note){
		var token=localStorage.getItem('jwt');
		var userData=homeService.getAllnode("collabUser","post",token,note);
		userData.then(function(response){
			$scope.userList=response.data;
			console.log(response.data);
		},function(response){
			console.log("hello"+response.data.message);
		});
	}
	
	// this method is get owner...
	
	
	/*================================================================================================================*/
	
    // this function use for share note 
	$scope.shareNote = function(note, email) {
	   var token = localStorage.getItem('jwt');
	   var nodes = homeService.getAllnode("notesShare/"+email+"/"+note.id+"", "post", token, null);
	   nodes.then(function(response) {
			//$scope.user = response.data;
			//$scope.ownerdetails=response.data;
		   console.log("welcome to share note");
		}, function(response) {
			console.log(response.data);
		});
	}

	/*================================================================================================================*/
	
	// this method using for call profile..
	$scope.ownerdetails = {};
	$scope.profileData = function() {
		var url = "userData";
		var method = "post";
		var token = localStorage.getItem('jwt');
		var data = null;
		var nodes = homeService.getAllnode(url, method, token, data);
		nodes.then(function(response) {
			$scope.user = response.data;
			$scope.ownerdetails=response.data;
		}, function(response) {
			console.log(response.data);
		});

	}

	/*================================================================================================================*/
	
	
	$scope.profileData();

	// this is function for create note
	$scope.addNode = function() {
		$scope.allNodes();
		var url = "create";
		var method = "post";
		var token = localStorage.getItem('jwt');
		var data = $scope.note;
		var nodes = homeService.getAllnode(url, method, token, data);
		nodes.then(function(response) {
			console.log("Note created ");
			$scope.allNodes();
		}, function(response) {
			console.log(response.data.message);
		});

	}
	
	/*================================================================================================================*/
	
	// get all note with help this function ....
	$scope.allNodes = function() {
		console.log("inside all note");
		var url = "all";
		var method = "post";
		$scope.notes = [];
		var token = localStorage.getItem('jwt');
		var data = null;
		console.log("inside all note")
		var nodes = homeService.getAllnode(url, method, token, data);
		nodes.then(function(response) {
			$scope.notes = response.data;
			console.log(response.data);
			console.log("fetch the data sucessfull ");

			$scope.data = response.data;
			var notes = response.data;
			console.log("Notes---->" + $scope.data.length);
			$interval(function() {
				for (var i = 0; i < $scope.data.length; i++) {
					if (notes[i].reminder != null) {
						reminderDate = $filter('date')(
								new Date(notes[i].reminder),
								'MMM dd yyyy HH:mm');
						var currentDate = $filter('date')(new Date(),
								'MMM dd yyyy HH:mm');
						console.log("system Date----->" + currentDate);
						console.log("Reminder Date------>" + reminderDate);
						if (currentDate == reminderDate) {
							alert(notes[i].description);
							notes[i].reminder = null;
							$scope.updateById(notes[i]);
						}
					}
				}
			}, 50000);
			$scope.notes = response.data;
		}, function(response) {
			var rep = response.data.message;
			console.log(rep);
		});
	}
	
	/*================================================================================================================*/
	
	// here i am call automatically allnodes();
	$scope.allNodes();
	$scope.getById = function() {
		var url = "record";
		var method = "get";
		var token = localStorage.getItem('jwt');
		// var data=$scope.note;
		var nodes = homeService.getAllnode(url, method, token, data);
		nodes.then(function(response) {
			console.log("fetch the data sucessfull by id ");
		}, function() {
			console.log(response.data.message);
		});
	}
	
	/*================================================================================================================*/
	
	// note will be delete by id
	$scope.deletebyId = function(note) {
		var url = "delete/" + note.id;
		var method = "delete";
		var token = null;
		var data = null;
		var nodes = homeService.getAllnode(url, method, token, data);
		nodes.then(function(response) {
			console.log("delete the data sucessfull by id ");
			$scope.allNodes();
		}, function() {
			console.log(response.data.message);
		});
	}
	
	/*================================================================================================================*/
	
	// note will be update by id....
	$scope.updateById = function(note) {
		var url = "update/" + note.id;
		var method = "put";
		var token = localStorage.getItem('jwt');
		var data = note;
		// console.log(note);
		var nodes = homeService.getAllnode(url, method, token, data);
		nodes.then(function(response) {
			console.log("update the data sucessfull by id ");
			$scope.allNodes();
		}, function(response) {
			console.log(response.data.meResponse);
		});
	}
	
	/*================================================================================================================*/
	
	// archive on
	$scope.archive = function(note) {
		$mdToast.show($mdToast.simple().textContent('Note Archived ..')
				.hideDelay(3000))
		if (note.archive == false) {
			note.archive = true;

		} else {
			note.archive = false;
		}
		$scope.updateById(note);
		console.log(note);
	}
	
	/*================================================================================================================*/
	
	// delete note..
	$scope.trash = function(note) {
		if (note.trash == false) {
			note.trash = true;
		} else {
			note.trash = false;
		}
		$mdToast.show($mdToast.simple().textContent('Note trashed ..')
				.hideDelay(3000)

		)
		$scope.updateById(note);
		console.log("sucessfull...");
	}

	/*================================================================================================================*/
	
	// pin notes....
	$scope.pin = function(note) {

		if (note.pin == false) {
			note.pin = true;
		} else {
			note.pin = false;
		}
		$scope.updateById(note);
		console.log("sucessfull pined");
	}
	/*================================================================================================================*/
	// trashFunction....
	$scope.trashFunction = function() {
		$state.go('TrashLoad');
		console.log("come inside sucessfull")
	}
	
	/*================================================================================================================*/
	// call from notes..
	$scope.notesFunction = function() {
		$state.go('homepage');
		console.log("sucessfull come..");
	}
	
	/*================================================================================================================*/

	// archiveFunction...
	$scope.archiveFunction = function() {
		$state.go('ArchiveLoad');
		console.log("sucessfull come..");
	}
	
	/*================================================================================================================*/
	// for color picker...
	$scope.color = function() {
		$scope.options = [ 'transparent', '#FF8A80', '#FFD180', '#FFFF8D',
				'#CFD8DC', '#80D8FF', '#A7FFEB', '#CCFF90' ];

		$scope.colorChanged = function(newColor, note) {
			note.color = newColor;
			$scope.updateById(note);
			console.log("Hi........" + note.color);
		}

	}
/*================================================================================================================*/
	// for using date time piker....

	$scope.displayDialog = function() {
		mdcDateTimeDialog.show({
			maxDate : $scope.maxDate,
			time : false
		}).then(function(date) {
			$scope.selectedDateTime = date;
			console.log('New Date / Time selected:', date);
		}, function() {
			console.log('Selection canceled');
		});
	}
	/*================================================================================================================*/
	// for reminder function......
	$scope.reminderFunction = function(note, reminderDate) {
		console.log("Inside Remainder..........");
		console.log(note);
		note.reminder = reminderDate;
		var date = new Date(reminderDate);

		var dates = $filter('date')(new Date(), 'MMM dd yyyy HH:mm');
		console.log(dates);
		// var parseDate = Date.parse();
		note.reminder = dates;
		console.log(" notes obj" + note);
		$scope.updateById(note);
		$scope.allNodes();
		
	}
	
	/*================================================================================================================*/

	// function to delete the reminder date
	$scope.deleteReminder = function(note) {

		console.log("it came inside delete reminder ")
		note.reminder = "";
		// here i am calling my update
		$scope.updateById(note);
		console.log("sucessfull update  note...");

	}
	/*================================================================================================================*/
	// This for image upload

	$scope.type = {};
	$scope.openHiddenButton = function(note) {
		console.log("welcome to open hidden function...");
		$('#image').trigger('click');
		$scope.type = note;
		console.log("helllo trigger after");
	}

	/*================================================================================================================*/
	
	$scope.stepsModel = [];
	$scope.imageUpload = function(note) {
		console.log("hello we r in imageUpload");
		var reader = new FileReader();
		console.log("note : " + note);
		reader.onload = $scope.imageLoader;
		reader.readAsDataURL(note.image);
		console.log("note.image : " + note.imageNote);
	}

	
	/*================================================================================================================*/
	
	$scope.imageLoader = function(image) {

		$scope.$apply(function() {
			$scope.stepsModel.push(image.target.result);
			var imageSrc = image.target.result;
			$scope.type.imageNote = imageSrc;
			$scope.updateById($scope.type);

		});
	}
	/*================================================================================================================*/
});