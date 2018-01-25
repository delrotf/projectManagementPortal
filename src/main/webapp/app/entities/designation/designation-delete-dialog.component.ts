import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Designation } from './designation.model';
import { DesignationPopupService } from './designation-popup.service';
import { DesignationService } from './designation.service';

@Component({
    selector: 'jhi-designation-delete-dialog',
    templateUrl: './designation-delete-dialog.component.html'
})
export class DesignationDeleteDialogComponent {

    designation: Designation;

    constructor(
        private designationService: DesignationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.designationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'designationListModification',
                content: 'Deleted an designation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-designation-delete-popup',
    template: ''
})
export class DesignationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private designationPopupService: DesignationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.designationPopupService
                .open(DesignationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
