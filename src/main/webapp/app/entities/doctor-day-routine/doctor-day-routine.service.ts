import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { DoctorDayRoutine } from './doctor-day-routine.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DoctorDayRoutineService {

    private resourceUrl = 'api/doctor-day-routines';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(doctorDayRoutine: DoctorDayRoutine): Observable<DoctorDayRoutine> {
        const copy = this.convert(doctorDayRoutine);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(doctorDayRoutine: DoctorDayRoutine): Observable<DoctorDayRoutine> {
        const copy = this.convert(doctorDayRoutine);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<DoctorDayRoutine> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convertItemFromServer(entity: any) {
        entity.dayBeginTime = this.dateUtils
            .convertDateTimeFromServer(entity.dayBeginTime);
        entity.dayEndTime = this.dateUtils
            .convertDateTimeFromServer(entity.dayEndTime);
        entity.date = this.dateUtils
            .convertLocalDateFromServer(entity.date);
    }

    private convert(doctorDayRoutine: DoctorDayRoutine): DoctorDayRoutine {
        const copy: DoctorDayRoutine = Object.assign({}, doctorDayRoutine);

        copy.dayBeginTime = this.dateUtils.toDate(doctorDayRoutine.dayBeginTime);

        copy.dayEndTime = this.dateUtils.toDate(doctorDayRoutine.dayEndTime);
        copy.date = this.dateUtils
            .convertLocalDateToServer(doctorDayRoutine.date);
        return copy;
    }
}
