import { Doctor } from '../doctor';
import { Client } from '../client';
export class Chat {
    constructor(
        public id?: number,
        public doctor?: Doctor,
        public client?: Client,
    ) {
    }
}
