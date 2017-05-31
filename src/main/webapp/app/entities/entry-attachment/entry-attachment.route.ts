import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { EntryAttachmentComponent } from './entry-attachment.component';
import { EntryAttachmentDetailComponent } from './entry-attachment-detail.component';
import { EntryAttachmentPopupComponent } from './entry-attachment-dialog.component';
import { EntryAttachmentDeletePopupComponent } from './entry-attachment-delete-dialog.component';

import { Principal } from '../../shared';

export const entryAttachmentRoute: Routes = [
    {
        path: 'entry-attachment',
        component: EntryAttachmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.entryAttachment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'entry-attachment/:id',
        component: EntryAttachmentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.entryAttachment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const entryAttachmentPopupRoute: Routes = [
    {
        path: 'entry-attachment-new',
        component: EntryAttachmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.entryAttachment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entry-attachment/:id/edit',
        component: EntryAttachmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.entryAttachment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entry-attachment/:id/delete',
        component: EntryAttachmentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clinicApp.entryAttachment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
