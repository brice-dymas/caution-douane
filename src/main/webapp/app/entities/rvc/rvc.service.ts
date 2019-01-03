import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRVC } from 'app/shared/model/rvc.model';

type EntityResponseType = HttpResponse<IRVC>;
type EntityArrayResponseType = HttpResponse<IRVC[]>;

@Injectable({ providedIn: 'root' })
export class RVCService {
    public resourceUrl = SERVER_API_URL + 'api/rvcs';

    constructor(protected http: HttpClient) {}

    create(rVC: IRVC): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(rVC);
        return this.http
            .post<IRVC>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(rVC: IRVC): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(rVC);
        return this.http
            .put<IRVC>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRVC>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRVC[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(rVC: IRVC): IRVC {
        const copy: IRVC = Object.assign({}, rVC, {
            heureDepart: rVC.heureDepart != null && rVC.heureDepart.isValid() ? rVC.heureDepart.toJSON() : null,
            heureArrivee: rVC.heureArrivee != null && rVC.heureArrivee.isValid() ? rVC.heureArrivee.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.heureDepart = res.body.heureDepart != null ? moment(res.body.heureDepart) : null;
            res.body.heureArrivee = res.body.heureArrivee != null ? moment(res.body.heureArrivee) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((rVC: IRVC) => {
                rVC.heureDepart = rVC.heureDepart != null ? moment(rVC.heureDepart) : null;
                rVC.heureArrivee = rVC.heureArrivee != null ? moment(rVC.heureArrivee) : null;
            });
        }
        return res;
    }
}
