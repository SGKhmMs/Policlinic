import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClinicSharedModule } from '../../shared';
import {
    DoctorProfileService,
    DoctorProfilePopupService,
    DoctorProfileComponent,
    DoctorProfileDetailComponent,
    DoctorProfileDialogComponent,
    DoctorProfilePopupComponent,
    DoctorProfileDeletePopupComponent,
    DoctorProfileDeleteDialogComponent,
    doctorProfileRoute,
    doctorProfilePopupRoute,
} from './';

const ENTITY_STATES = [
    ...doctorProfileRoute,
    ...doctorProfilePopupRoute,
];

@NgModule({
    imports: [
        ClinicSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DoctorProfileComponent,
        DoctorProfileDetailComponent,
        DoctorProfileDialogComponent,
        DoctorProfileDeleteDialogComponent,
        DoctorProfilePopupComponent,
        DoctorProfileDeletePopupComponent,
    ],
    entryComponents: [
        DoctorProfileComponent,
        DoctorProfileDialogComponent,
        DoctorProfilePopupComponent,
        DoctorProfileDeleteDialogComponent,
        DoctorProfileDeletePopupComponent,
    ],
    providers: [
        DoctorProfileService,
        DoctorProfilePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicDoctorProfileModule {}
