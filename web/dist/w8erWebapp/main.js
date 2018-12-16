(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["main"],{

/***/ "./src/$$_lazy_route_resource lazy recursive":
/*!**********************************************************!*\
  !*** ./src/$$_lazy_route_resource lazy namespace object ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncaught exception popping up in devtools
	return Promise.resolve().then(function() {
		var e = new Error("Cannot find module '" + req + "'");
		e.code = 'MODULE_NOT_FOUND';
		throw e;
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "./src/$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "./src/app/app-routing.module.ts":
/*!***************************************!*\
  !*** ./src/app/app-routing.module.ts ***!
  \***************************************/
/*! exports provided: AppRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppRoutingModule", function() { return AppRoutingModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _rests_rest_requests_rest_requests_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./rests/rest-requests/rest-requests.component */ "./src/app/rests/rest-requests/rest-requests.component.ts");
/* harmony import */ var _user_profile_user_profile_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./user-profile/user-profile.component */ "./src/app/user-profile/user-profile.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};




var routes = [
    { path: 'rest-requests', component: _rests_rest_requests_rest_requests_component__WEBPACK_IMPORTED_MODULE_2__["RestRequestsComponent"] },
    { path: 'user-profile', component: _user_profile_user_profile_component__WEBPACK_IMPORTED_MODULE_3__["UserProfileComponent"] }
];
var AppRoutingModule = /** @class */ (function () {
    function AppRoutingModule() {
    }
    AppRoutingModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"].forRoot(routes)],
            exports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"]]
        })
    ], AppRoutingModule);
    return AppRoutingModule;
}());



/***/ }),

/***/ "./src/app/app.component.html":
/*!************************************!*\
  !*** ./src/app/app.component.html ***!
  \************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<app-header></app-header>\n<main>\n  <router-outlet></router-outlet>\n\n  <agm-map [latitude]=\"51.678418\" [longitude]=\"7.809007\"></agm-map>\n</main>\n"

/***/ }),

/***/ "./src/app/app.component.less":
/*!************************************!*\
  !*** ./src/app/app.component.less ***!
  \************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "main {\n  margin: 1rem auto auto;\n  width: 80%;\n}\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIi9ob21lL2VyYW4vV2Vic3Rvcm1Qcm9qZWN0cy93OGVyL3dlYi9zcmMvYXBwL2FwcC5jb21wb25lbnQubGVzcyIsInNyYy9hcHAvYXBwLmNvbXBvbmVudC5sZXNzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0VBQ0UsdUJBQUE7RUFDQSxXQUFBO0NDQ0QiLCJmaWxlIjoic3JjL2FwcC9hcHAuY29tcG9uZW50Lmxlc3MiLCJzb3VyY2VzQ29udGVudCI6WyJtYWluIHtcbiAgbWFyZ2luOiAxcmVtIGF1dG8gYXV0bztcbiAgd2lkdGg6IDgwJTtcbn1cbiIsIm1haW4ge1xuICBtYXJnaW46IDFyZW0gYXV0byBhdXRvO1xuICB3aWR0aDogODAlO1xufVxuIl19 */"

/***/ }),

/***/ "./src/app/app.component.ts":
/*!**********************************!*\
  !*** ./src/app/app.component.ts ***!
  \**********************************/
/*! exports provided: AppComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppComponent", function() { return AppComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _user_holder_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./user-holder.service */ "./src/app/user-holder.service.ts");
/* harmony import */ var _user_forms_login_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./user-forms/login.service */ "./src/app/user-forms/login.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var AppComponent = /** @class */ (function () {
    function AppComponent(userHolderService, loginService) {
        this.userHolderService = userHolderService;
        this.loginService = loginService;
        this.title = 'w8erWebapp';
    }
    AppComponent.prototype.ngOnInit = function () {
        var _this = this;
        if (!this.userHolderService.isLoggedIn()) {
            try {
                this.loginService.getUserWithToken()
                    .subscribe(function (data) {
                    console.log(typeof (data));
                    if (data instanceof Array && data.length === 0) {
                        console.log('Auto login unsuccessful!');
                        return;
                    }
                    console.log('Auto login success. Authorized!');
                    _this.userHolderService.setUser(data);
                });
            }
            catch (err) {
                console.log('The user have no token.');
            }
        }
    };
    AppComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-root',
            template: __webpack_require__(/*! ./app.component.html */ "./src/app/app.component.html"),
            styles: [__webpack_require__(/*! ./app.component.less */ "./src/app/app.component.less")]
        }),
        __metadata("design:paramtypes", [_user_holder_service__WEBPACK_IMPORTED_MODULE_1__["UserHolderService"],
            _user_forms_login_service__WEBPACK_IMPORTED_MODULE_2__["LoginService"]])
    ], AppComponent);
    return AppComponent;
}());



/***/ }),

/***/ "./src/app/app.module.ts":
/*!*******************************!*\
  !*** ./src/app/app.module.ts ***!
  \*******************************/
