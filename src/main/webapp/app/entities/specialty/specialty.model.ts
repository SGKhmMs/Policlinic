import { DoctorSpecialty } from '../doctor-specialty';
export class Specialty {
    constructor(
        public id?: number,
        public specialtyName?: string,
        public doctorSpecialty?: DoctorSpecialty,
    ) {
    }
}
