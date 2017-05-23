import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClinicSharedModule } from '../../shared';
import {
    DoctorReviewService,
    DoctorReviewPopupService,
    DoctorReviewComponent,
    DoctorReviewDetailComponent,
    DoctorReviewDialogComponent,
    DoctorReviewPopupComponent,
    DoctorReviewDeletePopupComponent,
    DoctorReviewDeleteDialogComponent,
    doctorReviewRoute,
    doctorReviewPopupRoute,
} from './';

const ENTITY_STATES = [
    ...doctorReviewRoute,
    ...doctorReviewPopupRoute,
];

@NgModule({
    imports: [
        ClinicSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DoctorReviewComponent,
        DoctorReviewDetailComponent,
        DoctorReviewDialogComponent,
        DoctorReviewDeleteDialogComponent,
        DoctorReviewPopupComponent,
        DoctorReviewDeletePopupComponent,
    ],
    entryComponents: [
        DoctorReviewComponent,
        DoctorReviewDialogComponent,
        DoctorReviewPopupComponent,
        DoctorReviewDeleteDialogComponent,
        DoctorReviewDeletePopupComponent,
    ],
    providers: [
        DoctorReviewService,
        DoctorReviewPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicDoctorReviewModule {}
