import { Principal } from './../../shared/auth/principal.service';
import { VIEW_TEAMS_My, VIEW_TEAMS_ALL, VIEW_TEAMS_BROWSE_MORE,
    ACTION_JOIN_TEAM, ACTION_ADD_USERS_TO_TEAM, ACTION_TEAMS_TO_USER } from './../../shared/constants/page.constants';
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
    templateUrl: './team-member-dialog.component.html',
    styleUrls: [
        'team-member.css'
    ]
})
export class TeamMemberDialogComponent implements OnInit {

    currentAccount: any;
    teamMember: TeamMember;
    isSaving: boolean;

    viewId: string;
    myTeams = VIEW_TEAMS_My;
    allTeams = VIEW_TEAMS_ALL;
    browseMoreTeams = VIEW_TEAMS_BROWSE_MORE;

    action: string;
    joinTeam = ACTION_JOIN_TEAM;
    addUsersToTeam = ACTION_ADD_USERS_TO_TEAM;
    addTeamsToUser = ACTION_TEAMS_TO_USER;

    userInfos: UserInfo[];

    teams: Team[];

    params: any;
    filter: string;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private teamMemberService: TeamMemberService,
        private userInfoService: UserInfoService,
        private teamService: TeamService,
        private principal: Principal,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        private router: Router,
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });

        this.route.queryParams
        // .filter((params) => params.team)
        .subscribe((params) => {
            this.params = params;
            this.viewId = params.viewId;
            this.action = params.action;

            const userLogin = params.userLogin;
            const userInfoId = params.userInfoId;
            const teamId = params.teamId;

            // join team.
            if (this.action === this.joinTeam) {
                this.teamMember.teamId = params.teamId;
                this.userInfoService.query({
                    query: JSON.stringify({userLogin})
                }).subscribe((res: ResponseWrapper) => {
                        this.teamMember.userInfoId = res.json[0].id;
                    }, (res: ResponseWrapper) => this.onError(res.json));
            } else if (this.action === this.addUsersToTeam && teamId) { // for add users to teams. if has team, we need its userInfos selection
                this.teamMember.teamId = teamId;
                this.userInfoService.query({
                    query: JSON.stringify(params)
                }).subscribe((res: ResponseWrapper) => {
                        this.userInfos = res.json;
                    }, (res: ResponseWrapper) => this.onError(res.json));
            } else if (this.action === this.addTeamsToUser && userInfoId) { // for add teams to users. If has userInfo, we need its teams selection
                this.teamMember.userInfoId = userInfoId;
                this.teamService.query({query: JSON.stringify(params)})
                    .subscribe((res: ResponseWrapper) => {
                        this.teams = res.json;
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
    }

    private subscribeToSaveResponse(result: Observable<TeamMember>) {
        result.subscribe((res: TeamMember) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: TeamMember) {
        this.eventManager.broadcast({ name: 'teamMemberListModification', content: 'OK'});
        this.isSaving = false;
        if (this.router.url.startsWith('/team')) {
        this.router.navigate(['/team'], {
                queryParams: {
                    inactive: this.params.inactive,
                    viewId: this.params.viewId,
                    action: this.params.action,
                    saved: result.id,
                    teamId: result.teamId,
                    teamName: this.params.teamName,
                    teamHeadUserLogin: this.params.teamHeadUserLogin,
                    userInfoId: this.params.userInfoId,
                    userId: this.params.userId,
                    userLogin: this.params.userLogin
                }
            });
            if (this.action === this.joinTeam) {
                this.activeModal.dismiss('cancel');
            }
        } else if (this.params.userInfoId) {
            this.router.navigate(['/user-info'], {
                queryParams: {
                    action: this.params.action,
                    // active: this.params.active,
                    saved: result.id,
                    userInfoId: this.params.userInfoId,
                    userId: this.params.userId,
                    userLogin: this.params.userLogin
                }
            });
        }

    //     if (this.hasUserLogin && !this.hasUserInfo) {
    //         this.activeModal.dismiss(result);
    //     }
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
