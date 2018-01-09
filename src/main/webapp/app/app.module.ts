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
        NavbarComponent,
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
