import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ClientAdress } from './client-adress.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ClientAdressService {

    private resourceUrl = 'api/client-adresses';

    constructor(private http: Http) { }

    create(clientAdress: ClientAdress): Observable<ClientAdress> {
        const copy = this.convert(clientAdress);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(clientAdress: ClientAdress): Observable<ClientAdress> {
        const copy = this.convert(clientAdress);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ClientAdress> {
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

    private convert(clientAdress: ClientAdress): ClientAdress {
        const copy: ClientAdress = Object.assign({}, clientAdress);
        return copy;
    }
}
