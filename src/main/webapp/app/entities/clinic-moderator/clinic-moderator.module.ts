import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClinicSharedModule } from '../../shared';
import {
    ClinicModeratorService,
    ClinicModeratorPopupService,
    ClinicModeratorComponent,
    ClinicModeratorDetailComponent,
    ClinicModeratorDialogComponent,
    ClinicModeratorPopupComponent,
    ClinicModeratorDeletePopupComponent,
    ClinicModeratorDeleteDialogComponent,
    clinicModeratorRoute,
    clinicModeratorPopupRoute,
} from './';

const ENTITY_STATES = [
    ...clinicModeratorRoute,
    ...clinicModeratorPopupRoute,
];

@NgModule({
    imports: [
        ClinicSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClinicModeratorComponent,
        ClinicModeratorDetailComponent,
        ClinicModeratorDialogComponent,
        ClinicModeratorDeleteDialogComponent,
        ClinicModeratorPopupComponent,
        ClinicModeratorDeletePopupComponent,
    ],
    entryComponents: [
        ClinicModeratorComponent,
        ClinicModeratorDialogComponent,
        ClinicModeratorPopupComponent,
        ClinicModeratorDeleteDialogComponent,
        ClinicModeratorDeletePopupComponent,
    ],
    providers: [
        ClinicModeratorService,
        ClinicModeratorPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicClinicModeratorModule {}
