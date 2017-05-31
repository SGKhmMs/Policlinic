import { Message } from '../message';
export class MessageAttachment {
    constructor(
        public id?: number,
        public attachmentFile?: any,
        public message?: Message,
    ) {
    }
}
