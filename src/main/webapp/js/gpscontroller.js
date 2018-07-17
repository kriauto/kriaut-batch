(function () {
    var as = angular.module('angularspring');
    
    as.controller('CarteController', function ($scope, $rootScope, $http, i18n, $location,$compile,$injector) {
    	$scope.profile = {};
    	$scope.search = {};
    	$scope.cars = {};
    	$scope.dates = {};
    	$scope.map ;
        $scope.markers = [];
        $scope.maPolyline ;
    	
    	var ajaxgetcars = function () {
           	$http.post('action/getCars/',true).then(function (response) {
                   $scope.cars = response.data;
               });
        };
        if($rootScope.loggedIn){
           	ajaxgetcars();
        }
    	
        $scope.createSearch = function () {
            var $newDiv = $('<div id="style-selector-control"  class="map-control"  ng-controller="CarteController"><table>'+
                    '<tr>'+
                    '<td colspan="2"><select id="style-selector" class="selector-control" ng-model="search.deviceid"    ng-options="car.deviceid as car.immatriculation for car in cars" ng-change="loadDates()"><option value="">Voiture</option></select></td>'+                                      
                    '<td colspan="2"><select id="style-selector" class="selector-control" ng-model="search.date"    ng-options="date.code as date.label for date in dates" ng-change="initmap()"><option value="">Date</option></select></td>'+  
                    '</tr>'+  
                    '<tr>'+  
                    '<td colspan="2"><img style="width:25px;height:25px;margin-top:3px;"  data-dismiss="modal" src="img/close.png" alt="Fermer"></img></td>'+                                        
                    '<td colspan="2"><img style="width:25px;height:25px;margin-top:3px;"  src="img/refresh.png" alt="Rafraichir" ng-click="initmap()"></img></td>'+    
                    '</tr>'+  
                    '</table></div>');
          
            $injector.invoke(function ($compile) {
                var div = $compile($newDiv);
                var content = div($scope);
                $(document.body).append(content);
            });
        };
          
         
        $scope.loadCars = function() {
        	$scope.createSearch();        	
        	var optionsCarte = {
    				  zoom: 5,
    				  center:new google.maps.LatLng(30.095410, -8.645772),
    				  fullscreenControl: true,
    				  gestureHandling: 'cooperative'
    			    }
    				map = new google.maps.Map( document.getElementById( "map_canvas" ), optionsCarte );
    				var styleControl = document.getElementById('style-selector-control');
    				map.controls[google.maps.ControlPosition.BOTTOM_CENTER].push(styleControl);
         };
    
        $scope.loadDates = function () {
          if($scope.markers){
           	 deleteMarkers();
          }
          if($scope.maPolyline){
           	 removeLine();
          }
          if(!angular.equals($scope.search.deviceid,null)){
            $http.post('action/getDates/',$scope.search.deviceid).then(function (response) {
            	if(Object.keys(response.data).length >= 1 ){
                  $scope.dates = response.data;
            	}
            });
          }else{
        	 $scope.dates = null;
          }
        };
          
        var loadcardata = function () {
          	$http.post('action/loadCarData/',$scope.search).then(function (response) {
              var count = Object.keys(response.data).length;
         	  setCarPolyline(map,response.data,count);
  			  setCarMarkers(map,response.data,count);
            });
        };
          
       var loadalldata = function () {          	
          	$http.post('action/loadAllData/',$scope.search).then(function (response) {
               var count = Object.keys(response.data).length;
    		   setAllMarkers(map,response.data,count);
            });
        };
          
        var setCarPolyline = function (map, data, count) {
             var tableauPointsPolyline = [];
             if(count > 0){
               for (var i=0; i<count; i++) {
            		tableauPointsPolyline[i] = new google.maps.LatLng(data[i].latitude, data[i].longitude);
               }
               $scope.maPolyline = new google.maps.Polyline({
    				path: tableauPointsPolyline,
    				strokeColor: '#DF013A',
    				strokeOpacity: 10,
    				strokeWeight: 1
    			});
            	$scope.maPolyline.setMap(map);
            	addLine();
              }
        };
            
        var setAllPolyline = function (map, data, count) {
        	  var tableauPointsPolyline = [];
              if(count > 0){
        	     for (var i=0; i<count; i++) {
        		    tableauPointsPolyline[i] = new google.maps.LatLng(data[i].latitude, data[i].longitude);
        	      }
        	      $scope.maPolyline = new google.maps.Polyline({
				     path: tableauPointsPolyline,
				     strokeColor: '#DF013A',
				     strokeOpacity: 10,
				     strokeWeight: 1
			      });
        	      $scope.maPolyline.setMap(map);
        	      addLine();	
        	  }
       };
          
       function setMapOnAll(map) {
          for (var i = 0; i < $scope.markers.length; i++) {
              $scope.markers[i].setMap(map);
          }
       }

       function clearMarkers() {
          setMapOnAll(null);
       }

       function showMarkers() {
          setMapOnAll(map);
       }

       function deleteMarkers() {
          clearMarkers();
          $scope.markers = [];
       }
          
       function addLine() {
          $scope.maPolyline.setMap(map);
       }
 
       function removeLine() {
          $scope.maPolyline.setMap(null);
       }
          
       var setCarMarkers = function(map, data, count) {
          for (var i = 0; i < count; i++) {
               var icon ;
               var label ;
               var myLatLng = new google.maps.LatLng(data[i].latitude, data[i].longitude);
               var infoWindow = new google.maps.InfoWindow();
               if(i == 0){
                	  icon = "img/green-dot.png";
                	  label = 'A';
               }else if(i == count-1){
                	  icon = "img/blue-dot.png";
                	  label = 'B';
               }else{
                	  icon = "img/red-dot.png";
                	  label = '';
               }
               var marker = new google.maps.Marker({
                      position: myLatLng,
                      map: map,
                      label : label
                      //animation: google.maps.Animation.DROP,
                      //icon: icon
                   });
                   (function(i) {
                      google.maps.event.addListener(marker, "click", function() {
                          infoWindow.close();
                          infoWindow.setContent("<div id='boxcontent' style='font-family:Calibri'><strong style='color:#000000'>Vitesse : </strong><strong style='color:#FF8000'>"+data[i].speed+" km/h</strong><br><strong style='color:#000000'>Date : </strong><strong style='color:#FF8000'>"+data[i].servertime+"</strong><br><strong style='color:#000000'>Marque : </strong><strong style='color:#FF8000'>"+data[i].mark+"&nbsp;"+data[i].model+"&nbsp;"+data[i].color+"</strong><br><strong style='color:#000000'>Immatriculation : </strong><strong style='color:#FF8000'>"+data[i].immatriculation+"</strong>");
                          infoWindow.open(map, this);
                      });
                   })(i);
                   $scope.markers.push(marker);
           }
           map.setZoom(13);
           if(count >= 2){
              if($scope.markers[Math.trunc(count/2)] != null)
      	      map.setCenter($scope.markers[Math.trunc(count/2)].getPosition());
           }else{
              if($scope.markers[0] != null)
              map.setCenter($scope.markers[0].getPosition());
           }
      };
          
      var setAllMarkers = function(map, data, count) {
           for (var i = 0; i < count; i++) {
              var myLatLng = new google.maps.LatLng(data[i].latitude, data[i].longitude);
              var infoWindow = new google.maps.InfoWindow();
              var marker = new google.maps.Marker({
                      position: myLatLng,
                      map: map,
                      //animation: google.maps.Animation.DROP,
                      //icon : "img/blue-dot.png"
              });
              
              (function(i) {
            	  var infowindow = new google.maps.InfoWindow({
                      content: "<div id='boxcontent' style='font-family:Calibri'><strong style='color:#000000'>Vitesse : </strong><strong style='color:#FF8000'>"+data[i].speed+" km/h</strong><br><strong style='color:#000000'>Date : </strong><strong style='color:#FF8000'>"+data[i].servertime+"</strong><br><strong style='color:#000000'>Marque : </strong><strong style='color:#FF8000'>"+data[i].mark+"&nbsp;"+data[i].model+"&nbsp;"+data[i].color+"</strong><br><strong style='color:#000000'>Immatriculation : </strong><strong style='color:#FF8000'>"+data[i].immatriculation+"</strong><br><strong style='color:#000000'>Adresse : </strong><strong style='color:#FF8000'>"+data[i].address+"</strong>",
                });
            	  infowindow.open(map, marker);
                  google.maps.event.addListener(marker, "click", function() {
                      infoWindow.close();
                      infoWindow.setContent("<div id='boxcontent' style='font-family:Calibri'><strong style='color:#000000'>Vitesse : </strong><strong style='color:#FF8000'>"+data[i].speed+" km/h</strong><br><strong style='color:#000000'>Date : </strong><strong style='color:#FF8000'>"+data[i].servertime+"</strong><br><strong style='color:#000000'>Marque : </strong><strong style='color:#FF8000'>"+data[i].mark+"&nbsp;"+data[i].model+"&nbsp;"+data[i].color+"</strong><br><strong style='color:#000000'>Immatriculation : </strong><strong style='color:#FF8000'>"+data[i].immatriculation+"</strong><br><strong style='color:#000000'>Adresse : </strong><strong style='color:#FF8000'>"+data[i].address+"</strong>");
                      infoWindow.open(map, this);
                  });
               })(i);
               $scope.markers.push(marker);
          }
          map.setZoom(13);
          if(count >= 2){
           	  if($scope.markers[Math.trunc(count/2)] != null)
     	      map.setCenter($scope.markers[Math.trunc(count/2)].getPosition());
          }else{
           	  if($scope.markers[0] != null)
           	  map.setCenter($scope.markers[0].getPosition());
          }
      };
          
      var DeleteMarkers = function () {
          for (var i = 0; i < markers.length; i++) {
            markers[i].setMap(null);
          }
          markers = [];
      };
          
      $scope.initmap =  function (){
     	 if($scope.markers){
       	   deleteMarkers();
       	 }
       	 if($scope.maPolyline){
       	   removeLine();
         }
         if($scope.search.deviceid && !angular.equals($scope.search.deviceid, 111111) && !angular.equals($scope.search.date, null)){
        	     loadcardata();
         }
         if($scope.search.deviceid &&  angular.equals($scope.search.deviceid, 111111) && !angular.equals($scope.search.date, null)){
         	     loadalldata();
         }
	  };
	  
    });
    
}());