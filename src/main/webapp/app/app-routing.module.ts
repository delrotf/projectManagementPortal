import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute, navbarRoute, sidebarRoute } from './layouts';

const LAYOUT_ROUTES = [
    navbarRoute,
    sidebarRoute,
    ...errorRoute
];

@NgModule({
    imports: [
        RouterModule.forRoot(LAYOUT_ROUTES, { useHash: true })
    ],
    exports: [
        RouterModule
    ]
})
export class ProjectManagementPortalAppRoutingModule {}
