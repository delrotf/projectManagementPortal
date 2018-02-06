// import { ADD_SELF_TO_TEAM, MANAGE_TEAM_MEMBERS, My_ACTIVE_TEAMS, My_INACTIVE_TEAMS, BROWSE_MORE_TEAMS } from './../../shared/constants/screen.constants';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Team } from './team.model';
import { TeamService } from './team.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-team',
    templateUrl: './team.component.html'
})
export class TeamComponent implements OnInit, OnDestroy {

currentAccount: any;
    teams: Team[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    isAdmin: boolean;
    userInfoId: string;

    params: {[key: string]: any};
    active: boolean;
    allOthers: boolean;

    // addSelfToTeam: any;
    // manageTeamMembers: any;
    // myActiveTeams: any;
    // myInactiveTeams: any;
    // browseMoreTeams: any;

    constructor(
        private teamService: TeamService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private dataUtils: JhiDataUtils,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });

        this.itemsPerPage = ITEMS_PER_PAGE;
        // this.addSelfToTeam = ADD_SELF_TO_TEAM;
        // this.manageTeamMembers = MANAGE_TEAM_MEMBERS;

        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
        this.activatedRoute.queryParams
        // .filter((params) => params.team)
        .subscribe((params) => {
            this.params = params;
            this.active = params.active;
            this.allOthers = params.allOthers;
            this.loadAll();
        });
    }

    loadAll() {
        this.teamService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort(),
            query: JSON.stringify(this.params)
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }
    transition() {
        this.router.navigate(['/team'], {queryParams:
            {
                active: this.params.active,
                allOthers: this.params.allOthers,
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/team', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }
    ngOnInit() {
        // this.addSelfToTeam = ADD_SELF_TO_TEAM;
        // this.manageTeamMembers = MANAGE_TEAM_MEMBERS;
        // this.myActiveTeams = My_ACTIVE_TEAMS;
        // this.myInactiveTeams = My_INACTIVE_TEAMS;
        // this.browseMoreTeams = BROWSE_MORE_TEAMS;

        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });

        this.principal.hasAuthority('ROLE_ADMIN').then((value) => {
            this.isAdmin = value;
        });

        this.registerChangeInTeams();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Team) {
        return item.id;
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInTeams() {
        this.eventSubscriber = this.eventManager.subscribe('teamListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.teams = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
