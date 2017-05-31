import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ClinicModeratorComponent } from './clinic-moderator.component';
import { ClinicModeratorDetailComponent } from './clinic-moderator-detail.component';
import { ClinicModeratorPopupComponent } from './clinic-moderator-dialog.component';
import { ClinicModeratorDeletePopupComponent } from './clinic-moderator-delete-dialog.component';

import { Principal } from '../../shared';

export const clinicModeratorRoute: Routes = [
    {
        path: 'clinic-moderator',
        component: ClinicModeratorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clinicModerator.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'clinic-moderator/:id',
        component: ClinicModeratorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clinicModerator.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clinicModeratorPopupRoute: Routes = [
    {
        path: 'clinic-moderator-new',
        component: ClinicModeratorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clinicModerator.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'clinic-moderator/:id/edit',
        component: ClinicModeratorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clinicModerator.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'clinic-moderator/:id/delete',
        component: ClinicModeratorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clinicModerator.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
