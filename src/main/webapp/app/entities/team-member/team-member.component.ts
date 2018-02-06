import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { TeamMember } from './team-member.model';
import { TeamMemberService } from './team-member.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-team-member',
    templateUrl: './team-member.component.html'
})
export class TeamMemberComponent implements OnInit, OnDestroy {

    teamMembers: TeamMember[];
    currentAccount: any;
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
    reverse: any;
    previousPage: any;

    teamId: string;
    teamName: string;

    params: {[key: string]: any};

    constructor(
        private teamMemberService: TeamMemberService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;

        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    loadAll() {
        this.teamMemberService.query({
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
        this.router.navigate(['/team-member'], {queryParams:
            {
                teamId: this.teamId,
                teamName: this.teamName,
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/team-member', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }

    ngOnInit() {
        this.activatedRoute.queryParams
        // .filter((params) => params.team)
        .subscribe((params) => {
          this.teamId = params.teamId;
          this.teamName = params.teamName;
          this.params = params;
          this.loadAll();
        });

        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTeamMembers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TeamMember) {
        return item.id;
    }
    registerChangeInTeamMembers() {
        this.eventSubscriber = this.eventManager.subscribe('teamMemberListModification', (response) => this.loadAll());
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
        this.teamMembers = data;
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
