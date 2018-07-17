//Define a function scope, variables used inside it will NOT be globally visible.
(function () {

    var
    //the HTTP headers to be used by all requests
        httpHeaders,

    //the message to be shown to the user
        message,

    //Define the main module.
    //The module is accessible everywhere using "angular.module('angularspring')", therefore global variables can be avoided totally.
        as = angular.module('angularspring', ['ngRoute', 'ngCookies']); 

    as.config(function ($routeProvider, $httpProvider, $qProvider) {
        //configure the rounting of ng-view
        $routeProvider.when('/login', { controller: 'LoginController', templateUrl: 'html/login.html'})
                      .when('/geolocation', { controller: 'CarteController', templateUrl: 'html/carte.html'})
                      .when('/aboutus', { controller: 'AboutUsController', templateUrl: 'html/aboutus.html'})
                      .when('/contactus', { controller: 'ContactController', templateUrl: 'html/contactus.html'})
                      .when('/myaccount', { controller: 'AccountController', templateUrl: 'html/myaccount.html'})
                      .when('/logout', { controller: 'LogOutController', templateUrl: 'html/logout.html'})
                      .when('/initPassword', { controller: 'PasswordController', templateUrl: 'html/password.html'})
                      .when('/dashboard', { controller: 'DashboardController', templateUrl: 'html/dashboard.html'})
                      .when('/parametres', { controller: 'ParametresController', templateUrl: 'html/parametres.html'})
                      .otherwise({redirectTo: '/aboutus'});
        
        $qProvider.errorOnUnhandledRejections(false);
        
        $httpProvider.interceptors.push(function($q) {
        	 var setMessage = function (response) {
                //if the response has a text and a type property, it is a message to be shown
                if (response.data.type && response.data.code) {
                    message = {
                        type: response.data.type,
                        code: response.data.code,
                        show: true
                    };
                }else{
                	message = {
                        type: null,
                        code: null,
                        show: false
                    };
                }
            };
            
        	return {
        	   'response': function(response) {
        	       // same as above
        		   setMessage(response);
        		   return response;
        	    },

        	    'responseError': function(response) {
        	    	setMessage(response);
        	    	return $q.reject(response);
        	    }
            };
       });

        httpHeaders = $httpProvider.defaults.headers;
        if (!$httpProvider.defaults.headers.common) {
            $httpProvider.defaults.headers.common = {};
        }
        $httpProvider.defaults.headers.common["Cache-Control"] = "no-cache";
        $httpProvider.defaults.headers.common.Pragma = "no-cache";
        $httpProvider.defaults.headers.common["If-Modified-Since"] = "0";
    });
    
    
    as.run(function ($rootScope, $location, $cookies, $http) {
        //make current message accessible to root scope and therefore all scopes
        $rootScope.message = function () {
            return message;
        };
        
        // keep user logged in after page refresh
        $rootScope.credential = $cookies.getObject('credential') || {};
        if ($rootScope.credential.token) {
            $http.defaults.headers.common['Authorization'] = 'Basic' + $rootScope.credential.token;
        };
        
        $rootScope.loggedIn = false;
        
        if($rootScope.credential.token){
        	 $rootScope.loggedIn = true;
        }else{
        	 $rootScope.loggedIn = false;
        }
 
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in and trying to access a restricted page
            var restrictedPage = $.inArray($location.path(), ['/login','/aboutus','/initPassword','/contactus']) === -1;
            var loggedIn = $rootScope.credential.token;
            if (restrictedPage && !loggedIn) {
                $location.path('/login');
            }
        });
        
    });
    
}());