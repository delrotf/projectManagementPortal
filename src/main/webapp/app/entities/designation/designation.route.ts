import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DesignationComponent } from './designation.component';
import { DesignationDetailComponent } from './designation-detail.component';
import { DesignationPopupComponent } from './designation-dialog.component';
import { DesignationDeletePopupComponent } from './designation-delete-dialog.component';

export const designationRoute: Routes = [
    {
        path: 'designation',
        component: DesignationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Designations'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'designation/:id',
        component: DesignationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Designations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const designationPopupRoute: Routes = [
    {
        path: 'designation-new',
        component: DesignationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Designations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'designation/:id/edit',
        component: DesignationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Designations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'designation/:id/delete',
        component: DesignationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Designations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