/*! exports provided: AppModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppModule", function() { return AppModule; });
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/platform-browser */ "./node_modules/@angular/platform-browser/fesm5/platform-browser.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _app_routing_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app-routing.module */ "./src/app/app-routing.module.ts");
/* harmony import */ var _app_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./app.component */ "./src/app/app.component.ts");
/* harmony import */ var _header_header_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./header/header.component */ "./src/app/header/header.component.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/platform-browser/animations */ "./node_modules/@angular/platform-browser/fesm5/animations.js");
/* harmony import */ var _posts_post_create_post_create_component__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ./posts/post-create/post-create.component */ "./src/app/posts/post-create/post-create.component.ts");
/* harmony import */ var _posts_post_list_post_list_component__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ./posts/post-list/post-list.component */ "./src/app/posts/post-list/post-list.component.ts");
/* harmony import */ var _posts_posts_service__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! ./posts/posts.service */ "./src/app/posts/posts.service.ts");
/* harmony import */ var _user_forms_login_login_component__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! ./user-forms/login/login.component */ "./src/app/user-forms/login/login.component.ts");
/* harmony import */ var _user_forms_login_service__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ./user-forms/login.service */ "./src/app/user-forms/login.service.ts");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _angular_http__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! @angular/http */ "./node_modules/@angular/http/fesm5/http.js");
/* harmony import */ var _http_error_handler_service__WEBPACK_IMPORTED_MODULE_15__ = __webpack_require__(/*! ./http-error-handler.service */ "./src/app/http-error-handler.service.ts");
/* harmony import */ var _message_service__WEBPACK_IMPORTED_MODULE_16__ = __webpack_require__(/*! ./message.service */ "./src/app/message.service.ts");
/* harmony import */ var _rests_rest_requests_rest_requests_component__WEBPACK_IMPORTED_MODULE_17__ = __webpack_require__(/*! ./rests/rest-requests/rest-requests.component */ "./src/app/rests/rest-requests/rest-requests.component.ts");
/* harmony import */ var _angular_flex_layout__WEBPACK_IMPORTED_MODULE_18__ = __webpack_require__(/*! @angular/flex-layout */ "./node_modules/@angular/flex-layout/esm5/flex-layout.es5.js");
/* harmony import */ var _user_profile_user_profile_component__WEBPACK_IMPORTED_MODULE_19__ = __webpack_require__(/*! ./user-profile/user-profile.component */ "./src/app/user-profile/user-profile.component.ts");
/* harmony import */ var _auth_interceptor_service__WEBPACK_IMPORTED_MODULE_20__ = __webpack_require__(/*! ./auth-interceptor.service */ "./src/app/auth-interceptor.service.ts");
/* harmony import */ var _agm_core__WEBPACK_IMPORTED_MODULE_21__ = __webpack_require__(/*! @agm/core */ "./node_modules/@agm/core/index.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};






















var AppModule = /** @class */ (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
            declarations: [
                _app_component__WEBPACK_IMPORTED_MODULE_3__["AppComponent"],
                _header_header_component__WEBPACK_IMPORTED_MODULE_4__["HeaderComponent"],
                _posts_post_create_post_create_component__WEBPACK_IMPORTED_MODULE_8__["PostCreateComponent"],
                _posts_post_list_post_list_component__WEBPACK_IMPORTED_MODULE_9__["PostListComponent"],
                _user_forms_login_login_component__WEBPACK_IMPORTED_MODULE_11__["LoginComponent"],
                _rests_rest_requests_rest_requests_component__WEBPACK_IMPORTED_MODULE_17__["RestRequestsComponent"],
                _user_profile_user_profile_component__WEBPACK_IMPORTED_MODULE_19__["UserProfileComponent"],
            ],
            imports: [
                _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__["BrowserModule"],
                _app_routing_module__WEBPACK_IMPORTED_MODULE_2__["AppRoutingModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_5__["FormsModule"],
                _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_7__["BrowserAnimationsModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_6__["MatInputModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_6__["MatCardModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_6__["MatButtonModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_6__["MatToolbarModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_6__["MatExpansionModule"],
                _angular_common_http__WEBPACK_IMPORTED_MODULE_13__["HttpClientModule"],
                _angular_http__WEBPACK_IMPORTED_MODULE_14__["HttpModule"],
                _angular_flex_layout__WEBPACK_IMPORTED_MODULE_18__["FlexLayoutModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_6__["MatSidenavModule"],
                _agm_core__WEBPACK_IMPORTED_MODULE_21__["AgmCoreModule"].forRoot({
                    apiKey: 'AIzaSyAyKg00vFRU05qHOX6GTCR7ANb9RRYSj_o'
                }),
                _angular_material__WEBPACK_IMPORTED_MODULE_6__["MatDialogModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_6__["MatIconModule"]
            ],
            providers: [
                _posts_posts_service__WEBPACK_IMPORTED_MODULE_10__["PostsService"],
                _user_forms_login_service__WEBPACK_IMPORTED_MODULE_12__["LoginService"],
                _http_error_handler_service__WEBPACK_IMPORTED_MODULE_15__["HttpErrorHandler"],
                _message_service__WEBPACK_IMPORTED_MODULE_16__["MessageService"],
                {
                    provide: _angular_common_http__WEBPACK_IMPORTED_MODULE_13__["HTTP_INTERCEPTORS"],
                    useClass: _auth_interceptor_service__WEBPACK_IMPORTED_MODULE_20__["AuthInterceptor"],
                    multi: true
                }
            ],
            bootstrap: [_app_component__WEBPACK_IMPORTED_MODULE_3__["AppComponent"]],
            entryComponents: [_user_forms_login_login_component__WEBPACK_IMPORTED_MODULE_11__["LoginComponent"]]
        })
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "./src/app/auth-interceptor.service.ts":
/*!*********************************************!*\
  !*** ./src/app/auth-interceptor.service.ts ***!
  \*********************************************/
/*! exports provided: AuthInterceptor */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AuthInterceptor", function() { return AuthInterceptor; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var AuthInterceptor = /** @class */ (function () {
    function AuthInterceptor() {
    }
    AuthInterceptor.prototype.intercept = function (req, next) {
        var idToken = localStorage.getItem("token");
        if (idToken) {
            var cloned = req.clone({
                headers: req.headers.set("x-access-token", idToken)
            });
            return next.handle(cloned);
        }
        else {
            return next.handle(req);
        }
    };
    AuthInterceptor = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({
            providedIn: 'root'
        })
    ], AuthInterceptor);
    return AuthInterceptor;
}());



/***/ }),

/***/ "./src/app/header/header.component.html":
/*!**********************************************!*\
  !*** ./src/app/header/header.component.html ***!
  \**********************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<nav class=\"w8er-nav navbar navbar-expand-md navbar-light bg-faded\">\n  <button class=\"navbar-toggler navbar-toggler-right\" type=\"button\" data-toggle=\"collapse\"\n          data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\"\n          aria-label=\"Toggle navigation\">\n    <span class=\"navbar-toggler-icon\"></span>\n  </button>\n\n  <div routerLink=\"/\" class=\"w8er-logo-placeholder pull-left\">W8er</div>\n\n  <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">\n    <ul class=\"navbar-nav mr-auto\">\n\n      <li class=\"nav-item active home-nav-btn fade-in first\">\n        <a class=\"nav-link liner\" routerLink=\"/\">Home</a>\n      </li>\n      <li *ngIf=\"isLoggedIn()\" class=\"nav-item non-home-nav-btn fade-in first\">\n        <a class=\"nav-link profile-link liner\" routerLink=\"/user-profile\">Profile</a>\n      </li>\n\n      <li *ngIf=\"isLoggedIn()\" class=\"nav-item non-home-nav-btn fade-in first\">\n        <a class=\"nav-link restaurants-link liner\" routerLink=\"/\">Restaurants</a>\n      </li>\n\n    </ul>\n\n    <!--Notifications part:-->\n    <!--<div class=\"fade-in first\" ng-show=\"isLoggedIn()\">-->\n    <!--<button type=\"button\" class=\"fas fa-bell btn btn-success\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">-->\n    <!--</button>-->\n    <!--<div class=\"dropdown-menu dropdown-menu-right notifications-cont\">-->\n\n    <!--<div class=\"loading\" ng-show=\"loadingNotifications\" style=\"text-align: center;\">-->\n    <!--<div class=\"fa fa-spinner fa-spin\" style=\"font-size:34px; margin: auto; width: 100%; margin-top: 8%;\"></div>-->\n    <!--</div>-->\n\n    <!--<div class=\"no-nofitifications\" ng-if=\"notifications.length === 0\">-->\n    <!--No notifications-->\n    <!--</div>-->\n    <!--<div class=\"dropdown-item\" ng-repeat=\"notification in notifications\">-->\n    <!--<div class=\"notification-body font-weight-bold\">{{notification.content}}</div>-->\n    <!--<div class=\"notification-date\">{{notification.creation_date | date: 'medium'}}</div>-->\n\n    <!--</div>-->\n    <!--</div>-->\n    <!--</div>-->\n\n\n\n    <button\n      *ngIf=\"!isLoggedIn()\"\n      class=\"nav-link login\"\n      mat-raised-button\n      color=\"accent\"\n      type=\"submit\"\n      (click)=\"openDialog()\">Request login</button>\n\n    <button\n      *ngIf=\"isLoggedIn()\"\n      class=\"nav-link login\"\n      mat-raised-button\n      color=\"primary\"\n      type=\"submit\"\n      (click)=\"logout()\">Logout</button>\n    <!--<div class=\"nav-link login\" style=\"cursor: pointer\" (click)=\"openDialog()\">-->\n      <!--Log In-->\n      <!--<div class=\"underlined\"></div>-->\n    <!--</div>-->\n\n    <!--Log out:-->\n    <!--<div class=\"nav-link login\" style=\"cursor: pointer\">-->\n    <!--<div class=\"logout-activator\" ng-click=\"logout()\">Log Out</div>-->\n    <!--<div class=\"underlined\"></div>-->\n    <!--</div>-->\n\n\n    <!--Search: -->\n    <!--<form class=\"form-inline my-2 my-lg-0 search-nav-form animate-show-hide\" ng-show=\"showSearchNav\">-->\n\n      <!--<input class=\"form-control mr-sm-2\" type=\"text\" placeholder=\"Search\" ng-model=\"searchTerm.keywords\">-->\n      <!--<button class=\"btn btn-warning my-2 my-sm-0\" type=\"submit\">Search</button>-->\n\n    <!--</form>-->\n  </div>\n</nav>\n\n"

/***/ }),

