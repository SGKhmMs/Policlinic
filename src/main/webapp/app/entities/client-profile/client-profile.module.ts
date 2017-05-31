import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClinicSharedModule } from '../../shared';
import {
    ClientProfileService,
    ClientProfilePopupService,
    ClientProfileComponent,
    ClientProfileDetailComponent,
    ClientProfileDialogComponent,
    ClientProfilePopupComponent,
    ClientProfileDeletePopupComponent,
    ClientProfileDeleteDialogComponent,
    clientProfileRoute,
    clientProfilePopupRoute,
} from './';

const ENTITY_STATES = [
    ...clientProfileRoute,
    ...clientProfilePopupRoute,
];

@NgModule({
    imports: [
        ClinicSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClientProfileComponent,
        ClientProfileDetailComponent,
        ClientProfileDialogComponent,
        ClientProfileDeleteDialogComponent,
        ClientProfilePopupComponent,
        ClientProfileDeletePopupComponent,
    ],
    entryComponents: [
        ClientProfileComponent,
        ClientProfileDialogComponent,
        ClientProfilePopupComponent,
        ClientProfileDeleteDialogComponent,
        ClientProfileDeletePopupComponent,
    ],
    providers: [
        ClientProfileService,
        ClientProfilePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicClientProfileModule {}
