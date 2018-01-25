import { ActivatedRoute } from '@angular/router';
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
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;

    teamId: string;
    teamName: string;

    params: {[key: string]: any};

    constructor(
        private teamMemberService: TeamMemberService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        private route: ActivatedRoute
    ) {
        this.teamMembers = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.teamMemberService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort(),
            query: JSON.stringify(this.params)
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    reset() {
        this.page = 0;
        this.teamMembers = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    ngOnInit() {
        this.route.queryParams
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
        this.eventSubscriber = this.eventManager.subscribe('teamMemberListModification', (response) => this.reset());
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
        for (let i = 0; i < data.length; i++) {
            this.teamMembers.push(data[i]);
        }
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