/***/ "./src/app/header/header.component.less":
/*!**********************************************!*\
  !*** ./src/app/header/header.component.less ***!
  \**********************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".w8er-nav {\n  z-index: 99;\n  /* Permalink - use to edit and share this gradient: http://colorzilla.com/gradient-editor/#ff321b+66,ffca1b+89,47fa00+100 */\n  background: #780097;\n  /* Old browsers */\n}\n.w8er-nav .w8er-logo-placeholder {\n  color: white;\n}\n.w8er-nav .animate-show-hide {\n  transition: all linear 0.5s;\n}\n.w8er-nav .animate-show-hide.ng-hide {\n  transition: all linear 0.5s;\n  opacity: 0;\n}\n.w8er-nav .notifications-cont {\n  background-color: #b3ff99;\n}\n.w8er-nav .notifications-cont .no-nofitifications {\n  text-align: center;\n}\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIi9ob21lL2VyYW4vV2Vic3Rvcm1Qcm9qZWN0cy93OGVyL3dlYi9zcmMvYXBwL2hlYWRlci9oZWFkZXIuY29tcG9uZW50Lmxlc3MiLCJzcmMvYXBwL2hlYWRlci9oZWFkZXIuY29tcG9uZW50Lmxlc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7RUFDRSxZQUFBO0VDQ0EsNEhBQTRIO0VESTVILG9CQUFBO0VDRkEsa0JBQWtCO0NBQ25CO0FETEQ7RUFTSSxhQUFBO0NDREg7QURSRDtFQWVJLDRCQUFBO0NDSkg7QURPRztFQUNFLDRCQUFBO0VBQ0EsV0FBQTtDQ0xMO0FEZkQ7RUF5QkksMEJBQUE7Q0NQSDtBRGxCRDtFQTRCTSxtQkFBQTtDQ1BMIiwiZmlsZSI6InNyYy9hcHAvaGVhZGVyL2hlYWRlci5jb21wb25lbnQubGVzcyIsInNvdXJjZXNDb250ZW50IjpbIi53OGVyLW5hdiB7XG4gIHotaW5kZXg6IDk5O1xuICAvL2hlaWdodDogNTBweDtcbiAgLy9iYWNrZ3JvdW5kLWNvbG9yOiAjZmY2NjU0ICFpbXBvcnRhbnQ7XG5cbiAgLyogUGVybWFsaW5rIC0gdXNlIHRvIGVkaXQgYW5kIHNoYXJlIHRoaXMgZ3JhZGllbnQ6IGh0dHA6Ly9jb2xvcnppbGxhLmNvbS9ncmFkaWVudC1lZGl0b3IvI2ZmMzIxYis2NixmZmNhMWIrODksNDdmYTAwKzEwMCAqL1xuICBiYWNrZ3JvdW5kOiByZ2IoMTIwLCAwLCAxNTEpOyAvKiBPbGQgYnJvd3NlcnMgKi9cblxuICAudzhlci1sb2dvLXBsYWNlaG9sZGVyIHtcbiAgICBjb2xvcjogd2hpdGU7XG4gIH1cbiAgLnNlYXJjaC1uYXYtZm9ybSB7XG4gIH1cblxuICAuYW5pbWF0ZS1zaG93LWhpZGUge1xuICAgIHRyYW5zaXRpb246IGFsbCBsaW5lYXIgMC41cztcbiAgfVxuICAuYW5pbWF0ZS1zaG93LWhpZGUge1xuICAgICYubmctaGlkZSB7XG4gICAgICB0cmFuc2l0aW9uOiBhbGwgbGluZWFyIDAuNXM7XG4gICAgICBvcGFjaXR5OiAwO1xuICAgIH1cbiAgfVxuXG4gIC5ub3RpZmljYXRpb25zLWNvbnQge1xuICAgIGJhY2tncm91bmQtY29sb3I6ICNiM2ZmOTk7XG5cbiAgICAubm8tbm9maXRpZmljYXRpb25zIHtcbiAgICAgIHRleHQtYWxpZ246IGNlbnRlcjtcbiAgICB9XG4gIH1cbn1cbiIsIi53OGVyLW5hdiB7XG4gIHotaW5kZXg6IDk5O1xuICAvKiBQZXJtYWxpbmsgLSB1c2UgdG8gZWRpdCBhbmQgc2hhcmUgdGhpcyBncmFkaWVudDogaHR0cDovL2NvbG9yemlsbGEuY29tL2dyYWRpZW50LWVkaXRvci8jZmYzMjFiKzY2LGZmY2ExYis4OSw0N2ZhMDArMTAwICovXG4gIGJhY2tncm91bmQ6ICM3ODAwOTc7XG4gIC8qIE9sZCBicm93c2VycyAqL1xufVxuLnc4ZXItbmF2IC53OGVyLWxvZ28tcGxhY2Vob2xkZXIge1xuICBjb2xvcjogd2hpdGU7XG59XG4udzhlci1uYXYgLmFuaW1hdGUtc2hvdy1oaWRlIHtcbiAgdHJhbnNpdGlvbjogYWxsIGxpbmVhciAwLjVzO1xufVxuLnc4ZXItbmF2IC5hbmltYXRlLXNob3ctaGlkZS5uZy1oaWRlIHtcbiAgdHJhbnNpdGlvbjogYWxsIGxpbmVhciAwLjVzO1xuICBvcGFjaXR5OiAwO1xufVxuLnc4ZXItbmF2IC5ub3RpZmljYXRpb25zLWNvbnQge1xuICBiYWNrZ3JvdW5kLWNvbG9yOiAjYjNmZjk5O1xufVxuLnc4ZXItbmF2IC5ub3RpZmljYXRpb25zLWNvbnQgLm5vLW5vZml0aWZpY2F0aW9ucyB7XG4gIHRleHQtYWxpZ246IGNlbnRlcjtcbn1cbiJdfQ== */"

/***/ }),

/***/ "./src/app/header/header.component.ts":
/*!********************************************!*\
  !*** ./src/app/header/header.component.ts ***!
  \********************************************/
/*! exports provided: HeaderComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "HeaderComponent", function() { return HeaderComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var _user_forms_login_login_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../user-forms/login/login.component */ "./src/app/user-forms/login/login.component.ts");
/* harmony import */ var _user_holder_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../user-holder.service */ "./src/app/user-holder.service.ts");
/* harmony import */ var _user_forms_login_service__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../user-forms/login.service */ "./src/app/user-forms/login.service.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};






var HeaderComponent = /** @class */ (function () {
    function HeaderComponent(dialog, userHolderService, loginService, router) {
        this.dialog = dialog;
        this.userHolderService = userHolderService;
        this.loginService = loginService;
        this.router = router;
    }
    HeaderComponent.prototype.openDialog = function () {
        var dialogConfig = new _angular_material__WEBPACK_IMPORTED_MODULE_1__["MatDialogConfig"]();
        dialogConfig.disableClose = true;
        dialogConfig.autoFocus = true;
        this.dialog.open(_user_forms_login_login_component__WEBPACK_IMPORTED_MODULE_2__["LoginComponent"], dialogConfig);
    };
    HeaderComponent.prototype.isLoggedIn = function () {
        return this.userHolderService.isLoggedIn();
    };
    HeaderComponent.prototype.logout = function () {
        this.userHolderService.removeUser();
        this.loginService.removeToken();
        this.router.navigate(['']);
    };
    HeaderComponent.prototype.ngOnInit = function () {
    };
    HeaderComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-header',
            template: __webpack_require__(/*! ./header.component.html */ "./src/app/header/header.component.html"),
            styles: [__webpack_require__(/*! ./header.component.less */ "./src/app/header/header.component.less")]
        }),
        __metadata("design:paramtypes", [_angular_material__WEBPACK_IMPORTED_MODULE_1__["MatDialog"], _user_holder_service__WEBPACK_IMPORTED_MODULE_3__["UserHolderService"],
            _user_forms_login_service__WEBPACK_IMPORTED_MODULE_4__["LoginService"], _angular_router__WEBPACK_IMPORTED_MODULE_5__["Router"]])
    ], HeaderComponent);
    return HeaderComponent;
}());



