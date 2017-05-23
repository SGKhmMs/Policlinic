import { Clinic } from '../clinic';
export class ClinicModerator {
    constructor(
        public id?: number,
        public name?: string,
        public surname?: string,
        public lastname?: string,
        public email?: string,
        public phone?: string,
        public passHash?: number,
        public clinic?: Clinic,
    ) {
    }
}
