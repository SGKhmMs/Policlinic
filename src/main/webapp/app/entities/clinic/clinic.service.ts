import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Clinic } from './clinic.model';
import { DateUtils } from 'ng-jhipster';

@Injectable()
export class ClinicService {

    private resourceUrl = 'api/clinics';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(clinic: Clinic): Observable<Clinic> {
        const copy = this.convert(clinic);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(clinic: Clinic): Observable<Clinic> {
        const copy = this.convert(clinic);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Clinic> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.workDatBegin = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.workDatBegin);
            jsonResponse.workDayEnd = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.workDayEnd);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): Response {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].workDatBegin = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].workDatBegin);
            jsonResponse[i].workDayEnd = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].workDayEnd);
        }
        res.json().data = jsonResponse;
        return res;
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

    private convert(clinic: Clinic): Clinic {
        const copy: Clinic = Object.assign({}, clinic);

        copy.workDatBegin = this.dateUtils.toDate(clinic.workDatBegin);

        copy.workDayEnd = this.dateUtils.toDate(clinic.workDayEnd);
        return copy;
    }
}
