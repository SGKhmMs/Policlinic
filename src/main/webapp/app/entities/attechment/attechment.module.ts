import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClinicSharedModule } from '../../shared';
import {
    AttechmentService,
    AttechmentPopupService,
    AttechmentComponent,
    AttechmentDetailComponent,
    AttechmentDialogComponent,
    AttechmentPopupComponent,
    AttechmentDeletePopupComponent,
    AttechmentDeleteDialogComponent,
    attechmentRoute,
    attechmentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...attechmentRoute,
    ...attechmentPopupRoute,
];

@NgModule({
    imports: [
        ClinicSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AttechmentComponent,
        AttechmentDetailComponent,
        AttechmentDialogComponent,
        AttechmentDeleteDialogComponent,
        AttechmentPopupComponent,
        AttechmentDeletePopupComponent,
    ],
    entryComponents: [
        AttechmentComponent,
        AttechmentDialogComponent,
        AttechmentPopupComponent,
        AttechmentDeleteDialogComponent,
        AttechmentDeletePopupComponent,
    ],
    providers: [
        AttechmentService,
        AttechmentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicAttechmentModule {}
