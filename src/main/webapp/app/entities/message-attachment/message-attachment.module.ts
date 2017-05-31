import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClinicSharedModule } from '../../shared';
import {
    MessageAttachmentService,
    MessageAttachmentPopupService,
    MessageAttachmentComponent,
    MessageAttachmentDetailComponent,
    MessageAttachmentDialogComponent,
    MessageAttachmentPopupComponent,
    MessageAttachmentDeletePopupComponent,
    MessageAttachmentDeleteDialogComponent,
    messageAttachmentRoute,
    messageAttachmentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...messageAttachmentRoute,
    ...messageAttachmentPopupRoute,
];

@NgModule({
    imports: [
        ClinicSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MessageAttachmentComponent,
        MessageAttachmentDetailComponent,
        MessageAttachmentDialogComponent,
        MessageAttachmentDeleteDialogComponent,
        MessageAttachmentPopupComponent,
        MessageAttachmentDeletePopupComponent,
    ],
    entryComponents: [
        MessageAttachmentComponent,
        MessageAttachmentDialogComponent,
        MessageAttachmentPopupComponent,
        MessageAttachmentDeleteDialogComponent,
        MessageAttachmentDeletePopupComponent,
    ],
    providers: [
        MessageAttachmentService,
        MessageAttachmentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicMessageAttachmentModule {}
