import { Appointment } from '../appointment';
import { Operation } from '../operation';
export class AppointmentOperation {
    constructor(
        public id?: number,
        public price?: number,
        public appointment?: Appointment,
        public operation?: Operation,
    ) {
    }
}
