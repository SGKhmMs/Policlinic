import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ClinicDoctorComponent } from './clinic-doctor.component';
import { ClinicDoctorDetailComponent } from './clinic-doctor-detail.component';
import { ClinicDoctorPopupComponent } from './clinic-doctor-dialog.component';
import { ClinicDoctorDeletePopupComponent } from './clinic-doctor-delete-dialog.component';

import { Principal } from '../../shared';

export const clinicDoctorRoute: Routes = [
    {
        path: 'clinic-doctor',
        component: ClinicDoctorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clinicDoctor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'clinic-doctor/:id',
        component: ClinicDoctorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clinicDoctor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clinicDoctorPopupRoute: Routes = [
    {
        path: 'clinic-doctor-new',
        component: ClinicDoctorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clinicDoctor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'clinic-doctor/:id/edit',
        component: ClinicDoctorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clinicDoctor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'clinic-doctor/:id/delete',
        component: ClinicDoctorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.clinicDoctor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
