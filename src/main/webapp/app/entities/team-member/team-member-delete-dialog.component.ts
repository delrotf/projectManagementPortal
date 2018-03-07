import { VIEW_TEAMS_My, VIEW_TEAMS_ALL, VIEW_TEAMS_IM_MEMBER_OF,
    VIEW_TEAMS_BROWSE_MORE, VIEW_TEAMS_USERS_HEADED,
    VIEW_TEAMS_USERS_MEMBER_OF, VIEW_TEAMS_USERS_MEMBER_OF_MY } from './../../shared/constants/page.constants';

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TeamMember } from './team-member.model';
import { TeamMemberPopupService } from './team-member-popup.service';
import { TeamMemberService } from './team-member.service';

@Component({
    selector: 'jhi-team-member-delete-dialog',
    templateUrl: './team-member-delete-dialog.component.html'
})
export class TeamMemberDeleteDialogComponent {

    teamMember: TeamMember;
    params: any;

    viewId: string;
    myTeams = VIEW_TEAMS_My;
    allTeams = VIEW_TEAMS_ALL;
    teamsImMemberOf = VIEW_TEAMS_IM_MEMBER_OF;
    browseMoreTeams = VIEW_TEAMS_BROWSE_MORE;

    constructor(
        private teamMemberService: TeamMemberService,
        public activeModal: NgbActiveModal,
        private route: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.route.queryParams
        .subscribe((params) => {
            this.params = params;
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.teamMemberService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'teamMemberListModification',
                content: 'Deleted an teamMember'
            });

            if (this.params.viewId === this.teamsImMemberOf) {
                this.router.navigate(['/team'], {
                    queryParams: {
                        inactive: this.params.inactive,
                        viewId: this.params.viewId,
                        deleted: id,
                    }
                });
            }

            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-team-member-delete-popup',
    template: ''
})
export class TeamMemberDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private teamMemberPopupService: TeamMemberPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.teamMemberPopupService
                .open(TeamMemberDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
