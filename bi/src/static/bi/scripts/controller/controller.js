/**
* MainCtrl
*/
function MainCtrl($scope) {
    $scope.logout = function () {
        $cookies.remove("X-Access-Token");
        $state.go("login");
    };
}
/**
 * LoginCtrl login controller
 */
function LoginCtrl($scope, $cookies, $state, apiService) {
    // Initialize and set default variables
    $scope.remember = true;

    $scope.doLogin = function () {
        // Create a request object for login
        var req = {
            method: "POST",
            url: API_URI_ENDPOINT + "/login",
            headers: {
                "login": $scope.login,
                "password": sha256_digest($scope.password)
            }
        };

        // Authenticate user and get an access token
        apiService.getData(req).then(function (response) {
            var token = response.data.payload;
            // check if remember me has been checked, if so, save the access token to cookie
            if ($scope.remember) {
                $cookies.put("X-Access-Token", token);
            }
            // supply that token as HTTP header parameter to all API calls that client issues.
            //apiService.login(token);

            $state.go("home")
        });
    };
}

/**
 * LogoutCtrl - controller
 */
function LogoutCtrl($scope, $cookies, $state, apiService) {
    $scope.logout = function () {
        $cookies.remove("X-Access-Token");
        //alert($state.current.name)
        if($state.current.name == 'home') window.location.reload();
        //apiService.logout();
        $state.go("home");

    };
    $scope.login = function () {
        $state.go("login");
    };
}

angular
    .module('modeApp')
    .controller('MainCtrl', MainCtrl)
    .controller('LoginCtrl', LoginCtrl)
    .controller('LogoutCtrl', LogoutCtrl)
;
