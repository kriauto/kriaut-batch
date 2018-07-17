(function () {
    var as = angular.module('angularspring');
    
    as.controller('AccountController', function ($scope, $rootScope, $http, i18n, $location,$timeout,$q,$cookies) {
    	 
        $scope.profile = {};
        $scope.agency = {};
        $scope.iseditagency = false;
        $scope.iseditprofile = false;
    	
    	function ajaxgetprofile() {
            $http.get('action/getProfile/').then(function (response) {
            		$scope.profile = response.data ;
            });
        };
        ajaxgetprofile();
        
        function ajaxgetagency() {
            $http.get('action/getAgency/').then(function (response) {
            		$scope.agency = response.data ;
            });
        };
        ajaxgetagency();
                
        $scope.editagency = function () {
        	 $scope.iseditagency = true;
        };
        
        $scope.editprofile = function () {
       	     $scope.iseditprofile = true;
        };
        
        function ajaxsaveprofile() {
            var deferred = $q.defer();
            $http.post('action/updateProfile/', $scope.profile).then(function (response) {
            	if(angular.equals(response.data.type, 'success')){
            		deferred.resolve(response);
                 }else{
            	    deferred.reject(response);
                 }
            });
            return deferred.promise;
        }
        
        $scope.saveprofile = function () {
        	ajaxsaveprofile()
              .then(function(data) {
            	   ajaxgetprofile();
             	   $scope.iseditprofile = false;
  		        }, function(data) {
  		        }
  		    );
        };
        
        function ajaxsaveagency() {
           var deferred = $q.defer();
           $http.post('action/updateAgency/', $scope.agency).then(function (response) {
           	if(angular.equals(response.data.type, 'success')){
           		deferred.resolve(response);
                }else{
           	    deferred.reject(response);
                }
           });
           return deferred.promise;
       }
       
       $scope.saveagency = function () {
    	   ajaxsaveagency()
             .then(function(data) {
            	   ajaxgetagency();
            	   $scope.iseditagency = false;
 		        }, function(data) {
 		        }
 		    );
       };
    });
    
}());