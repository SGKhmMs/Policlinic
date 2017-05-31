import { Client } from '../client';
export class ClientProfile {
    constructor(
        public id?: number,
        public email?: string,
        public passHash?: number,
        public client?: Client,
    ) {
    }
}
