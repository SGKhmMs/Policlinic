import { Appointment } from '../appointment';
export class CardEntry {
    constructor(
        public id?: number,
        public attachmentFile?: any,
        public text?: string,
        public appointment?: Appointment,
    ) {
    }
}
