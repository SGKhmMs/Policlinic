import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { AppointmentOperation } from './appointment-operation.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AppointmentOperationService {

    private resourceUrl = 'api/appointment-operations';

    constructor(private http: Http) { }

    create(appointmentOperation: AppointmentOperation): Observable<AppointmentOperation> {
        const copy = this.convert(appointmentOperation);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(appointmentOperation: AppointmentOperation): Observable<AppointmentOperation> {
        const copy = this.convert(appointmentOperation);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<AppointmentOperation> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convert(appointmentOperation: AppointmentOperation): AppointmentOperation {
        const copy: AppointmentOperation = Object.assign({}, appointmentOperation);
        return copy;
    }
}
