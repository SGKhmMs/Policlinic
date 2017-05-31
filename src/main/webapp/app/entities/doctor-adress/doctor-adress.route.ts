import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { DoctorAdressComponent } from './doctor-adress.component';
import { DoctorAdressDetailComponent } from './doctor-adress-detail.component';
import { DoctorAdressPopupComponent } from './doctor-adress-dialog.component';
import { DoctorAdressDeletePopupComponent } from './doctor-adress-delete-dialog.component';

import { Principal } from '../../shared';

export const doctorAdressRoute: Routes = [
    {
        path: 'doctor-adress',
        component: DoctorAdressComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorAdress.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'doctor-adress/:id',
        component: DoctorAdressDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorAdress.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const doctorAdressPopupRoute: Routes = [
    {
        path: 'doctor-adress-new',
        component: DoctorAdressPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorAdress.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'doctor-adress/:id/edit',
        component: DoctorAdressPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorAdress.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'doctor-adress/:id/delete',
        component: DoctorAdressDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorAdress.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
