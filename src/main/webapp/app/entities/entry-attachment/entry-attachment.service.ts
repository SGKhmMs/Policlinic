import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { EntryAttachment } from './entry-attachment.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EntryAttachmentService {

    private resourceUrl = 'api/entry-attachments';

    constructor(private http: Http) { }

    create(entryAttachment: EntryAttachment): Observable<EntryAttachment> {
        const copy = this.convert(entryAttachment);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(entryAttachment: EntryAttachment): Observable<EntryAttachment> {
        const copy = this.convert(entryAttachment);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<EntryAttachment> {
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

    private convert(entryAttachment: EntryAttachment): EntryAttachment {
        const copy: EntryAttachment = Object.assign({}, entryAttachment);
        return copy;
    }
}
