var app = angular.module('ToDo');
app.factory('homeService', function($http ) {
	var notes = {};
	notes.nodeCreate = function(note) {
		return $http({
			method : "post",
			url : "create",
			headers:{'jwt':localStorage.getItem()},
			data : note
		})
	}
	return notes;
});