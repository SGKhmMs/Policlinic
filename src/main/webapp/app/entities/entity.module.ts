import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ClinicClientModule } from './client/client.module';
import { ClinicCardEntryModule } from './card-entry/card-entry.module';
import { ClinicDoctorModule } from './doctor/doctor.module';
import { ClinicAppointmentModule } from './appointment/appointment.module';
import { ClinicServiceModule } from './service/service.module';
import { ClinicServiceOnAppointmentModule } from './service-on-appointment/service-on-appointment.module';
import { ClinicDoctorReviewModule } from './doctor-review/doctor-review.module';
import { ClinicSpecialtyModule } from './specialty/specialty.module';
import { ClinicDoctorSpecialtyModule } from './doctor-specialty/doctor-specialty.module';
import { ClinicClinicModule } from './clinic/clinic.module';
import { ClinicClinicDoctorModule } from './clinic-doctor/clinic-doctor.module';
import { ClinicChatModule } from './chat/chat.module';
import { ClinicMassageModule } from './massage/massage.module';
import { ClinicAttechmentModule } from './attechment/attechment.module';
import { ClinicClinicModeratorModule } from './clinic-moderator/clinic-moderator.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ClinicClientModule,
        ClinicCardEntryModule,
        ClinicDoctorModule,
        ClinicAppointmentModule,
        ClinicServiceModule,
        ClinicServiceOnAppointmentModule,
        ClinicDoctorReviewModule,
        ClinicSpecialtyModule,
        ClinicDoctorSpecialtyModule,
        ClinicClinicModule,
        ClinicClinicDoctorModule,
        ClinicChatModule,
        ClinicMassageModule,
        ClinicAttechmentModule,
        ClinicClinicModeratorModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClinicEntityModule {}
