import { PasswordResetFinishComponent } from './account/password-reset/finish/password-reset-finish.component';
import { PasswordResetInitComponent } from './account/password-reset/init/password-reset-init.component';
import { PasswordStrengthBarComponent } from './account/password/password-strength-bar.component';
import { PasswordComponent } from './account/password/password.component';
import { RegisterComponent } from './account/register/register.component';
import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ngx-webstorage';

import { ProjectManagementPortalSharedModule, UserRouteAccessService } from './shared';
import { ProjectManagementPortalAppRoutingModule} from './app-routing.module';
import { ProjectManagementPortalHomeModule } from './home/home.module';
import { ProjectManagementPortalAdminModule } from './admin/admin.module';
import { ProjectManagementPortalAccountModule } from './account/account.module';
import { ProjectManagementPortalEntityModule } from './entities/entity.module';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    NavbarComponent,
    SidebarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        ProjectManagementPortalAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        ProjectManagementPortalSharedModule,
        ProjectManagementPortalHomeModule,
        ProjectManagementPortalAdminModule,
        ProjectManagementPortalAccountModule,
        ProjectManagementPortalEntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        RegisterComponent,
        PasswordComponent,
        PasswordStrengthBarComponent,
        PasswordResetInitComponent,
        PasswordResetFinishComponent,
        NavbarComponent,
        SidebarComponent,
        ErrorComponent,
        PageRibbonComponent,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class ProjectManagementPortalAppModule {}
