import { Chat } from '../chat';
import { MessageAttachment } from '../message-attachment';
export class Message {
    constructor(
        public id?: number,
        public messageText?: string,
        public sender?: number,
        public dateTime?: any,
        public chat?: Chat,
        public massageAttachment?: MessageAttachment,
    ) {
    }
}
