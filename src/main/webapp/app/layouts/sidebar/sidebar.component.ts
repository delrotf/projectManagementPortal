import { VIEW_TEAMS_My, VIEW_TEAMS_ALL, VIEW_TEAMS_IM_MEMBER_OF, VIEW_TEAMS_BROWSE_MORE,
    VIEW_TEAMS_USERS_MEMBER_OF,
    VIEW_TEAMS_USERS_MEMBER_OF_MY,
    VIEW_TEAMS_USERS_HEADED} from './../../shared/constants/page.constants';
import { UserInfo } from './../../entities/user-info/user-info.model';
import { UserInfoService } from './../../entities/user-info/user-info.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef, NgbDropdownConfig } from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs/Subscription';

import { ProfileService } from '../profiles/profile.service';
import { Principal, LoginModalService, LoginService, ResponseWrapper } from '../../shared';

import { VERSION } from '../../app.constants';
import { JhiEventManager } from 'ng-jhipster';

@Component({
    selector: 'jhi-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: [
        'sidebar.css'
    ],
    providers: [NgbDropdownConfig]
})
export class SidebarComponent implements OnInit, OnDestroy {
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;
    account: Account;
    isAdmin: boolean;
    eventSubscriber: Subscription;
    logoutEventSubscriber: Subscription;

    userInfo: UserInfo;

    allTeams = VIEW_TEAMS_ALL;
    myTeams = VIEW_TEAMS_My;
    teamsImMemberOf = VIEW_TEAMS_IM_MEMBER_OF;
    browseMoreTeams = VIEW_TEAMS_BROWSE_MORE;

    usersHeadedTeams = VIEW_TEAMS_USERS_HEADED;
    usersMemberOf = VIEW_TEAMS_USERS_MEMBER_OF;
    usersMemberOfMyTeams = VIEW_TEAMS_USERS_MEMBER_OF_MY;

    toggle = true;
    isProfileMenuOpen = false;
    isMyTeamsMenuOpen = false;
    expandedNavItem = this.toggle;

    constructor(
        private loginService: LoginService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private userInfoService: UserInfoService,
        private eventManager: JhiEventManager,
        private router: Router,
        private dropdownConfig: NgbDropdownConfig
    ) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
        });
    }

    ngOnInit() {
        this.isProfileMenuOpen = false;
        this.loadAll()
        this.eventSubscriber = this.eventManager.subscribe('authenticationSuccess', (response) => {
            this.loadAll()
        });
        this.profileService.getProfileInfo().then((profileInfo) => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });
    }

    loadAll() {
        this.principal.identity().then((account) => {
            this.account = account;
            this.userInfoService.query({
                query: JSON.stringify({userLogin: account.login})
            }).subscribe((res: ResponseWrapper) => {
                this.userInfo = res.json[0];
            });
        });

        this.principal.identity().then((account) => {
            this.account = account;
        });

        this.principal.hasAuthority('ROLE_ADMIN').then((value) => {
            this.isAdmin = value;
        });
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    login() {
        this.isProfileMenuOpen = false;
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
    toggleSidebar() {
        this.toggle = !this.toggle;
        this.eventManager.broadcast({
            name: 'toggleSidebar',
            content: 'Sidebar is clicked'
        });
    }

    toggleProfileMenu(event) {
        this.isProfileMenuOpen = event;
    }

    toggleMyTeamsMenu(event) {
        this.isMyTeamsMenuOpen = event;
    }

    getProfileMenuWidth() {
        if (this.isProfileMenuOpen) {
            return '218px';
        } else {
            return '54px';
        }
    }

    getMyTeamsMenuWidth() {
        if (this.isMyTeamsMenuOpen) {
            return '218px';
        } else {
            return '54px';
        }
    }

    getMenuAutoClose() {
        if (this.toggle) {
            return 'outside';
        } else {
            return true;
        }
    }

    getBarsMargin() {
        if (this.toggle) {
            return '200px';
        } else {
            return '8px';
        }
    }
    openDropdown(myDrop) {
        if (!this.toggle) {
            myDrop.open();
        }
    }
    closeDropdown(myDrop) {
        if (!this.toggle) {
            myDrop.close();
        }
    }
}
