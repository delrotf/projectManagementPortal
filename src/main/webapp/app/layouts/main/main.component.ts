import { Principal } from './../../shared/auth/principal.service';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRouteSnapshot, NavigationEnd } from '@angular/router';

import { Title } from '@angular/platform-browser';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

@Component({
    selector: 'jhi-main',
    templateUrl: './main.component.html',
    styleUrls: [
        'main.css'
    ]
})
export class JhiMainComponent implements OnInit {
    eventSubscriber: Subscription;
    isAuthenticatedAlready: boolean;

    constructor(
        private titleService: Title,
        private router: Router,
        private principal: Principal,
        private eventManager: JhiEventManager,
    ) {}

    private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
        let title: string = (routeSnapshot.data && routeSnapshot.data['pageTitle']) ? routeSnapshot.data['pageTitle'] : 'projectManagementPortalApp';
        if (routeSnapshot.firstChild) {
            title = this.getPageTitle(routeSnapshot.firstChild) || title;
        }
        return title;
    }

    ngOnInit() {
        this.eventSubscriber = this.eventManager.subscribe('authenticationSuccess', (response) => this.loadAll());
        this.eventSubscriber = this.eventManager.subscribe('logoutSuccess', (response) => this.loadAll());
        this.router.events.subscribe((event) => {
            if (event instanceof NavigationEnd) {
                this.titleService.setTitle(this.getPageTitle(this.router.routerState.snapshot.root));
            }
        });
        this.loadAll();
    }

    loadAll() {
        // just to refresh the component
        this.isAuthenticatedAlready = this.principal.isAuthenticated();
    }
    isAuthenticated() {
        return this.principal.isAuthenticated();
    }
}
