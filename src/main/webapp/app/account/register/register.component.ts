// import { My_ACTIVE_TEAMS } from './../../shared/constants/screen.constants';
import { Principal } from './../../shared/auth/principal.service';
import { Component, OnInit, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { Register } from './register.service';
import { LoginModalService, EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from '../../shared';
import { JhiEventManager } from 'ng-jhipster';
import { VIEW_TEAMS_My, VIEW_TEAMS_ALL, VIEW_TEAMS_IM_MEMBER_OF, VIEW_TEAMS_BROWSE_MORE,
    VIEW_TEAMS_USERS_MEMBER_OF,
    VIEW_TEAMS_USERS_MEMBER_OF_MY,
    VIEW_TEAMS_USERS_HEADED} from './../../shared/constants/page.constants';

@Component({
    selector: 'jhi-register',
    templateUrl: './register.component.html',
    styleUrls: [
        'register.css'
    ]
})
export class RegisterComponent implements OnInit, AfterViewInit {

    confirmPassword: string;
    doNotMatch: string;
    error: string;
    errorEmailExists: string;
    errorUserExists: string;
    registerAccount: any;
    success: boolean;
    modalRef: NgbModalRef;

    account: Account;
    isAdmin: boolean;

    myActiveTeams: any;

    allTeams = VIEW_TEAMS_ALL;
    myTeams = VIEW_TEAMS_My;
    teamsImMemberOf = VIEW_TEAMS_IM_MEMBER_OF;
    browseMoreTeams = VIEW_TEAMS_BROWSE_MORE;

    usersHeadedTeams = VIEW_TEAMS_USERS_HEADED;
    usersMemberOf = VIEW_TEAMS_USERS_MEMBER_OF;
    usersMemberOfMyTeams = VIEW_TEAMS_USERS_MEMBER_OF_MY;

    constructor(
        private loginModalService: LoginModalService,
        private registerService: Register,
        private elementRef: ElementRef,
        private principal: Principal,
        private renderer: Renderer,
        private eventManager: JhiEventManager
    ) {
        this.principal.identity().then((account) => {
            this.account = account;
        });
    }

    ngOnInit() {
        // this.myActiveTeams = My_ACTIVE_TEAMS;
        this.success = false;
        this.registerAccount = {};
        this.principal.identity().then((account) => {
            this.account = account;
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
            });
            this.principal.hasAuthority('ROLE_ADMIN').then((value) => {
                this.isAdmin = value;
            });
        });
    }

    ngAfterViewInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        if (!this.isAuthenticated()) {
            this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#login'), 'focus', []);
        }
    }

    register() {
        if (this.registerAccount.password !== this.confirmPassword) {
            this.doNotMatch = 'ERROR';
        } else {
            this.doNotMatch = null;
            this.error = null;
            this.errorUserExists = null;
            this.errorEmailExists = null;
            this.registerAccount.langKey = 'en';
            this.registerService.save(this.registerAccount).subscribe(() => {
                this.success = true;
            }, (response) => this.processError(response));
        }
    }

    openLogin() {
        this.modalRef = this.loginModalService.open();
    }

    private processError(response) {
        this.success = null;
        if (response.status === 400 && response.json().type === LOGIN_ALREADY_USED_TYPE) {
            this.errorUserExists = 'ERROR';
        } else if (response.status === 400 && response.json().type === EMAIL_ALREADY_USED_TYPE) {
            this.errorEmailExists = 'ERROR';
        } else {
            this.error = 'ERROR';
        }
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }
}
