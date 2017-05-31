import { Appointment } from '../appointment';
export class DoctorReview {
    constructor(
        public id?: number,
        public reviewText?: string,
        public appointment?: Appointment,
    ) {
    }
}
