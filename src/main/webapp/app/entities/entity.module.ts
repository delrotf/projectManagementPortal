import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ProjectManagementPortalTeamModule } from './team/team.module';
import { ProjectManagementPortalTeamMemberModule } from './team-member/team-member.module';
import { ProjectManagementPortalDesignationModule } from './designation/designation.module';
import { ProjectManagementPortalUserInfoModule } from './user-info/user-info.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ProjectManagementPortalTeamModule,
        ProjectManagementPortalTeamMemberModule,
        ProjectManagementPortalDesignationModule,
        ProjectManagementPortalUserInfoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjectManagementPortalEntityModule {}
