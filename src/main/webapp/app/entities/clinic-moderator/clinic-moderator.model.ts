import { Clinic } from '../clinic';
import { ClinicModeratorProfile } from '../clinic-moderator-profile';
export class ClinicModerator {
    constructor(
        public id?: number,
        public surname?: string,
        public name?: string,
        public lastname?: string,
        public phone?: string,
        public clinic?: Clinic,
        public clinicModeratorProfile?: ClinicModeratorProfile,
    ) {
    }
}
