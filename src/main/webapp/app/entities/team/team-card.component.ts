import { UserInfo } from './../user-info/user-info.model';
import { UserInfoService } from './../user-info/user-info.service';
import { TeamMember } from './../team-member/team-member.model';
import { TeamMemberService } from './../team-member/team-member.service';
// import { ADD_SELF_TO_TEAM, MANAGE_TEAM_MEMBERS, My_ACTIVE_TEAMS, My_INACTIVE_TEAMS, BROWSE_MORE_TEAMS } from './../../shared/constants/screen.constants';
import { Component, OnInit, OnDestroy, ViewChild, ElementRef, ViewContainerRef, TemplateRef, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Team } from './team.model';
import { TeamService } from './team.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { Observable } from 'rxjs/Observable';

@Component({
    selector: 'jhi-team-card',
    templateUrl: './team-card.component.html',
    styleUrls: [
        'team.css'
    ]
})
export class TeamCardComponent implements OnInit {

    @Input() currentAccount: any;
    @Input() team: Team;
    teamMembers: TeamMember[];
    isMember: boolean;
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
    userInfoId: string;

    params: {[key: string]: any};
    active: boolean;
    allOthers: boolean;

    isTabular: boolean;

    popoverPlacement = 'top';

    clientX = 0;
    clientY = 0;
    saved: string;
    delete: boolean;
    isSaving: boolean;

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
    }

    getMembers(teamId: string) {
        this.isMember = false;
        this.teamMemberService.query({
            query: JSON.stringify({teamId})
        }).subscribe(
            (res: ResponseWrapper) => {
                this.teamMembers = res.json;

                this.teamMembers.forEach((teamMember) => {
                    if (teamMember.userInfoUserLogin === this.currentAccount.login) {
                        this.isMember = true;
                    }
                });
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    ngOnInit() {
        this.principal.hasAuthority('ROLE_ADMIN').then((value) => {
            this.isAdmin = value;
        });

        this.getMembers(this.team.id.toString());

        this.activatedRoute.queryParams
        // .filter((params) => params.team)
        .subscribe((params) => {
            this.params = params;
            this.active = params.active;
            this.allOthers = params.allOthers;
            if (this.team && params.saved && params.saved !== this.saved && this.team.id.toString() === params.teamId) {
                this.saved = params.saved;
                this.getMembers(this.team.id.toString());
            }
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    coordinates(event: MouseEvent): void {
        this.clientX = event.clientX;
        this.clientY = event.clientY;

        if (this.clientX < 200) {
            this.popoverPlacement = 'top-left';
        } else {
            this.popoverPlacement = 'top';
        }
    }

    closePopover(popover) {
        setTimeout(() => { popover.close() }, 200);
    }

    join(userLogin: string) {
        this.isSaving = true;
        if (this.params.allOthers) {
            this.delete = true;
        }
        const teamMember = new TeamMember();
        teamMember.updatedTime = new Date().toISOString();

        this.userInfoService.query({
            query: JSON.stringify({userLogin})
        }).subscribe((res: ResponseWrapper) => {
            teamMember.userInfoId = res.json[0].id;
            teamMember.teamId = this.team.id;
            this.subscribeToSaveResponse(
                this.teamMemberService.create(teamMember));
        }, (res: ResponseWrapper) => this.onError(res.json));
    }

    private subscribeToSaveResponse(result: Observable<TeamMember>) {
        result.subscribe((res: TeamMember) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: TeamMember) {
        this.eventManager.broadcast({ name: 'teamMemberListModification', content: 'OK'});
        this.isSaving = false;
        if (this.params.allOthers) {
            setTimeout(() => {
                this.router.navigate(['/team'], {
                    queryParams: {
                        active: this.params.active,
                        allOthers: this.params.allOthers,
                        headed: this.params.headed,
                        saved: result.id,
                        teamId: result.teamId,
                        teamName: this.params.teamName,
                        teamHeadUserLogin: this.params.teamHeadUserLogin,
                        userInfoId: this.params.userInfoId,
                        userId: this.params.userId,
                        userLogin: this.params.userLogin
                    }
                });
            }, 500);
        }
    }

    private onSaveError() {
        this.isSaving = false;
    }

    confirmDelete(id: number) {
        if (this.params.imMemberOf) {
            this.delete = true;
        }
        this.teamMemberService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'teamMemberListModification',
                content: 'Deleted an teamMember'
            });
            if (this.params.imMemberOf) {
                setTimeout(() => {
                    this.router.navigate(['/team'], {
                        queryParams: {
                            active: this.params.active,
                            imMemberOf: this.params.imMemberOf,
                            deleted: id,
                        }
                    });
                }, 500);
            } else {
                    this.getMembers(this.team.id.toString());
            }
        });
    }
}
