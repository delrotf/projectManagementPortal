import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TeamMemberComponent } from './team-member.component';
import { TeamMemberDetailComponent } from './team-member-detail.component';
import { TeamMemberPopupComponent } from './team-member-dialog.component';
import { TeamMemberDeletePopupComponent } from './team-member-delete-dialog.component';

export const teamMemberRoute: Routes = [
    {
        path: 'team-member',
        component: TeamMemberComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TeamMembers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'team-member/:id',
        component: TeamMemberDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TeamMembers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const teamMemberPopupRoute: Routes = [
    {
        path: 'team-member-new',
        component: TeamMemberPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TeamMembers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'team-member/:id/edit',
        component: TeamMemberPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TeamMembers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'team-member/:id/delete',
        component: TeamMemberDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TeamMembers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
