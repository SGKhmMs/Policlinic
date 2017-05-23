import { Service } from '../service';
export class ServiceOnAppointment {
    constructor(
        public id?: number,
        public price?: number,
        public service?: Service,
    ) {
    }
}
