import { VIEW_TEAMS_My, VIEW_TEAMS_ALL, VIEW_TEAMS_IM_MEMBER_OF, VIEW_TEAMS_BROWSE_MORE,
    ACTION_JOIN_TEAM, ACTION_ADD_USERS_TO_TEAM, ACTION_TEAMS_TO_USER, VIEW_TEAMS_USERS_HEADED,
    VIEW_TEAMS_USERS_MEMBER_OF, VIEW_TEAMS_USERS_MEMBER_OF_MY } from './../../shared/constants/page.constants';
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

@Component({
    // tslint:disable-next-line:component-selector
    selector: '[jhi-team-td]',
    templateUrl: './team-td.component.html',
    styleUrls: [
        'team.css'
    ]
})
export class TeamTdComponent implements OnInit {

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
    inactive: boolean;

    viewId: string;
    myTeams = VIEW_TEAMS_My;
    allTeams = VIEW_TEAMS_ALL;
    teamsImMemberOf = VIEW_TEAMS_IM_MEMBER_OF;
    browseMoreTeams = VIEW_TEAMS_BROWSE_MORE;
    usersHeadedTeams = VIEW_TEAMS_USERS_HEADED;
    usersMemberOf = VIEW_TEAMS_USERS_MEMBER_OF;
    usersMemberOfMyTeams = VIEW_TEAMS_USERS_MEMBER_OF_MY;
    action: string;
    joinTeam = ACTION_JOIN_TEAM;
    addUsersToTeam = ACTION_ADD_USERS_TO_TEAM;
    addTeamsToUser = ACTION_TEAMS_TO_USER;

    isTabular: boolean;

    popoverPlacement = 'top';

    clientX = 0;
    clientY = 0;
    saved: string;

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
            this.inactive = params.inactive;
            this.viewId = params.viewId;
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

    confirmDelete(id: number) {
        this.teamMemberService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'teamMemberListModification',
                content: 'Deleted an teamMember'
            });

            if (this.viewId === this.teamsImMemberOf || this.viewId === this.usersMemberOf) {
                this.router.navigate(['/team'], {
                    queryParams: {
                        inactive: this.params.inactive,
                        viewId: this.params.viewId,
                        deleted: id,
                    }
                });
            } else {
                this.getMembers(this.team.id.toString());
            }
        });
    }
}
