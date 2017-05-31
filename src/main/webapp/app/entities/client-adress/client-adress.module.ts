import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClinicSharedModule } from '../../shared';
import {
    ClientAdressService,
    ClientAdressPopupService,
    ClientAdressComponent,
    ClientAdressDetailComponent,
    ClientAdressDialogComponent,
    ClientAdressPopupComponent,
    ClientAdressDeletePopupComponent,
    ClientAdressDeleteDialogComponent,
    clientAdressRoute,
    clientAdressPopupRoute,
} from './';

const ENTITY_STATES = [
    ...clientAdressRoute,
    ...clientAdressPopupRoute,
];

@NgModule({
    imports: [
        ClinicSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClientAdressComponent,
        ClientAdressDetailComponent,
        ClientAdressDialogComponent,
        ClientAdressDeleteDialogComponent,
        ClientAdressPopupComponent,
        ClientAdressDeletePopupComponent,
    ],
    entryComponents: [
        ClientAdressComponent,
        ClientAdressDialogComponent,
        ClientAdressPopupComponent,
        ClientAdressDeleteDialogComponent,
        ClientAdressDeletePopupComponent,
    ],
    providers: [
        ClientAdressService,
        ClientAdressPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicClientAdressModule {}
