import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Designation } from './designation.model';
import { DesignationPopupService } from './designation-popup.service';
import { DesignationService } from './designation.service';

@Component({
    selector: 'jhi-designation-dialog',
    templateUrl: './designation-dialog.component.html'
})
export class DesignationDialogComponent implements OnInit {

    designation: Designation;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private designationService: DesignationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.designation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.designationService.update(this.designation));
        } else {
            this.subscribeToSaveResponse(
                this.designationService.create(this.designation));
        }
    }

    private subscribeToSaveResponse(result: Observable<Designation>) {
        result.subscribe((res: Designation) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Designation) {
        this.eventManager.broadcast({ name: 'designationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-designation-popup',
    template: ''
})
export class DesignationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private designationPopupService: DesignationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.designationPopupService
                    .open(DesignationDialogComponent as Component, params['id']);
            } else {
                this.designationPopupService
                    .open(DesignationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
