import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { DoctorProfileComponent } from './doctor-profile.component';
import { DoctorProfileDetailComponent } from './doctor-profile-detail.component';
import { DoctorProfilePopupComponent } from './doctor-profile-dialog.component';
import { DoctorProfileDeletePopupComponent } from './doctor-profile-delete-dialog.component';

import { Principal } from '../../shared';

export const doctorProfileRoute: Routes = [
    {
        path: 'doctor-profile',
        component: DoctorProfileComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorProfile.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'doctor-profile/:id',
        component: DoctorProfileDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorProfile.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const doctorProfilePopupRoute: Routes = [
    {
        path: 'doctor-profile-new',
        component: DoctorProfilePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorProfile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'doctor-profile/:id/edit',
        component: DoctorProfilePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorProfile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'doctor-profile/:id/delete',
        component: DoctorProfileDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorProfile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
