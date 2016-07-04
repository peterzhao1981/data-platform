function newUserCtrl($scope, $http) {

  $scope.type = 1;
  $scope.datepicker = 0;
  //var start = $scope.start;
  $scope.search = function() {
    //
    $scope.start = $scope.start.replace(/\s+/g, "");
    var textStartDate = $scope.start.substring(0, 4) + "/" + $scope.start.substring(4, 6) + "/" + $scope.start.substring(6, 8);
    startIntDate = parseInt($scope.start);

    $scope.end = $scope.end.replace(/\s+/g, "");
    var textEndDate = $scope.end.substring(0, 4) + "/" + $scope.end.substring(4, 6) + "/" + $scope.end.substring(6, 8);
    endDate = parseInt($scope.end);

    if (startIntDate > endDate) {
      alert("star date must smaller than end ndate")
      return;
    };
    var type = parseInt($scope.type);
    if (type == 5) {
      startIntDate = Math.floor(startIntDate / 100);
      endDate = Math.floor(endDate / 100);
    };
    $scope.getUserNew(startIntDate, endDate, type);
    document.getElementById("strSearchDate").innerHTML = textStartDate + "-" + textEndDate;
  }

  $scope.initSearch = function() {
    $scope.searchLastDay(14);
  }

  $scope.searchLastDay = function(count) {
    // init search date the last day to last 15 day
    var myDate = new Date();

    var startDate = new Date(myDate.getTime() - count * 24 * 3600 * 1000);

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
    var textStartDate = startDate.getFullYear().toString() + "/" + startMonthStr + "/" + startDayStr;

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
    dateStr = myDate.getFullYear().toString() + monthStr + dayStr;
    var textEndDate = myDate.getFullYear().toString() + "/" + monthStr + "/" + dayStr;

    endDate = parseInt(dateStr);
    document.getElementById("strSearchDate").innerHTML = textStartDate + "-" + textEndDate;

    if ($scope.type == 5) {
      startIntDate = Math.floor(startIntDate / 100);
      endDate = Math.floor(endDate / 100);
    };

    $scope.getUserNew(startIntDate, endDate, $scope.type);
  }

  $scope.getRadioValue = function() {
      if ($scope.datepicker == 5) {
        $scope.search();
      };
  }

  $scope.setType = function(type) {
    $scope.type = type;

    if ($scope.datepicker == 1) {
      $scope.searchLastDay(7);
    } else if ($scope.datepicker == 2) {
      $scope.searchLastDay(30);
    } else if ($scope.datepicker == 3) {
      $scope.searchLastDay(90);
    } else if ($scope.datepicker == 4) {
      $scope.searchLastDay(365);
    } else if ($scope.datepicker == 5) {
      $scope.search();
    } else if ($scope.datepicker == 0) {
      $scope.searchLastDay(14);
    }
  }


  $scope.getUserNew = function(startDate, endDate, type) {
    var req = {
      method: "GET",
      url: API_URI_ENDPOINT + "/stats?startDate=" + startDate + "&endDate=" + endDate + "&type=" + type + "&query=user",
    }
    var space = type;
    $http(req)
      .success(function(response) {
        if (response.code == undefined) {
          $scope.data = response;

          google.charts.setOnLoadCallback(drawLineChart(space));

          google.charts.setOnLoadCallback(drawTable);
          var totalUser = totalUserFb = totalUserYt = totalUserIns= 0;
          for (var i = $scope.data.length - 1; i >= 0; i--) {
            totalUser += $scope.data[i].newUser;
            // totalUserFb += $scope.data[i].newUserFb;
            // totalUserYt += $scope.data[i].newUserYt;
            // totalUserIns += $scope.data[i].newUserIns;
          };
          var avg = (totalUser / $scope.data.length).toFixed(2);
          document.getElementById("avg_active").innerHTML = avg;
          document.getElementById("active").innerHTML = totalUser;
        } else {
          document.getElementById("chart_div1").innerHTML = "get data error";
          document.getElementById("table_div").innerHTML = "get data error";
        }
        
      })
  }

  function drawLineChart(space) {
    var length = $scope.data.length;
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Month');
    data.addColumn('number', 'newUser');
    data.addColumn('number', 'newUserFb');
    data.addColumn('number', 'newUserYt');
    data.addColumn('number', 'newUserIns');
    // for (var i = 0; i < length; i++) {
    //     if (i % 2 == 0) {
    //       data.addRows([
    //         [intDateToString($scope.newUserData[i].date), $scope.newUserData[i].newUser, $scope.newUserData[i].newUserFb,$scope.newUserData[i].newUserYt,$scope.newUserData[i].newUserIns]
    //       ]);
    //     } 
    //     else {
    //       data.addRows([
    //         ["", $scope.newUserData[i].newUser, $scope.newUserData[i].newUserFb,$scope.newUserData[i].newUserYt,$scope.newUserData[i].newUserIns]
    //       ]);
    //     }
    //   }

    // var data = google.visualization.arrayToDataTable([
    //   ['Date', 'new user','new userFb', 'new userYt','new userIns'],
    //   [intDateToString($scope.data[0].date), $scope.data[0].newUser, $scope.data[0].newUserFb,$scope.data[0].newUserYt,$scope.data[0].newUserIns]
    // ]);
    if (space == 1) {
      for (var i = 0; i < length; i++) {
        if (i % 2 == 0) {
          data.addRows([
            [intDateToString($scope.data[i].date), $scope.data[i].newUser, $scope.data[i].newUserFb,$scope.data[i].newUserYt,$scope.data[i].newUserIns]
          ]);
        } else {
          data.addRows([
            ["", $scope.data[i].newUser, $scope.data[i].newUserFb,$scope.data[i].newUserYt,$scope.data[i].newUserIns]
          ]);
        }
      }
    } else {
      for (var i = 0; i < length; i++) {
        data.addRows([
          [intDateToString($scope.data[i].date), $scope.data[i].newUser, $scope.data[i].newUserFb,$scope.data[i].newUserYt,$scope.data[i].newUserIns]
        ]);
      }
    }
    var options = {
      height: 300,
      //title : '                 User Active                        ',
      vAxis: {
        title: 'Number of New Users'
      },
      hAxis: {
        title: 'Date'
      },
      interpolateNulls : 'true',
    };
    var chart = new google.visualization.LineChart(document.getElementById('chart_div1'));
    chart.draw(data, options);
  }
  $scope.initSearch();



  //google.charts.load('current', {'packages':['table']});
  function drawTable() {
    var cssClassNames = {
      'tableCell': 'center-text',
      'headerCell': 'center-text'
    };
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Date');
    data.addColumn('number', 'New User');
    data.addColumn('number', 'New UserFb');
    data.addColumn('number', 'New UserYt');
    data.addColumn('number', 'New UserIns');

    data.addRows([
      [intDateToString($scope.data[0].date), $scope.data[0].newUser,$scope.data[0].newUserFb,$scope.data[0].newUserYt,$scope.data[0].newUserIns],
    ]);
    var length = $scope.data.length;
    for (var i = 1; i < length; i++) {
      data.addRows([
        [intDateToString($scope.data[i].date), $scope.data[i].newUser, $scope.data[i].newUserFb,$scope.data[i].newUserYt,$scope.data[i].newUserIns]
      ]);
    }

    var table = new google.visualization.Table(document.getElementById('table_div'));

    table.draw(data, {
      showRowNumber: false,
      width: '100%',
      height: '100%',
      'cssClassNames': cssClassNames,
      interpolateNulls : 'true',});
  }


  $scope.jsonToCSV = function() {
    var length = $scope.data.length;
      var data = [];
      for (var i = 0; i < length; i++) {
        var json = {};
        json.date = $scope.data[i].date;
        json.newUser = $scope.data[i].newUser;
        json.newUserFb = $scope.data[i].newUserFb;
        json.newUserYt = $scope.data[i].newUserYt;
        json.newUserIns = $scope.data[i].newUserIns;
        data.push(json);
      };
    JSONToCSVConvertor(data, "Report", true);
  }
  

  var watch = $scope.$watch('datepicker', function(newValue, oldValue, scope) {
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
  .controller('newUserCtrl', newUserCtrl);