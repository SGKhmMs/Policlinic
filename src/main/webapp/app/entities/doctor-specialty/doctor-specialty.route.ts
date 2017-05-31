import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { DoctorSpecialtyComponent } from './doctor-specialty.component';
import { DoctorSpecialtyDetailComponent } from './doctor-specialty-detail.component';
import { DoctorSpecialtyPopupComponent } from './doctor-specialty-dialog.component';
import { DoctorSpecialtyDeletePopupComponent } from './doctor-specialty-delete-dialog.component';

import { Principal } from '../../shared';

export const doctorSpecialtyRoute: Routes = [
    {
        path: 'doctor-specialty',
        component: DoctorSpecialtyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorSpecialty.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'doctor-specialty/:id',
        component: DoctorSpecialtyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorSpecialty.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const doctorSpecialtyPopupRoute: Routes = [
    {
        path: 'doctor-specialty-new',
        component: DoctorSpecialtyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorSpecialty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'doctor-specialty/:id/edit',
        component: DoctorSpecialtyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorSpecialty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'doctor-specialty/:id/delete',
        component: DoctorSpecialtyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorSpecialty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
