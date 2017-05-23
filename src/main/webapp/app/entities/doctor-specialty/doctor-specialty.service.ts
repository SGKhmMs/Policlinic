import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { DoctorSpecialty } from './doctor-specialty.model';

@Injectable()
export class DoctorSpecialtyService {

    private resourceUrl = 'api/doctor-specialties';

    constructor(private http: Http) { }

    create(doctorSpecialty: DoctorSpecialty): Observable<DoctorSpecialty> {
        const copy = this.convert(doctorSpecialty);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(doctorSpecialty: DoctorSpecialty): Observable<DoctorSpecialty> {
        const copy = this.convert(doctorSpecialty);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<DoctorSpecialty> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }
    private createRequestOption(req?: any): BaseRequestOptions {
        const options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            const params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }

    private convert(doctorSpecialty: DoctorSpecialty): DoctorSpecialty {
        const copy: DoctorSpecialty = Object.assign({}, doctorSpecialty);
        return copy;
    }
}