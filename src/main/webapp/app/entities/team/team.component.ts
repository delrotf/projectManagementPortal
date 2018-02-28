import { UserInfo } from './../user-info/user-info.model';
import { UserInfoService } from './../user-info/user-info.service';
import { TeamMember } from './../team-member/team-member.model';
import { TeamMemberService } from './../team-member/team-member.service';
// import { ADD_SELF_TO_TEAM, MANAGE_TEAM_MEMBERS, My_ACTIVE_TEAMS, My_INACTIVE_TEAMS, BROWSE_MORE_TEAMS } from './../../shared/constants/screen.constants';
import { Component, OnInit, OnDestroy, ViewChild, ElementRef, ViewContainerRef, TemplateRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Team } from './team.model';
import { TeamService } from './team.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-team',
    templateUrl: './team.component.html',
    styleUrls: [
        'team.css'
    ]
})
export class TeamComponent implements OnInit, OnDestroy {

currentAccount: any;
    teams: Team[];
    teamMembers: {[key: string]: TeamMember[]};
    isMember: {[key: string]: boolean};
    userInfo: UserInfo;
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

    params: {[key: string]: any};
    active: boolean;
    imMemberOf: boolean;
    userMemberOf: boolean;
    userLogin: string;
    allOthers: boolean;

    isTabular: boolean;

    constructor(
        private teamService: TeamService,
        private teamMemberService: TeamMemberService,
        private userInfoService: UserInfoService,
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
            this.imMemberOf = params.imMemberOf;
            this.userMemberOf = params.userMemberOf;
            this.userLogin = params.userLogin;
            this.allOthers = params.allOthers;

            if (this.params.userInfoId) {
                this.userInfoService.find(this.params.userInfoId).subscribe((userInfo) => {
                    this.userInfo = userInfo;
                });
            }

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
                imMemberOf: this.params.imMemberOf,
                userMemberOf: this.params.userMemberOf,
                userLogin: this.params.userLogin,
                allOthers: this.params.allOthers,
                headed: this.params.headed,
                teamHeadUserLogin: this.params.teamHeadUserLogin,
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
