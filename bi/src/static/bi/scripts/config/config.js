/**
 * Declare constants here.
 */
//var API_URI_ENDPOINT = "http://localhost:8080/data-platform-3.0-bi";
var API_URI_ENDPOINT = "http://www.whatsmode.com/bi";
//var API_URI_ENDPOINT = "http://192.168.2.110:8080/data-platform-3.0-bi";

/**
 * Configure the views and routes based on the UI Router, so that you can change the parts of
 * your site using your routing even if the URL does not change.
 * @param $stateProvider
 * @param $urlRouterProvider
 * @param $httpProvider
 */
function routeConfig($stateProvider, $urlRouterProvider, $httpProvider) {

    /**
     * In case of any API call returns 401 we have to redirect user to login page.
     */
    $httpProvider.interceptors.push('httpInterceptor');


    /**
     * Default all visits to the home page.
     */
    $urlRouterProvider.otherwise("dashboard/summary");

    $stateProvider
        .state('login', {
            url: "/login",
            templateUrl: "views/login.html",
        })
        
        .state('dashboard', {
            abstract:true,
            url: "/dashboard",
            templateUrl: "views/common/content.html",
        })
        .state('dashboard.summary', {
            url: "/summary",
            templateUrl: "views/dashboard/summary.html",
        })
        .state('user', {
            abstract:true,
            url: "/user",
            templateUrl: "views/common/content.html",
        })
        .state('user.register', {
            url: "/register",
            templateUrl: "views/user/newUser.html",
        })
        .state('user.active', {
            url: "/active",
            templateUrl: "views/user/activeUser.html",
        })
        .state('user.retention', {
            url: "/retention",
            templateUrl: "views/user/userRention.html",
        })

        .state('order', {
            abstract:true,
            url: "/order",
            templateUrl: "views/common/content.html",
        })
        .state('order.count', {
            url: "/count",
            templateUrl: "views/order/orderCount.html",
        })
        .state('order.gmv', {
            url: "/gmv",
            templateUrl: "views/order/orderGmv.html",
        })
        .state('post', {
            abstract:true,
            url: "/post",
            templateUrl: "views/common/content.html",
        })
        .state('post.action', {
            url: "/action",
            templateUrl: "views/post/postAction.html",
        })
        ;

}

/**
 * Once the module is registered using the angular.module('app', []); setter, we can get its
 * instance using the angular.module('app'); getter syntax.
 */
angular.module('modeApp')
    .config(routeConfig);