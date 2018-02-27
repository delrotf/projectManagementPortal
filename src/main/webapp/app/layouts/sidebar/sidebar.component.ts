import { UserInfo } from './../../entities/user-info/user-info.model';
import { UserInfoService } from './../../entities/user-info/user-info.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs/Subscription';

import { ProfileService } from '../profiles/profile.service';
import { Principal, LoginModalService, LoginService, ResponseWrapper } from '../../shared';

import { VERSION } from '../../app.constants';
import { JhiEventManager } from 'ng-jhipster';

import {NgbAccordionConfig} from '@ng-bootstrap/ng-bootstrap';
@Component({
    selector: 'jhi-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: [
        'sidebar.css'
    ],
    providers: [NgbAccordionConfig]
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

    constructor(
        private loginService: LoginService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private userInfoService: UserInfoService,
        private eventManager: JhiEventManager,
        private router: Router,
        private config: NgbAccordionConfig
    ) {
        config.closeOthers = true;
        config.type = 'info';
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
        this.principal.identity().then((account) => {
            this.account = account;
        });
    }

    ngOnInit() {
        this.eventSubscriber = this.eventManager.subscribe('authenticationSuccess', (response) => this.loadAll());
        this.profileService.getProfileInfo().then((profileInfo) => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });
        this.principal.identity().then((account) => {
            this.account = account;
        });

        this.principal.hasAuthority('ROLE_ADMIN').then((value) => {
            this.isAdmin = value;
        });
    }

    loadAll() {
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
}