/***/ }),

/***/ "./src/app/http-error-handler.service.ts":
/*!***********************************************!*\
  !*** ./src/app/http-error-handler.service.ts ***!
  \***********************************************/
/*! exports provided: HttpErrorHandler */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "HttpErrorHandler", function() { return HttpErrorHandler; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var rxjs__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! rxjs */ "./node_modules/rxjs/_esm5/index.js");
/* harmony import */ var _message_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./message.service */ "./src/app/message.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



/** Handles HttpClient errors */
var HttpErrorHandler = /** @class */ (function () {
    function HttpErrorHandler(messageService) {
        var _this = this;
        this.messageService = messageService;
        /** Create curried handleError function that already knows the service name */
        this.createHandleError = function (serviceName) {
            if (serviceName === void 0) { serviceName = ''; }
            return function (operation, result) {
                if (operation === void 0) { operation = 'operation'; }
                if (result === void 0) { result = {}; }
                return _this.handleError(serviceName, operation, result);
            };
        };
    }
    /**
     * Returns a function that handles Http operation failures.
     * This error handler lets the app continue to run as if no error occurred.
     * @param serviceName = name of the data service that attempted the operation
     * @param operation - name of the operation that failed
     * @param result - optional value to return as the observable result
     */
    HttpErrorHandler.prototype.handleError = function (serviceName, operation, result) {
        var _this = this;
        if (serviceName === void 0) { serviceName = ''; }
        if (operation === void 0) { operation = 'operation'; }
        if (result === void 0) { result = {}; }
        return function (error) {
            // TODO: send the error to remote logging infrastructure
            console.error(error); // log to console instead
            var message = (error.error instanceof ErrorEvent) ?
                error.error.message :
                "server returned code " + error.status + " with body \"" + error.error + "\"";
            // TODO: better job of transforming error for user consumption
            _this.messageService.add(serviceName + ": " + operation + " failed: " + message);
            // Let the app keep running by returning a safe result.
            return Object(rxjs__WEBPACK_IMPORTED_MODULE_1__["of"])(result);
        };
    };
    HttpErrorHandler = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])(),
        __metadata("design:paramtypes", [_message_service__WEBPACK_IMPORTED_MODULE_2__["MessageService"]])
    ], HttpErrorHandler);
    return HttpErrorHandler;
}());

/*
Copyright 2017-2018 Google Inc. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at http://angular.io/license
*/ 


/***/ }),

/***/ "./src/app/message.service.ts":
/*!************************************!*\
  !*** ./src/app/message.service.ts ***!
  \************************************/
/*! exports provided: MessageService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MessageService", function() { return MessageService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var MessageService = /** @class */ (function () {
    function MessageService() {
        this.messages = [];
    }
    MessageService.prototype.add = function (message) {
        this.messages.push(message);
    };
    MessageService.prototype.clear = function () {
        this.messages = [];
    };
    MessageService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])()
    ], MessageService);
    return MessageService;
}());

/*
Copyright 2017-2018 Google Inc. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at http://angular.io/license
*/ 


/***/ }),

/***/ "./src/app/posts/post-create/post-create.component.css":
/*!*************************************************************!*\
  !*** ./src/app/posts/post-create/post-create.component.css ***!
  \*************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\nmat-form-field,\ntextarea {\n  width: 100%;\n\n}\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvcG9zdHMvcG9zdC1jcmVhdGUvcG9zdC1jcmVhdGUuY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiO0FBQ0E7O0VBRUUsWUFBWTs7Q0FFYiIsImZpbGUiOiJzcmMvYXBwL3Bvc3RzL3Bvc3QtY3JlYXRlL3Bvc3QtY3JlYXRlLmNvbXBvbmVudC5jc3MiLCJzb3VyY2VzQ29udGVudCI6WyJcbm1hdC1mb3JtLWZpZWxkLFxudGV4dGFyZWEge1xuICB3aWR0aDogMTAwJTtcblxufVxuIl19 */"

/***/ }),

/***/ "./src/app/posts/post-create/post-create.component.html":
/*!**************************************************************!*\
  !*** ./src/app/posts/post-create/post-create.component.html ***!
  \**************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<!--<textarea rows=\"6\" [value]=\"newPost\" #postInput>{{ newPost }}</textarea>-->\n<mat-card>\n  <form (submit)=\"onAddPost(postForm)\" #postForm=\"ngForm\">\n    <mat-form-field>\n      <!--<input matInput type=\"text\" [(ngModel)]=\"enteredTitle\">-->\n      <input matInput type=\"text\" ngModel name=\"title\" required #title=\"ngModel\">\n      <mat-error *ngIf=\"title.invalid\">Please enter a title</mat-error>\n    </mat-form-field>\n    <mat-form-field>\n      <!--<textarea matInput rows=\"6\" [(ngModel)]=\"enteredContent\">{{ newPost }}</textarea>-->\n      <textarea matInput rows=\"4\" ngModel name=\"content\">{{ newPost }}</textarea>\n    </mat-form-field>\n    <button\n      mat-raised-button\n      color=\"accent\"\n      type=\"submit\">Save Post</button>\n    <!--(click)=\"onAddPost()\">Save Post</button>-->\n  </form>\n\n</mat-card>\n\n<!--<button (click)=\"onAddPost(postInput)\">Save Post</button>-->\n\n\n<p>{{ newPost }}</p>\n\n\n"

/***/ }),

/***/ "./src/app/posts/post-create/post-create.component.ts":
/*!************************************************************!*\
  !*** ./src/app/posts/post-create/post-create.component.ts ***!
  \************************************************************/
/*! exports provided: PostCreateComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "PostCreateComponent", function() { return PostCreateComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _posts_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../posts.service */ "./src/app/posts/posts.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// import { Post } from '../posts.model';
// import { Component, EventEmitter, Output } from "@angular/core";


var PostCreateComponent = /** @class */ (function () {
    // @Output() postCreated = new EventEmitter<Post>();
    // onAddPost(postInput: HTMLTextAreaElement) {
    //   // console.dir(postInput);
    //   this.newPost = postInput.value;
    // }
    function PostCreateComponent(postsService) {
        this.postsService = postsService;
        this.enteredTitle = "";
        this.enteredContent = "";
    }
    PostCreateComponent.prototype.onAddPost = function (form) {
        // const post: Post = {title: this.enteredTitle, content: this.enteredContent};
        if (form.invalid) {
            return;
        }
        // const post: Post = {title: form.value.title, content: form.value.content};
        // this.postCreated.emit(post);
        this.postsService.addPost(form.value.title, form.value.content);
    };
    PostCreateComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-post-create',
            template: __webpack_require__(/*! ./post-create.component.html */ "./src/app/posts/post-create/post-create.component.html"),
            styles: [__webpack_require__(/*! ./post-create.component.css */ "./src/app/posts/post-create/post-create.component.css")]
        }),
        __metadata("design:paramtypes", [_posts_service__WEBPACK_IMPORTED_MODULE_1__["PostsService"]])
    ], PostCreateComponent);
    return PostCreateComponent;
}());



/***/ }),

/***/ "./src/app/posts/post-list/post-list.component.html":
/*!**********************************************************!*\
  !*** ./src/app/posts/post-list/post-list.component.html ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<!--<mat-accordion multi=\"true\">-->   <!--Set multi=true if you want more than one to be expandable at once-->\n<mat-accordion *ngIf=\"posts.length > 0\">\n  <mat-expansion-panel *ngFor=\"let post of posts\">\n    <mat-expansion-panel-header>\n      {{ post.title }}\n    </mat-expansion-panel-header>\n\n    <p>{{ post.content }}</p>\n  </mat-expansion-panel>\n</mat-accordion>\n<p class=\"info-text mat-body-1\" *ngIf=\"posts.length == 0\">No posts added yet</p>\n"

/***/ }),

