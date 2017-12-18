var app=angular.module('ToDo');
app.directive('sideBar', function() {
    return {
        templateUrl : "template/sidebar.html"
    };
});