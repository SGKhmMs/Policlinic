import { CardEntry } from '../card-entry';
export class EntryAttachment {
    constructor(
        public id?: number,
        public attachmentFile?: any,
        public cardEntry?: CardEntry,
    ) {
    }
}
