import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ClinicDoctorModule } from './doctor/doctor.module';
import { ClinicDoctorAdressModule } from './doctor-adress/doctor-adress.module';
import { ClinicDoctorProfileModule } from './doctor-profile/doctor-profile.module';
import { ClinicClinicModule } from './clinic/clinic.module';
import { ClinicClinicModeratorModule } from './clinic-moderator/clinic-moderator.module';
import { ClinicClinicModeratorProfileModule } from './clinic-moderator-profile/clinic-moderator-profile.module';
import { ClinicDoctorDayRoutineModule } from './doctor-day-routine/doctor-day-routine.module';
import { ClinicRoutineCaseModule } from './routine-case/routine-case.module';
import { ClinicCardEntryModule } from './card-entry/card-entry.module';
import { ClinicEntryAttachmentModule } from './entry-attachment/entry-attachment.module';
import { ClinicSpecialtyModule } from './specialty/specialty.module';
import { ClinicClientModule } from './client/client.module';
import { ClinicAppointmentModule } from './appointment/appointment.module';
import { ClinicServiceModule } from './service/service.module';
import { ClinicClientProfileModule } from './client-profile/client-profile.module';
import { ClinicClientAdressModule } from './client-adress/client-adress.module';
import { ClinicClinicDoctorModule } from './clinic-doctor/clinic-doctor.module';
import { ClinicDoctorSpecialtyModule } from './doctor-specialty/doctor-specialty.module';
import { ClinicDoctorReviewModule } from './doctor-review/doctor-review.module';
import { ClinicChatModule } from './chat/chat.module';
import { ClinicMessageModule } from './message/message.module';
import { ClinicMessageAttachmentModule } from './message-attachment/message-attachment.module';
import { ClinicOperationModule } from './operation/operation.module';
import { ClinicAppointmentOperationModule } from './appointment-operation/appointment-operation.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ClinicDoctorModule,
        ClinicDoctorAdressModule,
        ClinicDoctorProfileModule,
        ClinicClinicModule,
        ClinicClinicModeratorModule,
        ClinicClinicModeratorProfileModule,
        ClinicDoctorDayRoutineModule,
        ClinicRoutineCaseModule,
        ClinicCardEntryModule,
        ClinicEntryAttachmentModule,
        ClinicSpecialtyModule,
        ClinicClientModule,
        ClinicAppointmentModule,
        ClinicServiceModule,
        ClinicClientProfileModule,
        ClinicClientAdressModule,
        ClinicClinicDoctorModule,
        ClinicDoctorSpecialtyModule,
        ClinicDoctorReviewModule,
        ClinicChatModule,
        ClinicMessageModule,
        ClinicMessageAttachmentModule,
        ClinicOperationModule,
        ClinicAppointmentOperationModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicEntityModule {}
