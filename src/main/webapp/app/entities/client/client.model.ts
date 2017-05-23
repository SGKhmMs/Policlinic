export class Client {
    constructor(
        public id?: number,
        public name?: string,
        public surname?: string,
        public lastname?: string,
        public phone?: string,
        public adress?: string,
        public email?: string,
        public passHash?: number,
        public birth_date?: any,
        public photo?: any,
    ) {
    }
}