/***/ "./src/app/posts/post-list/post-list.component.less":
/*!**********************************************************!*\
  !*** ./src/app/posts/post-list/post-list.component.less ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ":host {\n  display: block;\n  margin-top: 1rem;\n}\n.info-text {\n  text-align: center  ;\n}\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIi9ob21lL2VyYW4vV2Vic3Rvcm1Qcm9qZWN0cy93OGVyL3dlYi9zcmMvYXBwL3Bvc3RzL3Bvc3QtbGlzdC9wb3N0LWxpc3QuY29tcG9uZW50Lmxlc3MiLCJzcmMvYXBwL3Bvc3RzL3Bvc3QtbGlzdC9wb3N0LWxpc3QuY29tcG9uZW50Lmxlc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7RUFDRSxlQUFBO0VBQ0EsaUJBQUE7Q0NDRDtBREVEO0VBQ0UscUJBQUE7Q0NBRCIsImZpbGUiOiJzcmMvYXBwL3Bvc3RzL3Bvc3QtbGlzdC9wb3N0LWxpc3QuY29tcG9uZW50Lmxlc3MiLCJzb3VyY2VzQ29udGVudCI6WyI6aG9zdCB7XG4gIGRpc3BsYXk6IGJsb2NrO1xuICBtYXJnaW4tdG9wOiAxcmVtO1xufVxuXG4uaW5mby10ZXh0IHtcbiAgdGV4dC1hbGlnbjogY2VudGVyICA7XG59XG4iLCI6aG9zdCB7XG4gIGRpc3BsYXk6IGJsb2NrO1xuICBtYXJnaW4tdG9wOiAxcmVtO1xufVxuLmluZm8tdGV4dCB7XG4gIHRleHQtYWxpZ246IGNlbnRlciAgO1xufVxuIl19 */"

/***/ }),

/***/ "./src/app/posts/post-list/post-list.component.ts":
/*!********************************************************!*\
  !*** ./src/app/posts/post-list/post-list.component.ts ***!
  \********************************************************/
/*! exports provided: PostListComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "PostListComponent", function() { return PostListComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _posts_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../posts.service */ "./src/app/posts/posts.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
// import { Component, OnInit, Input } from '@angular/core';


var PostListComponent = /** @class */ (function () {
    function PostListComponent(postsService) {
        this.postsService = postsService;
        // posts = [
        //   {title: 'First Post', content: 'This is the first post\'s content'},
        //   {title: 'Seconds Post', content: 'This is the second post\'s content'},
        //   {title: 'Third Post', content: 'This is the third post\'s content'},
        // ];
        // @Input() posts: Post[] = [];
        this.posts = [];
    }
    // Option 1 for injecting dependencies: (Option 2 is above)
    // postsService: PostsService;
    //
    // constructor(postsService: PostsService) {
    //   this.postsService = postsService;
    // }
    PostListComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.posts = this.postsService.getPosts();
        this.postsSub = this.postsService.getPostUpdateListener()
            .subscribe(function (posts) {
            _this.posts = posts;
        });
    };
    PostListComponent.prototype.ngOnDestroy = function () {
        this.postsSub.unsubscribe();
    };
    PostListComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-post-list',
            template: __webpack_require__(/*! ./post-list.component.html */ "./src/app/posts/post-list/post-list.component.html"),
            styles: [__webpack_require__(/*! ./post-list.component.less */ "./src/app/posts/post-list/post-list.component.less")]
        }),
        __metadata("design:paramtypes", [_posts_service__WEBPACK_IMPORTED_MODULE_1__["PostsService"]])
    ], PostListComponent);
    return PostListComponent;
}());



/***/ }),

/***/ "./src/app/posts/posts.service.ts":
/*!****************************************!*\
  !*** ./src/app/posts/posts.service.ts ***!
  \****************************************/
/*! exports provided: PostsService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "PostsService", function() { return PostsService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var rxjs__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! rxjs */ "./node_modules/rxjs/_esm5/index.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};


var PostsService = /** @class */ (function () {
    function PostsService() {
        this.posts = [];
        this.postsUpdated = new rxjs__WEBPACK_IMPORTED_MODULE_1__["Subject"]();
    }
    PostsService.prototype.getPosts = function () {
        return this.posts.slice();
    };
    PostsService.prototype.addPost = function (title, content) {
        var post = {
            title: title,
            content: content
        };
        this.posts.push(post);
        this.postsUpdated.next(this.posts.slice());
    };
    PostsService.prototype.getPostUpdateListener = function () {
        return this.postsUpdated.asObservable();
    };
    PostsService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({ providedIn: 'root' })
    ], PostsService);
    return PostsService;
}());



/***/ }),

/***/ "./src/app/rests/rest-requests/rest-requests.component.css":
/*!*****************************************************************!*\
  !*** ./src/app/rests/rest-requests/rest-requests.component.css ***!
  \*****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL3Jlc3RzL3Jlc3QtcmVxdWVzdHMvcmVzdC1yZXF1ZXN0cy5jb21wb25lbnQuY3NzIn0= */"

/***/ }),

/***/ "./src/app/rests/rest-requests/rest-requests.component.html":
/*!******************************************************************!*\
  !*** ./src/app/rests/rest-requests/rest-requests.component.html ***!
  \******************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<p>\n  rest-requests works!\n</p>\n"

/***/ }),

/***/ "./src/app/rests/rest-requests/rest-requests.component.ts":
/*!****************************************************************!*\
  !*** ./src/app/rests/rest-requests/rest-requests.component.ts ***!
  \****************************************************************/
/*! exports provided: RestRequestsComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "RestRequestsComponent", function() { return RestRequestsComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var RestRequestsComponent = /** @class */ (function () {
    function RestRequestsComponent() {
    }
    RestRequestsComponent.prototype.ngOnInit = function () {
    };
    RestRequestsComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-rest-requests',
            template: __webpack_require__(/*! ./rest-requests.component.html */ "./src/app/rests/rest-requests/rest-requests.component.html"),
            styles: [__webpack_require__(/*! ./rest-requests.component.css */ "./src/app/rests/rest-requests/rest-requests.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], RestRequestsComponent);
    return RestRequestsComponent;
}());



/***/ }),

/***/ "./src/app/user-forms/login.service.ts":
/*!*********************************************!*\
  !*** ./src/app/user-forms/login.service.ts ***!
  \*********************************************/
/*! exports provided: LoginService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "LoginService", function() { return LoginService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var rxjs_operators__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! rxjs/operators */ "./node_modules/rxjs/_esm5/operators/index.js");
/* harmony import */ var _http_error_handler_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../http-error-handler.service */ "./src/app/http-error-handler.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var LoginService = /** @class */ (function () {
    function LoginService(http, httpErrorHandler) {
        this.http = http;
        this.httpOptions = {};
        this.handleError = httpErrorHandler.createHandleError('sService');
        this.httpOptions = {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpHeaders"]({
                'Content-Type': 'application/json' // ,
                // 'Authorization': 'my-auth-token'
            })
        };
    }
    LoginService.prototype.login = function (phone_num) {
        console.log('CALLED LOGIN WITH PHONE = ' + phone_num);
        return this.http.post('/auth/login-signup', { phone_number: phone_num }, { headers: this.httpOptions })
            .pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_2__["catchError"])(this.handleError('login', [])));
    };
    LoginService.prototype.verify = function (phone_num, ver_num) {
        console.log('CALLED VERIFY WITH PHONE = ' + phone_num + ' AND PASSWORD = ' + ver_num);
        return this.http.post('/auth/verify', { phone_number: phone_num, password: ver_num }, { headers: this.httpOptions })
            .pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_2__["catchError"])(this.handleError('verify', [])));
    };
    LoginService.prototype.setToken = function (token) {
        if (token) {
            localStorage.setItem('token', token);
            return true;
        }
        console.log('Token NOT found in setToken');
        return false;
    };
    LoginService.prototype.removeToken = function () {
        localStorage.removeItem('token');
    };
    LoginService.prototype.hasToken = function () {
        var token = localStorage.getItem('token');
        if (token) {
            return true;
        }
        return false;
    };
    LoginService.prototype.getUserWithToken = function () {
        if (this.hasToken()) {
            return this.http.get('/user/get-profile', { headers: this.httpOptions })
                .pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_2__["catchError"])(this.handleError('login', [])));
        }
    };
    LoginService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpClient"], _http_error_handler_service__WEBPACK_IMPORTED_MODULE_3__["HttpErrorHandler"]])
    ], LoginService);
    return LoginService;
}());



