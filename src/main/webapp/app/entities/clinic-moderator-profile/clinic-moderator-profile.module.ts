import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClinicSharedModule } from '../../shared';
import {
    ClinicModeratorProfileService,
    ClinicModeratorProfilePopupService,
    ClinicModeratorProfileComponent,
    ClinicModeratorProfileDetailComponent,
    ClinicModeratorProfileDialogComponent,
    ClinicModeratorProfilePopupComponent,
    ClinicModeratorProfileDeletePopupComponent,
    ClinicModeratorProfileDeleteDialogComponent,
    clinicModeratorProfileRoute,
    clinicModeratorProfilePopupRoute,
} from './';

const ENTITY_STATES = [
    ...clinicModeratorProfileRoute,
    ...clinicModeratorProfilePopupRoute,
];

@NgModule({
    imports: [
        ClinicSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClinicModeratorProfileComponent,
        ClinicModeratorProfileDetailComponent,
        ClinicModeratorProfileDialogComponent,
        ClinicModeratorProfileDeleteDialogComponent,
        ClinicModeratorProfilePopupComponent,
        ClinicModeratorProfileDeletePopupComponent,
    ],
    entryComponents: [
        ClinicModeratorProfileComponent,
        ClinicModeratorProfileDialogComponent,
        ClinicModeratorProfilePopupComponent,
        ClinicModeratorProfileDeleteDialogComponent,
        ClinicModeratorProfileDeletePopupComponent,
    ],
    providers: [
        ClinicModeratorProfileService,
        ClinicModeratorProfilePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicClinicModeratorProfileModule {}
