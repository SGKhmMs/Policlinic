import { Massage } from '../massage';
export class Attechment {
    constructor(
        public id?: number,
        public contentType?: string,
        public massage?: Massage,
    ) {
    }
}
