angular.module('tb-color-picker', [])
    .run(['$templateCache', function($templateCache) {
        $templateCache.put('ColorePicker.html', '<div class="color-picker">'+
            '<div ><md-icon ng-click="color()" aria-label="Open some menu" md-svg-icon="image/colore.svg"></md-icon></div>' +
            '<div class="color-palette">'+
                '<div ng-repeat="option in vm.options"'+
                'ng-style="{\'background-color\': option}"'+
                'ng-class="{\'palette-selected-color\': option == vm.color, \'transparent-color\': option == \'transparent\'}"'+
                'ng-click="vm.changeColor(option)"></div>'+
            '</div>'+
        '</div>');
    }])
    .directive('colorPicker', function () {
        return {
            restrict: 'E',
            templateUrl: 'ColorePicker.html',
            replace: true,
            controller: colorPickerDirectiveController,
            controllerAs: 'vm',
            bindToController: {
                color: '=',
                options: '=',
                colorChanged: '&'
            }
        };

        function colorPickerDirectiveController($scope) {
            var vm = this;
            vm.changeColor = function (option) {
                if(vm.color1 != option) {
                    var old = vm.color1;
                    vm.color1 = option;
                    $scope.colorChanged({newColor: option, oldColor: old});
                }
            }

        }
    });