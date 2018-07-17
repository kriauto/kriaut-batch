(function () {
    var as = angular.module('angularspring');
    
    as.controller('DashboardController', function ($scope, $rootScope, $http, i18n, $location,$timeout,$q,$cookies) {
    	 $scope.car = {}; 
    	 $scope.cars = {};
    	 $scope.totalcours = {};
    	 $scope.maxspeed = {};
    	 $scope.consumption = {};
    	 $scope.notification = {};
    	 
    	 var ajaxgetcars = function () {
         	$http.post('action/getCars/',false).then(function (response) {
                 $scope.cars = response.data;
             });
         };
         ajaxgetcars();
         
         $scope.ajaxtotalcourse = function () {
        	 if(angular.equals($scope.car.deviceid1,"")){
        		  $scope.totalcours = null;
        	 }else{
          	    $http.post('action/getTotalCours/',$scope.car.deviceid1).then(function (response) {
          		  $scope.totalcours = response.data;
                });
        	 }
          };
          
          $scope.ajaxmaxspeed = function () {
        	 if(angular.equals($scope.car.deviceid2,"")){
        		  $scope.maxspeed = null;
        	 }else{
            	$http.post('action/getMaxSpeed/',$scope.car.deviceid2).then(function (response) {
            		$scope.maxspeed = response.data;
                });
        	 }
          };
          
          $scope.ajaxtotalconsumption = function () {
        	 if(angular.equals($scope.car.deviceid3,"")){
        		  $scope.consumption = null;
        	 }else{
          	      $http.post('action/getConsumption/',$scope.car.deviceid3).then(function (response) {
          		    $scope.consumption = response.data;
                  });
        	 }
          };
          
          $scope.ajaxnotification = function () {
         	 if(angular.equals($scope.car.deviceid4,"")){
         		  $scope.notification = null;
         	 }else{
           	      $http.post('action/getNotification/',$scope.car.deviceid4).then(function (response) {
           		    $scope.notification = response.data;
                   });
         	 }
           };
    });
}());