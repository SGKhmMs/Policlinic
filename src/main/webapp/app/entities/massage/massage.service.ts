import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Massage } from './massage.model';
import { DateUtils } from 'ng-jhipster';

@Injectable()
export class MassageService {

    private resourceUrl = 'api/massages';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(massage: Massage): Observable<Massage> {
        const copy = this.convert(massage);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(massage: Massage): Observable<Massage> {
        const copy = this.convert(massage);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Massage> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.timeOfSending = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.timeOfSending);
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
            jsonResponse[i].timeOfSending = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].timeOfSending);
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

    private convert(massage: Massage): Massage {
        const copy: Massage = Object.assign({}, massage);

        copy.timeOfSending = this.dateUtils.toDate(massage.timeOfSending);
        return copy;
    }
}
