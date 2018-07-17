(function () {
    var as = angular.module('angularspring');
    
    as.controller('LoginController', function ($scope, $rootScope, $http, i18n, $location,$timeout,$q,$cookies) {

    	$scope.profile ={};
    	
        function ajaxLogin() {
            var deferred = $q.defer();
            $http.post('action/login/', $scope.profile).then(function (response) {
            	if(!angular.equals(response.data.id, null)){
            		createcookie(response.data.token);
            		$rootScope.loggedIn = true;
            		deferred.resolve(response);
                }else{
                	deferred.reject(response);
                }
            });
            return deferred.promise;
        }
        
        function createcookie(token) {
            $rootScope.credential = {token:token};
 
            // set default auth header for http requests
            $http.defaults.headers.common['Authorization'] ='Basic'+token;
 
            // store user details in globals cookie that keeps user logged in for 1 week (or until they logout)
            var cookieExp365 = new Date();
            var cookieExp30 = new Date(cookieExp365);
            if($scope.profile.remember){
            	cookieExp365.setDate(cookieExp365.getDate() + 365);
                $cookies.putObject('credential', $rootScope.credential, { expires: cookieExp365 });
            }else{
            	cookieExp30.setMinutes( cookieExp365.getMinutes() + 30);
                $cookies.putObject('credential', $rootScope.credential, { expires: cookieExp30 });
            }
        }
        
        $scope.login = function () {
        	ajaxLogin().then(function(data) {
            	  $rootScope.loggedIn = true;
  		           $location.url('/myaccount');
  		        }, function(data) {
  		        }
  		    );
        }
    });
    
    as.controller('LogOutController', function ($scope, $rootScope, $http, i18n, $location,$timeout,$q,$cookies) {
    	var ajaxlogout = function () {
            $http.get('action/logout/').then(function (response) {
            	if(angular.equals(response.data.type, 'success')){
            		removecookie();
            		$rootScope.loggedIn = false;
            		$location.url('/login');
                 }
            });
            
        };
        ajaxlogout();
        
        function removecookie() {
            $rootScope.globals = {};
            $cookies.remove('credential');
            $http.defaults.headers.common.Authorization = 'Basic';
        };
    });
    
    
    as.controller('PasswordController', function ($scope, $rootScope, $http, i18n, $location,$timeout,$q,$cookies) {
        $scope.profile = {};
        
        $scope.isinitpassword = true ;
        
        $scope.ajaxinitpassword = function () {
        	alert("pwd");
            $http.post('action/initPassword/', $scope.profile).then(function (response) {
            	if(angular.equals(response.data.type, 'success')){
            		 $scope.isinitpassword = false ;
                 }
            });
        };
    });
}());