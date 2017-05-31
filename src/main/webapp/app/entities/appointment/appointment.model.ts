import { Client } from '../client';
import { RoutineCase } from '../routine-case';
import { AppointmentOperation } from '../appointment-operation';
import { DoctorReview } from '../doctor-review';
import { CardEntry } from '../card-entry';
export class Appointment {
    constructor(
        public id?: number,
        public price?: number,
        public client?: Client,
        public routineCase?: RoutineCase,
        public appointmentOperation?: AppointmentOperation,
        public doctorRewiew?: DoctorReview,
        public cardEntry?: CardEntry,
    ) {
    }
}
