// import { UserInfo } from './../user-info/user-info.model';
// import { UserInfoService } from './../user-info/user-info.service';
import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Team } from './team.model';
import { TeamPopupService } from './team-popup.service';
import { TeamService } from './team.service';
import { User, UserService, Principal } from '../../shared';
import { UserInfo, UserInfoService } from '../user-info';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-team-dialog',
    templateUrl: './team-dialog.component.html',
    styleUrls: [
        'team.css'
    ]
})
export class TeamDialogComponent implements OnInit {

    team: Team;
    isSaving: boolean;

    userInfos: UserInfo[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private teamService: TeamService,
        private userInfoService: UserInfoService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.principal.hasAuthority('ROLE_ADMIN').then((hasAuthority) => {
            if (hasAuthority) {
                this.userInfoService.query().subscribe(
                    (res: ResponseWrapper) => {
                        this.userInfos = res.json;
                    },
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            } else {
                this.principal.identity().then((account) => {
                    this.userInfoService.query({query: JSON.stringify({userLogin: account.login})}).subscribe(
                        (res: ResponseWrapper) => {
                            this.team.teamHeadId = res.json[0].id;
                        },
                        (res: ResponseWrapper) => this.onError(res.json)
                    );
                });
            }
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.team, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.team.createdDate = new Date().toISOString();
        if (this.team.id !== undefined) {
            this.subscribeToSaveResponse(this.teamService.update(this.team));
        } else {
            this.team.active = true;
            this.subscribeToSaveResponse(this.teamService.create(this.team));
        }
    }

    private subscribeToSaveResponse(result: Observable<Team>) {
        result.subscribe(
            (res: Team) => this.onSaveSuccess(res),
            (res: Response) => this.onSaveError()
        );
    }

    private onSaveSuccess(result: Team) {
        this.eventManager.broadcast({
            name: 'teamListModification',
            content: 'OK'
        });
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserInfoById(index: number, item: UserInfo) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-team-popup',
    template: ''
})
export class TeamPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private teamPopupService: TeamPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.teamPopupService.open(
                    TeamDialogComponent as Component,
                    params['id']
                );
            } else {
                this.teamPopupService.open(TeamDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
