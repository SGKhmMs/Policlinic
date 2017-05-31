import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Appointment } from './appointment.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AppointmentService {

    private resourceUrl = 'api/appointments';

    constructor(private http: Http) { }

    create(appointment: Appointment): Observable<Appointment> {
        const copy = this.convert(appointment);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(appointment: Appointment): Observable<Appointment> {
        const copy = this.convert(appointment);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Appointment> {
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

    private convert(appointment: Appointment): Appointment {
        const copy: Appointment = Object.assign({}, appointment);
        return copy;
    }
}
