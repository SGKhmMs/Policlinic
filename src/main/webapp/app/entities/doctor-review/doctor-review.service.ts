import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { DoctorReview } from './doctor-review.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DoctorReviewService {

    private resourceUrl = 'api/doctor-reviews';

    constructor(private http: Http) { }

    create(doctorReview: DoctorReview): Observable<DoctorReview> {
        const copy = this.convert(doctorReview);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(doctorReview: DoctorReview): Observable<DoctorReview> {
        const copy = this.convert(doctorReview);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<DoctorReview> {
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

    private convert(doctorReview: DoctorReview): DoctorReview {
        const copy: DoctorReview = Object.assign({}, doctorReview);
        return copy;
    }
}
