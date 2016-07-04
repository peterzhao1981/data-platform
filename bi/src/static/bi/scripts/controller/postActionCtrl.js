function postActionCtrl($scope, $http) {

	$scope.type = 1;
	$scope.datepicker = 0;
	 //var start = $scope.start;
	 $scope.search = function() {
	 	
	 	$scope.start = $scope.start.replace(/\s+/g,"");
	 	var textStartDate = $scope.start.substring(0,4) + "/" + $scope.start.substring(4,6) + "/" +$scope.start.substring(6,8);
	 	startIntDate = parseInt($scope.start);

	 	$scope.end = $scope.end.replace(/\s+/g,"");
	 	var textEndDate = $scope.end.substring(0,4) + "/" + $scope.end.substring(4,6) + "/" +$scope.end.substring(6,8);
	 	endDate = parseInt($scope.end);


	 	if (startIntDate > endDate) {
	 		alert("star date must smaller than end ndate")
	 		return;
	 	};
	 	
	 	$scope.getOrderCount(startIntDate, endDate);
	 	document.getElementById("strSearchDate").innerHTML= textStartDate + "-" + textEndDate;
	 }

	 $scope.initSearch = function () {
	 	$scope.searchLastDay(14);
	 }

	 $scope.getRadioValue = function() {
      	if ($scope.datepicker == 5) {
        	$scope.search();
      	};
  	}

	 $scope.searchLastDay = function( count ) {
	 	// init search date the last day to last 15 day
	 	var myDate = new Date();
	 	//endDate = myDate.getTime();
	 	//return;

	 	var startDate = new Date(myDate.getTime() - count * 24 * 3600 * 1000);
	 	//startIntDate = startDate.getTime();
	 	
	 	var startMonth = startDate.getMonth() + 1;

	 	if (startMonth < 10) {
	 		var startMonthStr = "0" + startMonth.toString();
	 	} else {
	 		startMonthStr = startMonth.toString();
	 	}
	 	var startDay = startDate.getDate();
	 	if (startDay < 10) {
	 		var startDayStr = "0" + startDay.toString();
	 	} else {
	 		startDayStr = startDay.toString();
	 	};

	 	startDateStr = startDate.getFullYear().toString() + startMonthStr + startDayStr;
	 	var textStartDate = startDate.getFullYear().toString() + "/" + startMonthStr + "/" +startDayStr;

	 	startIntDate = parseInt(startDateStr);


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
	 	dateStr = myDate.getFullYear().toString()+monthStr+dayStr;
	 	var textEndDate = myDate.getFullYear().toString() + "/" + monthStr + "/" +dayStr;

	 	endDate = parseInt(dateStr);
	 	document.getElementById("strSearchDate").innerHTML= textStartDate + "-" + textEndDate;

	 	$scope.getOrderCount(startIntDate, endDate);
	}
	 

	$scope.getOrderCount = function(startDate, endDate) {
		var req = {
			method:"GET",
			url : API_URI_ENDPOINT + "/stats?startDate=" + startDate + "&endDate=" + endDate + "&query=post",
		}
		$http(req)
		.success(function(response){
			//$scope.realTimedata = response;
			if (response.code == undefined) {
				$scope.data = response;
				var length = $scope.data.length;
				var totalViews = totalFavors = totalComments = totalShares = 0; 
				for (var i = length - 1; i >= 0; i--) {
					totalViews += $scope.data[i].views;
					totalFavors += $scope.data[i].favors;
					totalComments += $scope.data[i].comments;
					totalShares += $scope.data[i].shares;
				};
				document.getElementById("view").innerHTML = (totalViews/length).toFixed(2);
				document.getElementById("favor").innerHTML = (totalFavors/length).toFixed(2);
				document.getElementById("comment").innerHTML = (totalComments/length).toFixed(2);
				document.getElementById("share").innerHTML = (totalShares/length).toFixed(2);
				
				google.charts.setOnLoadCallback(drawTable);
			} else if (response.code == 23) {
				document.getElementById("table_div").innerHTML = "No More Data";
				document.getElementById("view").innerHTML = 0;
				document.getElementById("favor").innerHTML = 0;
				document.getElementById("comment").innerHTML = 0;
				document.getElementById("share").innerHTML = 0;
			}
		})
	}
      $scope.initSearch();
      
      //google.charts.load('current', {'packages':['table']});
      function drawTable() {

      	var cssClassNames = {
	    'tableCell': 'center-text',
		'headerCell': 'center-text'
		};

        var data = new google.visualization.DataTable();
        data.addColumn('string', 'title');
        data.addColumn('number', 'views');
        data.addColumn('number', 'favors');
        data.addColumn('number', 'shares');
        data.addColumn('number', 'comments');
        data.addRows([
          [$scope.data[0].title,$scope.data[0].views,$scope.data[0].favors,$scope.data[0].shares,$scope.data[0].comments],
        ]);
        var length = $scope.data.length;
        for(var i = 1;i < length; i++) {
      		data.addRows([[$scope.data[i].title,$scope.data[i].views,$scope.data[i].favors,$scope.data[i].shares,$scope.data[i].comments]]);
	    }
	    
        var table = new google.visualization.Table(document.getElementById('table_div'));

        table.draw(data, {showRowNumber: false, width: '100%', height: '100%',pageSize:'20',pagingButtons:'5','cssClassNames': cssClassNames,interpolateNulls : 'true',});
      }
          

      $scope.jsonToCSV = function() {
  		
		JSONToCSVConvertor($scope.data, "Post Data", true);
      }

	  var watch = $scope.$watch('datepicker',function(newValue,oldValue, scope){
	        if (newValue == 5) {
	        	document.getElementById("datepicker").style.display = 'block';
	        } else if (newValue == 1) {
	        	$scope.searchLastDay(7);
	        	document.getElementById("datepicker").style.display = 'none';
	        } else if (newValue == 2) {
	        	$scope.searchLastDay(30);
	        	document.getElementById("datepicker").style.display = 'none';
	        } else if (newValue == 3) {
	        	$scope.searchLastDay(90);
	        	document.getElementById("datepicker").style.display = 'none';
	        } else if (newValue == 4) {
	        	$scope.searchLastDay(365);
	        	document.getElementById("datepicker").style.display = 'none';
	        } 
		});
}




angular
    .module('modeApp')
    .controller('postActionCtrl', postActionCtrl);