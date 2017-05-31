import { Client } from '../client';
import { Doctor } from '../doctor';
import { Message } from '../message';
export class Chat {
    constructor(
        public id?: number,
        public client?: Client,
        public doctor?: Doctor,
        public massage?: Message,
    ) {
    }
}
