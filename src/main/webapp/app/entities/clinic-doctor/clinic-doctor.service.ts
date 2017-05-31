import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ClinicDoctor } from './clinic-doctor.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ClinicDoctorService {

    private resourceUrl = 'api/clinic-doctors';

    constructor(private http: Http) { }

    create(clinicDoctor: ClinicDoctor): Observable<ClinicDoctor> {
        const copy = this.convert(clinicDoctor);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(clinicDoctor: ClinicDoctor): Observable<ClinicDoctor> {
        const copy = this.convert(clinicDoctor);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ClinicDoctor> {
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

    private convert(clinicDoctor: ClinicDoctor): ClinicDoctor {
        const copy: ClinicDoctor = Object.assign({}, clinicDoctor);
        return copy;
    }
}
