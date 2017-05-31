import { Appointment } from '../appointment';
export class Client {
    constructor(
        public id?: number,
        public name?: string,
        public surname?: string,
        public lastname?: string,
        public phone?: string,
        public photo?: any,
        public appointment?: Appointment,
    ) {
    }
}
