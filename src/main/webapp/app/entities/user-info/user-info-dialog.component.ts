import { Principal } from './../../shared/auth/principal.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserInfo } from './user-info.model';
import { UserInfoPopupService } from './user-info-popup.service';
import { UserInfoService } from './user-info.service';
import { User, UserService } from '../../shared';
import { Designation, DesignationService } from '../designation';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-user-info-dialog',
    templateUrl: './user-info-dialog.component.html'
})
export class UserInfoDialogComponent implements OnInit {

    userInfo: UserInfo;

    isSaving: boolean;

    userInfosForSupervisor: UserInfo[]; // supervisors

    user: User

    designations: Designation[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private userInfoService: UserInfoService,
        private userService: UserService,
        private principal: Principal,
        private designationService: DesignationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        // this.userService.query()
        //     .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));

        // find the user id of current login.
        this.principal.identity().then((account) => {
            console.log('account.login: ' + JSON.stringify(account.login));
            this.userService.find(account.login)
            .subscribe((user) => {
                this.user = user;
            });
                // find the userInfo of current login.
                this.userInfoService.query({query: JSON.stringify({userLogin: account.login})})
                    .subscribe((res: ResponseWrapper) => {
                        this.userInfo = res.json[0];
                        console.log('this.userInfo: ' + JSON.stringify(this.userInfo));
                    }, (res: ResponseWrapper) => this.onError(res.json));
        });

        // for drop-down list of supervisor
        this.userInfoService.query()
            .subscribe((res: ResponseWrapper) => {
                this.userInfosForSupervisor = res.json;
            }, (res: ResponseWrapper) => this.onError(res.json));

        this.designationService.query()
            .subscribe((res: ResponseWrapper) => { this.designations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userInfo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userInfoService.update(this.userInfo));
        } else {
            this.subscribeToSaveResponse(
                this.userInfoService.create(this.userInfo));
        }
    }

    private subscribeToSaveResponse(result: Observable<UserInfo>) {
        result.subscribe((res: UserInfo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: UserInfo) {
        this.eventManager.broadcast({ name: 'userInfoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    // trackUserById(index: number, item: User) {
    //     return item.id;
    // }

    trackUserInfoById(index: number, item: UserInfo) {
        return item.id;
    }
    trackDesignationById(index: number, item: Designation) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-info-popup',
    template: ''
})
export class UserInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userInfoPopupService: UserInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userInfoPopupService
                    .open(UserInfoDialogComponent as Component, params['id']);
            } else {
                this.userInfoPopupService
                    .open(UserInfoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
