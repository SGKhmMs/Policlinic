import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { MessageAttachment } from './message-attachment.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MessageAttachmentService {

    private resourceUrl = 'api/message-attachments';

    constructor(private http: Http) { }

    create(messageAttachment: MessageAttachment): Observable<MessageAttachment> {
        const copy = this.convert(messageAttachment);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(messageAttachment: MessageAttachment): Observable<MessageAttachment> {
        const copy = this.convert(messageAttachment);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<MessageAttachment> {
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

    private convert(messageAttachment: MessageAttachment): MessageAttachment {
        const copy: MessageAttachment = Object.assign({}, messageAttachment);
        return copy;
    }
}
