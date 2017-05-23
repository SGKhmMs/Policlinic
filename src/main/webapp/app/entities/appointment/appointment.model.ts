import { Doctor } from '../doctor';
import { Client } from '../client';
import { ServiceOnAppointment } from '../service-on-appointment';
export class Appointment {
    constructor(
        public id?: number,
        public beginTime?: any,
        public endTime?: any,
        public price?: number,
        public doctor?: Doctor,
        public client?: Client,
        public serviceOnAppointment?: ServiceOnAppointment,
    ) {
    }
}
