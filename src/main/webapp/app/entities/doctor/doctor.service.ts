import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Doctor } from './doctor.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DoctorService {

    private resourceUrl = 'api/doctors';

    constructor(private http: Http) { }

    create(doctor: Doctor): Observable<Doctor> {
        const copy = this.convert(doctor);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(doctor: Doctor): Observable<Doctor> {
        const copy = this.convert(doctor);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Doctor> {
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

    private convert(doctor: Doctor): Doctor {
        const copy: Doctor = Object.assign({}, doctor);
        return copy;
    }
}
