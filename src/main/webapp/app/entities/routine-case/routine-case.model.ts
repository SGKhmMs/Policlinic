import { DoctorDayRoutine } from '../doctor-day-routine';
export class RoutineCase {
    constructor(
        public id?: number,
        public beginTime?: any,
        public endTime?: any,
        public description?: string,
        public doctorDayRoutine?: DoctorDayRoutine,
    ) {
    }
}
