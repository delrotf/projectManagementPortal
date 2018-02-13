import { UserInfo } from './../user-info/user-info.model';
import { UserInfoService } from './../user-info/user-info.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TeamMember } from './team-member.model';
import { TeamMemberPopupService } from './team-member-popup.service';
import { TeamMemberService } from './team-member.service';
import { User, UserService } from '../../shared';
import { Team, TeamService } from '../team';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-team-member-dialog',
    templateUrl: './team-member-dialog.component.html'
})
export class TeamMemberDialogComponent implements OnInit {

    teamMember: TeamMember;
    isSaving: boolean;

    hasTeam: boolean;
    hasUserInfo: boolean;
    hasUserLogin: boolean;

    showOk: boolean;

    userInfos: UserInfo[];

    teams: Team[];

    params: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private teamMemberService: TeamMemberService,
        private userInfoService: UserInfoService,
        private teamService: TeamService,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        private router: Router,
    ) {
    }

    ngOnInit() {
        this.showOk = true;
        this.route.queryParams
        // .filter((params) => params.team)
        .subscribe((params) => {
            this.params = params;
            this.hasTeam = params.teamId ? true : false;
            this.hasUserInfo = params.userInfoId ? true : false;
            this.hasUserLogin = params.userLogin ? true : false;

            // adding self to a team.
            if (this.hasUserLogin && !this.hasUserInfo) {
                this.showOk = false;
                this.teamMember.teamId = params.teamId;
                this.userInfoService.query({
                    query: JSON.stringify({userLogin: this.params.userLogin})
                })
                    .subscribe((res: ResponseWrapper) => {
                        this.teamMember.userInfoId = res.json[0].id;
                    }, (res: ResponseWrapper) => this.onError(res.json));
            } else if (this.hasTeam) { // if has team, we need its userInfos
                this.teamMember.teamId = params.teamId;
                this.userInfoService.query({
                    query: JSON.stringify(params)
                })
                    .subscribe((res: ResponseWrapper) => {
                        this.userInfos = res.json;
                        this.showOk = this.userInfos && this.userInfos.length ? false : true;
                    }, (res: ResponseWrapper) => this.onError(res.json));
            } else if (this.hasUserInfo) { // If has userInfo, we need its team
                this.teamMember.userInfoId = params.userInfoId;
                this.teamService.query({query: JSON.stringify(params)})
                    .subscribe((res: ResponseWrapper) => {
                        this.teams = res.json;
                        this.showOk = this.teams && this.teams.length ? false : true;
                }, (res: ResponseWrapper) => this.onError(res.json));
            }
        });

        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.teamMember.updatedTime = new Date().toISOString();

        if (this.teamMember.id !== undefined) {
            this.subscribeToSaveResponse(
                this.teamMemberService.update(this.teamMember));
        } else {
            this.subscribeToSaveResponse(
                this.teamMemberService.create(this.teamMember));
        }
        // this.router.navigate(['/team'], {
        //     queryParams: {
        //         active: this.params.active,
        //         allOthers: this.params.allOthers,
        //     }
        // });
    }

    private subscribeToSaveResponse(result: Observable<TeamMember>) {
        result.subscribe((res: TeamMember) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: TeamMember) {
        this.eventManager.broadcast({ name: 'teamMemberListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserInfoById(index: number, item: UserInfo) {
        return item.id;
    }

    trackTeamById(index: number, item: Team) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-team-member-popup',
    template: ''
})
export class TeamMemberPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private teamMemberPopupService: TeamMemberPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.teamMemberPopupService
                    .open(TeamMemberDialogComponent as Component, params['id']);
            } else {
                this.teamMemberPopupService
                    .open(TeamMemberDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
