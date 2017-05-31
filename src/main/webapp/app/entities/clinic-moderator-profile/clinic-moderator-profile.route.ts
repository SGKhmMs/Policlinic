import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ClinicModeratorProfileComponent } from './clinic-moderator-profile.component';
import { ClinicModeratorProfileDetailComponent } from './clinic-moderator-profile-detail.component';
import { ClinicModeratorProfilePopupComponent } from './clinic-moderator-profile-dialog.component';
import { ClinicModeratorProfileDeletePopupComponent } from './clinic-moderator-profile-delete-dialog.component';

import { Principal } from '../../shared';

export const clinicModeratorProfileRoute: Routes = [
    {
        path: 'clinic-moderator-profile',
        component: ClinicModeratorProfileComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clinicModeratorProfile.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'clinic-moderator-profile/:id',
        component: ClinicModeratorProfileDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clinicModeratorProfile.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clinicModeratorProfilePopupRoute: Routes = [
    {
        path: 'clinic-moderator-profile-new',
        component: ClinicModeratorProfilePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clinicModeratorProfile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'clinic-moderator-profile/:id/edit',
        component: ClinicModeratorProfilePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clinicModeratorProfile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'clinic-moderator-profile/:id/delete',
        component: ClinicModeratorProfileDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clinicModeratorProfile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
