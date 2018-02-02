import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProjectManagementPortalSharedModule } from '../../shared';
import { ProjectManagementPortalAdminModule } from '../../admin/admin.module';
import {
    TeamMemberService,
    TeamMemberPopupService,
    TeamMemberComponent,
    TeamMemberDetailComponent,
    TeamMemberDialogComponent,
    TeamMemberPopupComponent,
    TeamMemberDeletePopupComponent,
    TeamMemberDeleteDialogComponent,
    teamMemberRoute,
    teamMemberPopupRoute,
    TeamMemberResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...teamMemberRoute,
    ...teamMemberPopupRoute,
];

@NgModule({
    imports: [
        ProjectManagementPortalSharedModule,
        ProjectManagementPortalAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TeamMemberComponent,
        TeamMemberDetailComponent,
        TeamMemberDialogComponent,
        TeamMemberDeleteDialogComponent,
        TeamMemberPopupComponent,
        TeamMemberDeletePopupComponent,
    ],
    entryComponents: [
        TeamMemberComponent,
        TeamMemberDialogComponent,
        TeamMemberPopupComponent,
        TeamMemberDeleteDialogComponent,
        TeamMemberDeletePopupComponent,
    ],
    providers: [
        TeamMemberService,
        TeamMemberPopupService,
        TeamMemberResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjectManagementPortalTeamMemberModule {}
