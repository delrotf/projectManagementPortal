import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { UserInfo } from './user-info.model';
import { UserInfoService } from './user-info.service';

@Component({
    selector: 'jhi-user-info-detail',
    templateUrl: './user-info-detail.component.html'
})
export class UserInfoDetailComponent implements OnInit, OnDestroy {

    userInfo: UserInfo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private userInfoService: UserInfoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserInfos();
    }

    load(id) {
        this.userInfoService.find(id).subscribe((userInfo) => {
            this.userInfo = userInfo;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserInfos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userInfoListModification',
            (response) => this.load(this.userInfo.id)
        );
    }
}