/***/ }),

/***/ "./src/app/user-forms/login/login.component.html":
/*!*******************************************************!*\
  !*** ./src/app/user-forms/login/login.component.html ***!
  \*******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<mat-card>\n  <form (submit)=\"login(postForm)\" #postForm=\"ngForm\">\n    <mat-form-field>\n\n      <!--<input matInput type=\"number\" ngModel name=\"phonenum\" required #phonenum=\"ngModel\">-->\n      <input matInput type=\"text\" ngModel name=\"phonenum\" required #phonenum=\"ngModel\" (keypress)=\"numberOnly($event)\">\n\n      <mat-error *ngIf=\"phonenum.invalid\">Please a phone number</mat-error>\n    </mat-form-field>\n\n    <button\n      mat-raised-button\n      color=\"primary\"\n      type=\"submit\">Request login</button>\n\n  </form>\n\n  <form class=\"verify-code\" (submit)=\"verifyCode(postForm)\" #postForm=\"ngForm\" *ngIf=\"codeRequestWasSuccessful\">\n    <mat-form-field>\n\n      <input matInput type=\"text\" ngModel name=\"vernum\" required #vernum=\"ngModel\" (keypress)=\"numberOnly($event)\">\n\n      <mat-error *ngIf=\"vernum.invalid\">Please a phone number</mat-error>\n    </mat-form-field>\n\n    <button\n      mat-raised-button\n      color=\"primary\"\n      type=\"submit\">Verify</button>\n\n  </form>\n\n  <mat-dialog-actions>\n    <button class=\"mat-raised-button\"(click)=\"closeDialog()\">Close</button>\n  </mat-dialog-actions>\n\n</mat-card>\n\n"

/***/ }),

/***/ "./src/app/user-forms/login/login.component.less":
/*!*******************************************************!*\
  !*** ./src/app/user-forms/login/login.component.less ***!
  \*******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL3VzZXItZm9ybXMvbG9naW4vbG9naW4uY29tcG9uZW50Lmxlc3MifQ== */"

/***/ }),

/***/ "./src/app/user-forms/login/login.component.ts":
/*!*****************************************************!*\
  !*** ./src/app/user-forms/login/login.component.ts ***!
  \*****************************************************/
/*! exports provided: LoginComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "LoginComponent", function() { return LoginComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _login_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../login.service */ "./src/app/user-forms/login.service.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _user_holder_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../user-holder.service */ "./src/app/user-holder.service.ts");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm5/material.es5.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var __param = (undefined && undefined.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};





var LoginComponent = /** @class */ (function () {
    function LoginComponent(loginService, router, userHolderService, dialogRef, data) {
        this.loginService = loginService;
        this.router = router;
        this.userHolderService = userHolderService;
        this.dialogRef = dialogRef;
        this.localData = {};
        this.codeRequestWasSuccessful = false;
        // Saving the number for retrying
        this.numberSaveForRetry = '';
    }
    LoginComponent.prototype.numberOnly = function (event) {
        var charCode = (event.which) ? event.which : event.keyCode;
        return !(charCode > 31 && (charCode < 48 || charCode > 57));
    };
    LoginComponent.prototype.login = function (form) {
        var _this = this;
        if (form.invalid) {
            return;
        }
        var val = form.value.phonenum;
        this.numberSaveForRetry = val;
        console.log('Phone = ' + val);
        this.loginService.login(val)
            .subscribe(function (data) {
            console.log(typeof (data));
            if (data instanceof Array && data.length === 0) {
                console.log('No data received');
                _this.codeRequestWasSuccessful = false;
                return;
            }
            _this.codeRequestWasSuccessful = true;
            console.log('The user got a code');
        });
    };
    LoginComponent.prototype.verifyCode = function (form) {
        var _this = this;
        if (form.invalid) {
            return;
        }
        var val = form.value.vernum;
        console.log('Verify number = ' + val);
        this.loginService.verify(this.numberSaveForRetry, val)
            .subscribe(function (data) {
            console.log(typeof (data));
            if (data instanceof Array && data.length === 0) {
                console.log('Wrong code!');
                _this.codeRequestWasSuccessful = false;
                return;
            }
            _this.codeRequestWasSuccessful = true;
            console.log('Success. Authorized!');
            console.log(data);
            _this.loginService.setToken(data.accessToken);
            _this.userHolderService.setUser(data);
            _this.closeDialog();
        });
    };
    LoginComponent.prototype.closeDialog = function () {
        this.dialogRef.close();
    };
    LoginComponent.prototype.ngOnInit = function () {
    };
    LoginComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-login',
            template: __webpack_require__(/*! ./login.component.html */ "./src/app/user-forms/login/login.component.html"),
            styles: [__webpack_require__(/*! ./login.component.less */ "./src/app/user-forms/login/login.component.less")]
        }),
        __param(4, Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Inject"])(_angular_material__WEBPACK_IMPORTED_MODULE_4__["MAT_DIALOG_DATA"])),
        __metadata("design:paramtypes", [_login_service__WEBPACK_IMPORTED_MODULE_1__["LoginService"], _angular_router__WEBPACK_IMPORTED_MODULE_2__["Router"], _user_holder_service__WEBPACK_IMPORTED_MODULE_3__["UserHolderService"],
            _angular_material__WEBPACK_IMPORTED_MODULE_4__["MatDialogRef"], Object])
    ], LoginComponent);
    return LoginComponent;
}());



/***/ }),

/***/ "./src/app/user-holder.service.ts":
/*!****************************************!*\
  !*** ./src/app/user-holder.service.ts ***!
  \****************************************/
/*! exports provided: UserHolderService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UserHolderService", function() { return UserHolderService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var UserHolderService = /** @class */ (function () {
    function UserHolderService() {
        this.user = null;
    }
    UserHolderService.prototype.setUser = function (user) {
        this.user = user;
        console.log('I got the user ' + user);
    };
    UserHolderService.prototype.getUser = function () {
        return this.user;
    };
    UserHolderService.prototype.removeUser = function () {
        this.user = null;
    };
    UserHolderService.prototype.isLoggedIn = function () {
        return this.user != null;
    };
    UserHolderService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [])
    ], UserHolderService);
    return UserHolderService;
}());



/***/ }),

