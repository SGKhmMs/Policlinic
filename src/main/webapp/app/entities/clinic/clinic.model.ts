import { ClinicDoctor } from '../clinic-doctor';
import { ClinicModerator } from '../clinic-moderator';
export class Clinic {
    constructor(
        public id?: number,
        public clinicName?: string,
        public adress?: string,
        public beginOfWork?: any,
        public endOfWork?: any,
        public registryPhone?: string,
        public clinicDoctor?: ClinicDoctor,
        public clinicModerator?: ClinicModerator,
    ) {
    }
}
