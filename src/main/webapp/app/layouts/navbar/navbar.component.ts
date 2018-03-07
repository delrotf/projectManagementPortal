import { VIEW_TEAMS_My, VIEW_TEAMS_ALL, VIEW_TEAMS_IM_MEMBER_OF, VIEW_TEAMS_BROWSE_MORE,
    VIEW_TEAMS_USERS_HEADED, VIEW_TEAMS_USERS_MEMBER_OF,
    VIEW_TEAMS_USERS_MEMBER_OF_MY } from './../../shared/constants/page.constants';
import { UserInfo } from './../../entities/user-info/user-info.model';
import { UserInfoService } from './../../entities/user-info/user-info.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { ProfileService } from '../profiles/profile.service';
import { Principal, LoginModalService, LoginService, ResponseWrapper } from '../../shared';

import { VERSION } from '../../app.constants';
import { JhiEventManager } from 'ng-jhipster';

@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: [
        'navbar.css'
    ]
})
export class NavbarComponent implements OnInit {
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;
    account: Account;
    isAdmin: boolean;
    userInfo: UserInfo;

    allTeams = VIEW_TEAMS_ALL;
    myTeams = VIEW_TEAMS_My;
    teamsImMemberOf = VIEW_TEAMS_IM_MEMBER_OF;
    browseMoreTeams = VIEW_TEAMS_BROWSE_MORE;

    usersHeadedTeams = VIEW_TEAMS_USERS_HEADED;
    usersMemberOf = VIEW_TEAMS_USERS_MEMBER_OF;
    usersMemberOfMyTeams = VIEW_TEAMS_USERS_MEMBER_OF_MY;

    constructor(
        private loginService: LoginService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private userInfoService: UserInfoService,
        private eventManager: JhiEventManager,
        private router: Router
    ) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
        // this.principal.identity().then((account) => {
        //     this.account = account;
        // });
    }

    ngOnInit() {
        this.profileService.getProfileInfo().then((profileInfo) => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });
        this.principal.identity().then((account) => {
            this.account = account;
            // find the userInfo of current login.
            if (account) {
                this.userInfoService.query({query: JSON.stringify({userLogin: account.login})})
                .subscribe((res: ResponseWrapper) => {
                    if (res.json.length) {
                        this.userInfo = res.json[0];
                        this.account.imageURL = this.userInfo.image ? 'data:' + this.userInfo.imageContentType + ';base64,' + this.userInfo.image : null;
                    // } else {
                    //     this.userInfo = new UserInfo();
                    }
                });
            }
        });

        this.principal.hasAuthority('ROLE_ADMIN').then((value) => {
            this.isAdmin = value;
        });

        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
                this.account.imageURL = this.userInfo && this.userInfo.image ? 'data:' + this.userInfo.imageContentType + ';base64,' + this.userInfo.image : null;
            });
            this.principal.hasAuthority('ROLE_ADMIN').then((value) => {
                this.isAdmin = value;
            });
           });
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.eventManager.broadcast({
            name: 'logoutSuccess',
            content: 'Sending Logout Success'
        });

        this.router.navigate(['']);
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        // return this.isAuthenticated() ? this.principal.getImageUrl() : null;
        return this.isAuthenticated() && this.account ? this.account.imageURL : null;
        // return this.isAuthenticated() && this.userInfo.image ? 'data:' + this.userInfo.imageContentType + ';base64,' + this.userInfo.image : null;
    }
}
