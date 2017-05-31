import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { DoctorReviewComponent } from './doctor-review.component';
import { DoctorReviewDetailComponent } from './doctor-review-detail.component';
import { DoctorReviewPopupComponent } from './doctor-review-dialog.component';
import { DoctorReviewDeletePopupComponent } from './doctor-review-delete-dialog.component';

import { Principal } from '../../shared';

export const doctorReviewRoute: Routes = [
    {
        path: 'doctor-review',
        component: DoctorReviewComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorReview.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'doctor-review/:id',
        component: DoctorReviewDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorReview.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const doctorReviewPopupRoute: Routes = [
    {
        path: 'doctor-review-new',
        component: DoctorReviewPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorReview.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'doctor-review/:id/edit',
        component: DoctorReviewPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorReview.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'doctor-review/:id/delete',
        component: DoctorReviewDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.doctorReview.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
