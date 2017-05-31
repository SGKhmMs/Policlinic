import { Doctor } from '../doctor';
export class DoctorProfile {
    constructor(
        public id?: number,
        public email?: string,
        public passHash?: number,
        public doctor?: Doctor,
    ) {
    }
}
