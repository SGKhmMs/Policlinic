import { Client } from '../client';
export class ClientAdress {
    constructor(
        public id?: number,
        public adress?: string,
        public client?: Client,
    ) {
    }
}
