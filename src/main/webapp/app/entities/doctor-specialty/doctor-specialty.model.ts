import { Doctor } from '../doctor';
import { Specialty } from '../specialty';
export class DoctorSpecialty {
    constructor(
        public id?: number,
        public doctor?: Doctor,
        public specialty?: Specialty,
    ) {
    }
}
