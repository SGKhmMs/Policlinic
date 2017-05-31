import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ClientProfile } from './client-profile.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ClientProfileService {

    private resourceUrl = 'api/client-profiles';

    constructor(private http: Http) { }

    create(clientProfile: ClientProfile): Observable<ClientProfile> {
        const copy = this.convert(clientProfile);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(clientProfile: ClientProfile): Observable<ClientProfile> {
        const copy = this.convert(clientProfile);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ClientProfile> {
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

    private convert(clientProfile: ClientProfile): ClientProfile {
        const copy: ClientProfile = Object.assign({}, clientProfile);
        return copy;
    }
}
