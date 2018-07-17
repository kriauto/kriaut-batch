(function () {
    var as = angular.module('angularspring');

    as.controller('ContactController', function ($scope, $rootScope, $http, i18n, $location,$timeout,$q,$cookies) {
    	$scope.contacts = {};
    	
    	var load = function () {
            $http.get('action/getContacts/').then(function (response) {
            	$scope.contacts = response.data;
            });
        };
        load();
    });
}());