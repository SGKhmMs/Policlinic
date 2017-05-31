import { Doctor } from '../doctor';
import { RoutineCase } from '../routine-case';
export class DoctorDayRoutine {
    constructor(
        public id?: number,
        public dayBeginTime?: any,
        public dayEndTime?: any,
        public date?: any,
        public doctor?: Doctor,
        public routineCase?: RoutineCase,
    ) {
    }
}
