import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Designation } from './designation.model';
import { DesignationService } from './designation.service';

@Component({
    selector: 'jhi-designation-detail',
    templateUrl: './designation-detail.component.html'
})
export class DesignationDetailComponent implements OnInit, OnDestroy {

    designation: Designation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private designationService: DesignationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDesignations();
    }

    load(id) {
        this.designationService.find(id).subscribe((designation) => {
            this.designation = designation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDesignations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'designationListModification',
            (response) => this.load(this.designation.id)
        );
    }
}
