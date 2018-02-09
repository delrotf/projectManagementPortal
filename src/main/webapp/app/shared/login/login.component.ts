import { Principal } from './../auth/principal.service';
import { ResponseWrapper } from './../model/response-wrapper.model';
import { UserInfoService } from './../../entities/user-info/user-info.service';
import { Component, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

import { LoginService } from './login.service';
import { StateStorageService } from '../auth/state-storage.service';

@Component({
    selector: 'jhi-login-modal',
    templateUrl: './login.component.html'
})
export class JhiLoginModalComponent implements AfterViewInit {
    authenticationError: boolean;
    password: string;
    rememberMe: boolean;
    username: string;
    credentials: any;

    constructor(
        private eventManager: JhiEventManager,
        private loginService: LoginService,
        private stateStorageService: StateStorageService,
        private elementRef: ElementRef,
        private renderer: Renderer,
        private router: Router,
        private userInfoService: UserInfoService,
        private principal: Principal,
        public activeModal: NgbActiveModal
    ) {
        this.credentials = {};
    }

    ngAfterViewInit() {
        this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#username'), 'focus', []);
    }

    cancel() {
        this.credentials = {
            username: null,
            password: null,
            rememberMe: true
        };
        this.authenticationError = false;
        this.activeModal.dismiss('cancel');
    }

    login() {
        this.loginService.login({
            username: this.username,
            password: this.password,
            rememberMe: this.rememberMe
        }).then(() => {
            this.authenticationError = false;
            this.activeModal.dismiss('login success');
            // if (this.router.url === '/register' || (/^\/activate\//.test(this.router.url)) ||
            if (this.router.url === '/register' || (/^\/activate/.test(this.router.url)) ||
            (/^\/activate\//.test(this.router.url)) || (/^\/reset\//.test(this.router.url))) {
                this.router.navigate(['']);
            }

            this.eventManager.broadcast({
                name: 'authenticationSuccess',
                content: 'Sending Authentication Success'
            });

            // // previousState was set in the authExpiredInterceptor before being redirected to login modal.
            // // since login is succesful, go to stored previousState and clear previousState
            const redirect = this.stateStorageService.getUrl();
            if (redirect) {
                this.stateStorageService.storeUrl(null);
                this.router.navigate([redirect]);
            }
            this.principal.identity().then((account) => {
                // find the userInfo of current login.
                if (account) {
                    this.userInfoService.query({query: JSON.stringify({userLogin: account.login})})
                    .subscribe((res: ResponseWrapper) => {
                        if (res.json.length) {
                            const userInfo = res.json[0];
                            account.imageURL = userInfo.image ? 'data:' + userInfo.imageContentType + ';base64,' + userInfo.image : null;
                        // } else {
                        //     this.userInfo = new UserInfo();
                        }
                    });
                }
            });
        }).catch(() => {
            this.authenticationError = true;
        });
    }

    register() {
        this.activeModal.dismiss('to state register');
        // this.router.navigate(['/register']);
        this.router.navigate(['']);
    }

    requestResetPassword() {
        this.activeModal.dismiss('to state requestReset');
        this.router.navigate(['/reset', 'request']);
    }
}