/***/ "./src/app/user-profile/user-profile.component.html":
/*!**********************************************************!*\
  !*** ./src/app/user-profile/user-profile.component.html ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"container user-profile-page\">\n  <div class=\"row\">\n    <div class=\"col-md-3 \">\n      <div class=\"list-group \">\n        <button (click)=\"changeActiveBtn(0)\" [ngClass]=\"{'active':isClicked[0]}\"\n                class=\"list-group-item list-group-item-action\">Dashboard\n        </button>\n        <button (click)=\"changeActiveBtn(1)\" [ngClass]=\"{'active':isClicked[1]}\"\n                class=\"list-group-item list-group-item-action \">User Management\n        </button>\n        <!--<button (click)=\"changeActiveBtn(2)\" [ngClass]=\"{'active':isClicked[2]}\" class=\"list-group-item list-group-item-action\">Used</button>-->\n        <!--<button (click)=\"changeActiveBtn(3)\" [ngClass]=\"{'active':isClicked[3]}\" class=\"list-group-item list-group-item-action\">Enquiry</button>-->\n        <!--<button (click)=\"changeActiveBtn(4)\" [ngClass]=\"{'active':isClicked[4]}\" class=\"list-group-item list-group-item-action\">Dealer</button>-->\n        <!--<button (click)=\"changeActiveBtn(5)\" [ngClass]=\"{'active':isClicked[5]}\" class=\"list-group-item list-group-item-action\">Media</button>-->\n        <!--<button (click)=\"changeActiveBtn(6)\" [ngClass]=\"{'active':isClicked[6]}\" class=\"list-group-item list-group-item-action\">Post</button>-->\n        <!--<button (click)=\"changeActiveBtn(7)\" [ngClass]=\"{'active':isClicked[7]}\" class=\"list-group-item list-group-item-action\">Category</button>-->\n        <!--<button (click)=\"changeActiveBtn(8)\" [ngClass]=\"{'active':isClicked[8]}\" class=\"list-group-item list-group-item-action\">New</button>-->\n        <!--<button (click)=\"changeActiveBtn(9)\" [ngClass]=\"{'active':isClicked[9]}\" class=\"list-group-item list-group-item-action\">Comments</button>-->\n        <!--<button (click)=\"changeActiveBtn(10)\" [ngClass]=\"{'active':isClicked[10]}\" class=\"list-group-item list-group-item-action\">Appearance</button>-->\n        <!--<button (click)=\"changeActiveBtn(11)\" [ngClass]=\"{'active':isClicked[11]}\" class=\"list-group-item list-group-item-action\">Reports</button>-->\n        <!--<button (click)=\"changeActiveBtn(12)\" [ngClass]=\"{'active':isClicked[12]}\" class=\"list-group-item list-group-item-action\">Settings</button>-->\n\n\n      </div>\n    </div>\n    <div class=\"col-md-9\" *ngIf=\"isClicked[0]\">\n      <div class=\"card\">\n        <div class=\"card-body\">\n          <div class=\"row\">\n            <div class=\"col-md-12\">\n              <h4>Let's get to know you!</h4>\n              <hr>\n            </div>\n          </div>\n          <div class=\"row\">\n            <div class=\"col-md-12\">\n              <form (submit)=\"editProfile(postForm)\" #postForm=\"ngForm\">\n                <div class=\"form-group row\">\n                  <mat-form-field class=\"edit-profile-input\" appearance=\"outline\">\n                    <mat-label>Email</mat-label>\n                    <!--<input matInput placeholder=\"Placeholder\">-->\n                    <input matInput ngModel name=\"email\" required #email=\"ngModel\"\n                           id=\"email\" name=\"email\" placeholder=\"ex@ample.com\" class=\"form-control here\"\n                           required=\"required\" type=\"email\">\n                    <mat-icon matSuffix>email</mat-icon>\n                    <mat-hint>ex@ample.com</mat-hint>\n                  </mat-form-field>\n                  <!--<label for=\"email\" class=\"col-4 col-form-label\">Email*</label>-->\n                  <!--<div class=\"col-8\">-->\n                  <!--<mat-form-field>-->\n                  <!--<input ngModel name=\"email\" required #email=\"ngModel\"-->\n                  <!--id=\"email\" name=\"email\" placeholder=\"ex@ample.com\" class=\"form-control here\" required=\"required\" type=\"email\">-->\n                  <!--</mat-form-field>-->\n                  <!--</div>-->\n                </div>\n                <div class=\"form-group row\">\n                  <mat-form-field class=\"edit-profile-input\" appearance=\"outline\">\n                    <mat-label>First Name</mat-label>\n                    <!--<input matInput placeholder=\"Placeholder\">-->\n                    <input matInput ngModel name=\"fname\" #fname=\"ngModel\"\n                           id=\"fname\" name=\"fname\" placeholder=\"First Name\" class=\"form-control here\" type=\"text\">\n                    <mat-icon matSuffix>person</mat-icon>\n                    <mat-hint>John</mat-hint>\n                  </mat-form-field>\n                </div>\n                <div class=\"form-group row\">\n                  <mat-form-field class=\"edit-profile-input\" appearance=\"outline\">\n                    <mat-label>Last Name</mat-label>\n                    <!--<input matInput placeholder=\"Placeholder\">-->\n                    <input matInput ngModel name=\"lname\" #lname=\"ngModel\"\n                           id=\"lname\" name=\"lname\" placeholder=\"Last Name\" class=\"form-control here\" type=\"text\">\n                    <mat-icon matSuffix>people</mat-icon>\n                    <mat-hint>Doe</mat-hint>\n                  </mat-form-field>\n                </div>\n                <div class=\"form-group row\">\n                  <mat-form-field class=\"edit-profile-input\" appearance=\"outline\">\n                    <mat-label>About me</mat-label>\n                    <!--<input matInput placeholder=\"Placeholder\">-->\n                    <textarea matInput ngModel name=\"publicinfo\" #publicinfo=\"ngModel\"\n                              id=\"publicinfo\" name=\"publicinfo\" cols=\"40\" rows=\"4\" class=\"form-control\"></textarea>\n                    <mat-icon matSuffix>content_paste</mat-icon>\n                    <mat-hint></mat-hint>\n                  </mat-form-field>\n                </div>\n                <div class=\"form-group row\">\n                  <mat-form-field class=\"edit-profile-input\" appearance=\"outline\">\n                    <mat-label>Country</mat-label>\n                    <!--<input matInput placeholder=\"Placeholder\">-->\n                    <input matInput ngModel name=\"country\" #country=\"ngModel\"\n                           id=\"country\" name=\"country\" placeholder=\"Wonderland\" class=\"form-control here\" type=\"text\">\n                    <mat-icon matSuffix>not_listed_location</mat-icon>\n                    <mat-hint></mat-hint>\n                  </mat-form-field>\n                </div>\n                <div class=\"form-group row\">\n                    <label for=\"owner\" class=\"col-4 col-form-label\">Do you own a restaurant/bar?</label>\n                    <div class=\"col-1\">\n                      <input ngModel name=\"owner\" #owner=\"ngModel\"\n                             id=\"owner\" name=\"owner\" style=\"width: 30px; height: 30px\" class=\"form-control here\"\n                             type=\"checkbox\">\n\n                      <!--<mat-form-field class=\"edit-profile-input\" appearance=\"outline\">-->\n                      <!--<mat-label>Owner?</mat-label>-->\n                      <!--&lt;!&ndash;<input matInput placeholder=\"Placeholder\">&ndash;&gt;-->\n                      <!--<input matInput ngModel name=\"owner\" #owner=\"ngModel\"-->\n                      <!--id=\"owner\" name=\"owner\" style=\"width: 30px; height: 30px\" class=\"form-control here\" type=\"checkbox\">-->\n                      <!--<mat-icon matSuffix>more_vert</mat-icon>-->\n                      <!--<mat-hint></mat-hint>-->\n                  <!--</mat-form-field>-->\n                </div>\n                </div>\n                <div class=\"form-group row\">\n                  <div class=\"offset-4 col-4\">\n                    <!--<button type=\"submit\" class=\"btn btn-primary\">Update My Profile</button>-->\n                    <button\n                      mat-raised-button\n                      color=\"primary\"\n                      type=\"submit\">Update Profile\n                    </button>\n                  </div>\n                  <br/>\n                  <div class=\"col-4\">\n                    <button class=\"btn btn-default\">Skip</button>\n                  </div>\n                </div>\n              </form>\n            </div>\n          </div>\n\n        </div>\n      </div>\n    </div>\n\n\n    <div class=\"col-md-9\" *ngIf=\"isClicked[1]\">\n      <div class=\"card\">\n        <div class=\"card-body\">\n          <div class=\"row\">\n            <div class=\"col-md-12\">\n              <h4>More options</h4>\n              <hr>\n            </div>\n          </div>\n          <div class=\"row\">\n            <div class=\"col-md-12\">\n\n              Some text\n\n            </div>\n          </div>\n\n        </div>\n      </div>\n    </div>\n\n\n  </div>\n</div>\n"

/***/ }),

