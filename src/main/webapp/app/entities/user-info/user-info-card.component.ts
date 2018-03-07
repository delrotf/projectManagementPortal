import { TeamMember } from './../team-member/team-member.model';
import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { UserInfo } from './user-info.model';
import { UserInfoService } from './user-info.service';
import { TeamMemberService } from './../team-member/team-member.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { ActivatedRoute, Router } from '@angular/router';
import { VIEW_TEAMS_My, VIEW_TEAMS_ALL, VIEW_TEAMS_IM_MEMBER_OF,
    VIEW_TEAMS_BROWSE_MORE, VIEW_TEAMS_USERS_HEADED,
    VIEW_TEAMS_USERS_MEMBER_OF, VIEW_TEAMS_USERS_MEMBER_OF_MY,
    ACTION_JOIN_TEAM, ACTION_ADD_USERS_TO_TEAM, ACTION_TEAMS_TO_USER } from './../../shared/constants/page.constants';

@Component({
    selector: 'jhi-user-info-card',
    templateUrl: './user-info-card.component.html',
    styleUrls: [
        'user-info.css'
    ]
})
export class UserInfoCardComponent implements OnInit, OnDestroy {

    @Input() userInfo: UserInfo;
    @Input() currentAccount: any;
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    links: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    isTabular: boolean;
    teamMembers: TeamMember[];
    popoverPlacement = 'top';

    clientX = 0;
    clientY = 0;
    isAdmin: boolean;

    usersHeadedTeams = VIEW_TEAMS_USERS_HEADED;
    usersMemberOf = VIEW_TEAMS_USERS_MEMBER_OF;
    usersMemberOfMyTeams = VIEW_TEAMS_USERS_MEMBER_OF_MY;
    action: string;
    joinTeam = ACTION_JOIN_TEAM;
    addUsersToTeam = ACTION_ADD_USERS_TO_TEAM;
    addTeamsToUser = ACTION_TEAMS_TO_USER;

    constructor(
        private userInfoService: UserInfoService,
        private teamMemberService: TeamMemberService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private dataUtils: JhiDataUtils,
        private router: Router,
    ) {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
    }

    getTeams() {
        if (!this.isAdmin) {
        this.teamMemberService.query({
            query: JSON.stringify({userInfoId: this.userInfo.id, teamHeadUserLogin: this.currentAccount.login})
        }).subscribe(
            (res: ResponseWrapper) => {
                this.teamMembers = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        } else {
        this.teamMemberService.query({
            query: JSON.stringify({userInfoId: this.userInfo.id})
        }).subscribe(
            (res: ResponseWrapper) => {
                this.teamMembers = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        }
    }

    ngOnInit() {

        this.principal.hasAuthority('ROLE_ADMIN').then((value) => {
            this.isAdmin = value;
        this.getTeams();
        });

        this.registerChangeInUserInfos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
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

    registerChangeInUserInfos() {
        this.eventSubscriber = this.eventManager.subscribe('teamMemberListModification', (response) => this.getTeams());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    confirmDelete(id: number) {
        this.teamMemberService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'teamMemberListModification',
                content: 'Deleted an teamMember'
            });
        });
    }
}
