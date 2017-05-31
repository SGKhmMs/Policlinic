import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClinicSharedModule } from '../../shared';
import {
    ChatService,
    ChatPopupService,
    ChatComponent,
    ChatDetailComponent,
    ChatDialogComponent,
    ChatPopupComponent,
    ChatDeletePopupComponent,
    ChatDeleteDialogComponent,
    chatRoute,
    chatPopupRoute,
} from './';

const ENTITY_STATES = [
    ...chatRoute,
    ...chatPopupRoute,
];

@NgModule({
    imports: [
        ClinicSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ChatComponent,
        ChatDetailComponent,
        ChatDialogComponent,
        ChatDeleteDialogComponent,
        ChatPopupComponent,
        ChatDeletePopupComponent,
    ],
    entryComponents: [
        ChatComponent,
        ChatDialogComponent,
        ChatPopupComponent,
        ChatDeleteDialogComponent,
        ChatDeletePopupComponent,
    ],
    providers: [
        ChatService,
        ChatPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicChatModule {}
