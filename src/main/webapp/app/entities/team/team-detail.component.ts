import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Team } from './team.model';
import { TeamService } from './team.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-team-detail',
    templateUrl: './team-detail.component.html'
})
export class TeamDetailComponent implements OnInit, OnDestroy {

    currentAccount: any;
    team: Team;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private teamService: TeamService,
        private principal: Principal,
        private route: ActivatedRoute
    ) {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTeams();
    }

    load(id) {
        this.teamService.find(id).subscribe((team) => {
            this.team = team;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTeams() {
        this.eventSubscriber = this.eventManager.subscribe(
            'teamListModification',
            (response) => this.load(this.team.id)
        );
    }
}
