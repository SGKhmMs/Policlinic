import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { CardEntry } from './card-entry.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CardEntryService {

    private resourceUrl = 'api/card-entries';

    constructor(private http: Http) { }

    create(cardEntry: CardEntry): Observable<CardEntry> {
        const copy = this.convert(cardEntry);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(cardEntry: CardEntry): Observable<CardEntry> {
        const copy = this.convert(cardEntry);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<CardEntry> {
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

    private convert(cardEntry: CardEntry): CardEntry {
        const copy: CardEntry = Object.assign({}, cardEntry);
        return copy;
    }
}
