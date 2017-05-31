import { Clinic } from '../clinic';
import { Doctor } from '../doctor';
export class ClinicDoctor {
    constructor(
        public id?: number,
        public clinic?: Clinic,
        public doctor?: Doctor,
    ) {
    }
}
