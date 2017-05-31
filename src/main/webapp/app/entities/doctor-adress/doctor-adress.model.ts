import { Doctor } from '../doctor';
export class DoctorAdress {
    constructor(
        public id?: number,
        public adress?: string,
        public doctor?: Doctor,
    ) {
    }
}
