/**
 * @author ThakurGulab
 * @Decription home controller
 * 
 */
// this this my home controller ....
var app = angular.module('ToDo');
app.controller('homepageCrt', function($scope, homeService, $location, $state,
		$window, $mdToast, $document, mdcDateTimeDialog) {
   // this is function  for create note
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
	// get all note with help this function  ....
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
		}, function(response) {
			var rep = response.data.meResponse;
			console.log(rep);
		});
	}
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
	// note will be update by id.... 
	$scope.updateById = function(note) {
		var url = "update/" + note.id;
		var method = "put";
		var token = localStorage.getItem('jwt');
		var data = note;
		console.log(note);
		var nodes = homeService.getAllnode(url, method, token, data);
		nodes.then(function(response) {
			console.log("update the data sucessfull by id ");
		}, function(response) {
			console.log(response.data.meResponse);
		});
	}
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

	// trashFunction....
	$scope.trashFunction = function() {
		$state.go('TrashLoad');
		console.log("come inside sucessfull")
	}
	// call from notes..
	$scope.notesFunction = function() {
		$state.go('homepage');
		console.log("sucessfull come..");
	}

	// archiveFunction...
	$scope.archiveFunction = function() {
		$state.go('ArchiveLoad');
		console.log("sucessfull come..");
	}

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

	// for reminder function......

	$scope.reminderFunction = function(note, reminder) {
		// console.log("Inside Remainder..........");
		console.log(note);
		var date = new Date(reminder);
		var dates = $filter('date')(new Date(date), 'MMM dd yyyy HH:mm');
		// var parseDate = Date.parse();
		note.reminder = dates;
		var updateReminderTime = notesService.updateNotes(note);

		updateReminderTime.then(function(response) {
			getNotes();
		})
		console.log("date------>" + note.reminderDate);
	}

	// function to delete the reminder date
	$scope.deleteReminder = function(note) {
		
		console.log("it came inside delete reminder ")
		note.reminder = "";
		
		// here i am calling my update  
		$scope.updateById(note);
		console.log("sucessfull update  note...");
	
	}
	
	// function to create reminder 
	$scope.createFunction =function(note){
		$scope.first=false;
		console.log("it will come inside reminder create function");
		console.log(note.reminder);
	}

});