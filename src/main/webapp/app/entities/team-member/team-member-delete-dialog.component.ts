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
    params: any

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

            if (this.params.imMemberOf) {
                this.router.navigate(['/team'], {
                    queryParams: {
                        active: this.params.active,
                        imMemberOf: this.params.imMemberOf,
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
