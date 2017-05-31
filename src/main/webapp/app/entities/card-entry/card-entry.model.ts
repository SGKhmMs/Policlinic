import { EntryAttachment } from '../entry-attachment';
import { Appointment } from '../appointment';
export class CardEntry {
    constructor(
        public id?: number,
        public entryText?: string,
        public entryAttachent?: EntryAttachment,
        public appointment?: Appointment,
    ) {
    }
}
