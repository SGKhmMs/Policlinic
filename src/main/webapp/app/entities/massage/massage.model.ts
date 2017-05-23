import { Chat } from '../chat';
export class Massage {
    constructor(
        public id?: number,
        public timeOfSending?: any,
        public sender?: string,
        public text?: string,
        public chat?: Chat,
    ) {
    }
}
