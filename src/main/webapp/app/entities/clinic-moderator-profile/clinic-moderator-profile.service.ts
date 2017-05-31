import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ClinicModeratorProfile } from './clinic-moderator-profile.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ClinicModeratorProfileService {

    private resourceUrl = 'api/clinic-moderator-profiles';

    constructor(private http: Http) { }

    create(clinicModeratorProfile: ClinicModeratorProfile): Observable<ClinicModeratorProfile> {
        const copy = this.convert(clinicModeratorProfile);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(clinicModeratorProfile: ClinicModeratorProfile): Observable<ClinicModeratorProfile> {
        const copy = this.convert(clinicModeratorProfile);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ClinicModeratorProfile> {
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

    private convert(clinicModeratorProfile: ClinicModeratorProfile): ClinicModeratorProfile {
        const copy: ClinicModeratorProfile = Object.assign({}, clinicModeratorProfile);
        return copy;
    }
}
