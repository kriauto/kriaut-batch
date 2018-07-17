(function () {
    var as = angular.module('angularspring');
    
    as.controller('ParametresController', function ($scope, $rootScope, $http, i18n, $location,$timeout,$q,$cookies) {
    	$scope.car = {};
    	$scope.cars = {};
    	$scope.geofence = {};
    	 
    	 var ajaxgetcars = function () {
         	$http.post('action/getCars/',false).then(function (response) {
                 $scope.cars = response.data;
             });
         };
         ajaxgetcars();
         
         $scope.ajaxstopcar = function (deviceid) {
         	   $http.post('action/stopCar/',deviceid).then(function (response) {
         		  $timeout(function() {ajaxgetcars()}, 5000);
               });
          };
           
          $scope.ajaxstartcar = function (deviceid) {
               $http.post('action/startCar/',deviceid).then(function (response) {
            	   $timeout(function() {ajaxgetcars()}, 5000);
               });
          };
           
          $scope.ajaxgeofencecar = function () {
          	 if(angular.equals($scope.car.deviceid5,"")){
          		$scope.geofence = null;
          	 }else{
            	$http.post('action/getCarByDevice/',$scope.car.deviceid5).then(function (response) {
                $scope.geofence = response.data;
             });
          	}
          };
          
          $scope.ajaxupdatecar = function () {
           	 if(angular.equals($scope.geofence.id,"")){
           		$scope.car = null;
           	 }else{
             	$http.post('action/updateCar/',$scope.geofence).then(function (response) {
              });
           	}
          };
    });
}());