/***/ "./src/app/user-profile/user-profile.component.less":
/*!**********************************************************!*\
  !*** ./src/app/user-profile/user-profile.component.less ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".edit-profile-input {\n  width: 80%;\n  margin: auto;\n}\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIi9ob21lL2VyYW4vV2Vic3Rvcm1Qcm9qZWN0cy93OGVyL3dlYi9zcmMvYXBwL3VzZXItcHJvZmlsZS91c2VyLXByb2ZpbGUuY29tcG9uZW50Lmxlc3MiLCJzcmMvYXBwL3VzZXItcHJvZmlsZS91c2VyLXByb2ZpbGUuY29tcG9uZW50Lmxlc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7RUFDRSxXQUFBO0VBQ0EsYUFBQTtDQ0NEIiwiZmlsZSI6InNyYy9hcHAvdXNlci1wcm9maWxlL3VzZXItcHJvZmlsZS5jb21wb25lbnQubGVzcyIsInNvdXJjZXNDb250ZW50IjpbIi5lZGl0LXByb2ZpbGUtaW5wdXQge1xuICB3aWR0aDogODAlO1xuICBtYXJnaW46IGF1dG87XG59XG4iLCIuZWRpdC1wcm9maWxlLWlucHV0IHtcbiAgd2lkdGg6IDgwJTtcbiAgbWFyZ2luOiBhdXRvO1xufVxuIl19 */"

/***/ }),

/***/ "./src/app/user-profile/user-profile.component.ts":
/*!********************************************************!*\
  !*** ./src/app/user-profile/user-profile.component.ts ***!
  \********************************************************/
/*! exports provided: UserProfileComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UserProfileComponent", function() { return UserProfileComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _user_updates_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./user-updates.service */ "./src/app/user-profile/user-updates.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var UserProfileComponent = /** @class */ (function () {
    function UserProfileComponent(userUpdatesService) {
        this.userUpdatesService = userUpdatesService;
        this.numOfSidenavBtns = 13;
        this.isClicked = new Array(this.numOfSidenavBtns);
        this.oldActive = 0;
    }
    UserProfileComponent.prototype.changeActiveBtn = function (newActive) {
        this.isClicked[this.oldActive] = false;
        this.isClicked[newActive] = true;
        this.oldActive = newActive;
    };
    UserProfileComponent.prototype.editProfile = function (form) {
        if (form.invalid) {
            console.log('Invalid form');
            return;
        }
        var val = form.value;
        var user = {
            first_name: val.fname,
            last_name: val.lname,
            email: val.email,
            about_me: val.public_info,
            is_admin: val.owner
        };
        // const user: User = new User();
        // {
        //   first_name: val.fname,
        //   last_name: val.lname,
        //   email: val.email,
        //   about_me: val.public_info,
        //   is_admin: val.owner
        // };
        // user.first_name = val.fname;
        // user.last_name = val.lname;
        // user.email = val.email;
        // user.about_me = val.public_info;
        // user.is_admin = val.owner;
        this.userUpdatesService.editProfile(user)
            .subscribe(function (data) {
            console.log(typeof (data));
            if (data instanceof Array && data.length == 0) {
                console.log('No data received');
                return;
            }
            console.log('The user got a code');
        });
    };
    UserProfileComponent.prototype.ngOnInit = function () {
        this.isClicked[0] = true;
    };
    UserProfileComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-user-profile',
            template: __webpack_require__(/*! ./user-profile.component.html */ "./src/app/user-profile/user-profile.component.html"),
            styles: [__webpack_require__(/*! ./user-profile.component.less */ "./src/app/user-profile/user-profile.component.less")]
        }),
        __metadata("design:paramtypes", [_user_updates_service__WEBPACK_IMPORTED_MODULE_1__["UserUpdatesService"]])
    ], UserProfileComponent);
    return UserProfileComponent;
}());



/***/ }),

/***/ "./src/app/user-profile/user-updates.service.ts":
/*!******************************************************!*\
  !*** ./src/app/user-profile/user-updates.service.ts ***!
  \******************************************************/
/*! exports provided: UserUpdatesService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UserUpdatesService", function() { return UserUpdatesService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _http_error_handler_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../http-error-handler.service */ "./src/app/http-error-handler.service.ts");
/* harmony import */ var rxjs_internal_operators__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! rxjs/internal/operators */ "./node_modules/rxjs/internal/operators/index.js");
/* harmony import */ var rxjs_internal_operators__WEBPACK_IMPORTED_MODULE_3___default = /*#__PURE__*/__webpack_require__.n(rxjs_internal_operators__WEBPACK_IMPORTED_MODULE_3__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var UserUpdatesService = /** @class */ (function () {
    function UserUpdatesService(http, httpErrorHandler) {
        this.http = http;
        this.httpOptions = {};
        this.handleError = httpErrorHandler.createHandleError('sService');
        this.httpOptions = {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpHeaders"]({
                'Content-Type': 'application/json'
            })
        };
    }
    UserUpdatesService.prototype.editProfile = function (user) {
        console.log('Called editProfile');
        return this.http.post('/user/edit-profile', {
            first_name: user.first_name,
            last_name: user.last_name,
            // user.birthday
            gender: user.gender,
            about_me: user.about_me,
            email: user.email,
            // address: user.address
            country: user.country,
            is_admin: user.is_admin
        }, { headers: this.httpOptions })
            .pipe(Object(rxjs_internal_operators__WEBPACK_IMPORTED_MODULE_3__["catchError"])(this.handleError('login', [])));
    };
    UserUpdatesService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [_angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpClient"], _http_error_handler_service__WEBPACK_IMPORTED_MODULE_2__["HttpErrorHandler"]])
    ], UserUpdatesService);
    return UserUpdatesService;
}());



/***/ }),

/***/ "./src/environments/environment.ts":
/*!*****************************************!*\
  !*** ./src/environments/environment.ts ***!
  \*****************************************/
/*! exports provided: environment */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "environment", function() { return environment; });
// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
var environment = {
    production: false
};
/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.


/***/ }),

/***/ "./src/main.ts":
/*!*********************!*\
  !*** ./src/main.ts ***!
  \*********************/
/*! no exports provided */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser-dynamic */ "./node_modules/@angular/platform-browser-dynamic/fesm5/platform-browser-dynamic.js");
/* harmony import */ var _app_app_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app/app.module */ "./src/app/app.module.ts");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./environments/environment */ "./src/environments/environment.ts");




if (_environments_environment__WEBPACK_IMPORTED_MODULE_3__["environment"].production) {
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["enableProdMode"])();
}
Object(_angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__["platformBrowserDynamic"])().bootstrapModule(_app_app_module__WEBPACK_IMPORTED_MODULE_2__["AppModule"])
    .catch(function (err) { return console.error(err); });


/***/ }),

/***/ 0:
/*!***************************!*\
  !*** multi ./src/main.ts ***!
  \***************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! /home/eran/WebstormProjects/w8er/web/src/main.ts */"./src/main.ts");


/***/ })

},[[0,"runtime","vendor"]]]);
//# sourceMappingURL=main.js.map