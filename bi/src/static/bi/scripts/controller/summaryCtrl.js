function summaryCtrl($scope, $http) {

	$scope.initSearch = function() {
	 	
		var myDate = new Date();
    	var date = new Date(myDate.getTime() - 1 * 24 * 3600 * 1000);
    	var month = date.getMonth() + 1;
	    if (month < 10) {
	      var month = "0" + month.toString();
	    } else {
	      month = startMonth.toString();
	    }
	    var day = date.getDate();
	    if (day < 10) {
	      var dayStr = "0" + day.toString();
	    } else {
	      dayStr = day.toString();
	    };
	    dateStr = date.getFullYear().toString() + month + dayStr;
	    var textStartDate = date.getFullYear().toString() + "/" + month + "/" + dayStr;
	    intDate = parseInt(dateStr);

	 	var myDate = new Date(myDate.getTime() - 15 * 24 * 3600 * 1000);

	 	var month = myDate.getMonth() + 1;
	    if (month < 10) {
	      var monthStr = "0" + month.toString();
	    } else {
	      monthStr = month.toString();
	    }
	    var day = myDate.getDate();
	    if (day < 10) {
	      var dayStr = "0" + day.toString();
	    } else {
	      dayStr = day.toString();
	    }
	    dateStr = myDate.getFullYear().toString() + monthStr + dayStr;
	    var textEndDate = myDate.getFullYear().toString() + "/" + monthStr + "/" + dayStr;

	    startDate = parseInt(dateStr);

	    $scope.getNewUserInfo(startDate, intDate, 1);
	    //$scope.getActiveInfo(startDate, intDate, 1);
	}

	$scope.getNewUserInfo = function(startDate, endDate, type) {
		var req = {
	      method: "GET",
	      url: API_URI_ENDPOINT + "/stats?startDate=" + startDate + "&endDate=" + endDate + "&type=" + type + "&query=user",
	    }
	    $http(req)
	    .success(function(response){
	    	if (response.code == undefined) {
				$scope.newUserData = response;
	    		google.charts.setOnLoadCallback(drawNewUser());
	    		google.setOnLoadCallback(drawActiveUser());
	    		var length = $scope.newUserData.length;
	    		var total = $scope.newUserData[length-1].totalUser;
	    		var activeUser = $scope.newUserData[length-1].activeUser;
	    		var newUser = $scope.newUserData[length-1].newUser;
	    		var order = $scope.newUserData[length-1].order;
	    		document.getElementById("totalUser").innerHTML = total;
	    		document.getElementById("activeUser").innerHTML = activeUser;
	    		document.getElementById("newUser").innerHTML = newUser;
	    		document.getElementById("order").innerHTML = order;
			} else {
				document.getElementById("chart_new").innerHTML = "get data error";
			}
	    })
	}
	
      function drawActiveUser() {
		var length = $scope.newUserData.length;
	 	var data = google.visualization.arrayToDataTable([
		 ['Month', 'Active User'],
		 [intDateToString($scope.newUserData[0].date),$scope.newUserData[0].activeUser]
		]);
      	for (var i = 1; i < length; i++) {
	        if (i % 2 == 0) {
	          data.addRows([
	            [intDateToString($scope.newUserData[i].date), $scope.newUserData[i].activeUser]
	          ]);
	        } else {
	          data.addRows([
	            ["", $scope.newUserData[i].activeUser]
	          ]);
	        }
      	}
	     var options = {
			height : "300",
			//title : 'User Active',
			legend: { position: 'none' },
			vAxis: {
		        title: 'Number of Active Users'
		    },
		    hAxis: {
		        title: 'Date'
		    },
		    interpolateNulls : 'true',
		};
        var chart = new google.visualization.ComboChart(document.getElementById('chart_active'));
        chart.draw(data, options);
	}



	function drawNewUser() {
		var length = $scope.newUserData.length;
	 
	 	var data = google.visualization.arrayToDataTable([
		 ['Month', 'newUser', 'newUserFb', 'newUserYt', 'newUserIns'],
		 [intDateToString($scope.newUserData[0].date),$scope.newUserData[0].newUser,$scope.newUserData[0].newUserFb,$scope.newUserData[0].newUserYt,$scope.newUserData[0].newUserIns]
		]);
      	for (var i = 1; i < length; i++) {
	        if (i % 2 == 0) {
	          data.addRows([
	            [intDateToString($scope.newUserData[i].date), $scope.newUserData[i].newUser, $scope.newUserData[i].newUserFb,$scope.newUserData[i].newUserYt,$scope.newUserData[i].newUserIns]
	          ]);
	        } else {
	          data.addRows([
	            ["", $scope.newUserData[i].newUser, $scope.newUserData[i].newUserFb,$scope.newUserData[i].newUserYt,$scope.newUserData[i].newUserIns]
	          ]);
	        }
      	}
	    var total = $scope.newUserData[length-1].totalUser;
	    var options = {
			height : "300",
			legend: { position: 'right' },
			vAxis: {
		        title: 'Number of New Users'
		    },
		    hAxis: {
		        title: 'Date'
		    },
		    interpolateNulls : 'true',
		};
        var chart = new google.visualization.LineChart(document.getElementById('chart_new'));
        chart.draw(data, options);
      }

  	$scope.initSearch();

  	$scope.jsonToExcelNewUser = function() {
  		var length = $scope.newUserData.length;
  		var data = [];
  		for (var i = 0; i < length; i++) {
  			var json = {};
  			json.date = $scope.newUserData[i].date;
  			json.newUser = $scope.newUserData[i].newUser;
  			json.newUserFb = $scope.newUserData[i].newUserFb;
  			json.newUserYt = $scope.newUserData[i].newUserYt;
  			json.newUserIns = $scope.newUserData[i].newUserIns;
  			data.push(json);
  		};
	    JSONToCSVConvertor(data, "newUser", true);
	    //JSONToExcelConvertor(date, "newUser", $scope.title);
  	}

  	$scope.jsonToExcelActiveUser = function() {
  		var length = $scope.newUserData.length;
  		var data = [];
  		for (var i = 0; i < length; i++) {
  			var json = {};
  			json.date = $scope.newUserData[i].date;
  			json.activeUser = $scope.newUserData[i].activeUser;
  			data.push(json);
  		};
  
  		JSONToCSVConvertor(data, "activeUser", true);
  	}


  	$scope.getActiveUsersCountry = function() {
	      	var req = {
	      		method : "GET",
	      		url : API_URI_ENDPOINT + "/stats?query=country"
	      	}
	      	$http(req)
	      	.success(function(response) {
	      		$scope.countryData = response;
	      		google.charts.setOnLoadCallback(drawRegionsMap());
	      	})
	    }

      function drawRegionsMap() {
        var data = google.visualization.arrayToDataTable([
          ['Country', 'Popularity'],
          [$scope.countryData[0].country.toString(),$scope.countryData[0].total]
        ]);
        for (var i = 1; i < $scope.countryData.length; i++) {
        	//var array = [$scope.countryData[i].country,$scope.countryData[i].total];
        	data.addRows([[$scope.countryData[i].country.toString(),$scope.countryData[i].total]]);
        };
        //console.log(data)
        var options = {
        	height:300,
        	interpolateNulls : 'true',
        };
        var chart = new google.visualization.GeoChart(document.getElementById('chart_div2'));
        chart.draw(data, options);
      }
    	$scope.getActiveUsersCountry();
   $scope.jsonToExcelCountry = function() {
    JSONToCSVConvertor($scope.countryData, "newUserCountry", true);
  }
}


angular
    .module('modeApp')
    .controller('summaryCtrl', summaryCtrl)