import { DoctorSpecialty } from '../doctor-specialty';
import { DoctorDayRoutine } from '../doctor-day-routine';
export class Doctor {
    constructor(
        public id?: number,
        public name?: string,
        public surname?: string,
        public lastname?: string,
        public phone?: string,
        public photo?: any,
        public doctorSpecialty?: DoctorSpecialty,
        public doctorDayRoutine?: DoctorDayRoutine,
    ) {
    }
}
