import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDemandeImportation } from 'app/shared/model/demande-importation.model';

type EntityResponseType = HttpResponse<IDemandeImportation>;
type EntityArrayResponseType = HttpResponse<IDemandeImportation[]>;

@Injectable({ providedIn: 'root' })
export class DemandeImportationService {
    public resourceUrl = SERVER_API_URL + 'api/demande-importations';

    constructor(protected http: HttpClient) {}

    create(demandeImportation: IDemandeImportation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(demandeImportation);
        return this.http
            .post<IDemandeImportation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(demandeImportation: IDemandeImportation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(demandeImportation);
        return this.http
            .put<IDemandeImportation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDemandeImportation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDemandeImportation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(demandeImportation: IDemandeImportation): IDemandeImportation {
        const copy: IDemandeImportation = Object.assign({}, demandeImportation, {
            dateReceptionFacture:
                demandeImportation.dateReceptionFacture != null && demandeImportation.dateReceptionFacture.isValid()
                    ? demandeImportation.dateReceptionFacture.format(DATE_FORMAT)
                    : null,
            dateDepotFacture:
                demandeImportation.dateDepotFacture != null && demandeImportation.dateDepotFacture.isValid()
                    ? demandeImportation.dateDepotFacture.format(DATE_FORMAT)
                    : null,
            dateReceptionPR:
                demandeImportation.dateReceptionPR != null && demandeImportation.dateReceptionPR.isValid()
                    ? demandeImportation.dateReceptionPR.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateReceptionFacture = res.body.dateReceptionFacture != null ? moment(res.body.dateReceptionFacture) : null;
            res.body.dateDepotFacture = res.body.dateDepotFacture != null ? moment(res.body.dateDepotFacture) : null;
            res.body.dateReceptionPR = res.body.dateReceptionPR != null ? moment(res.body.dateReceptionPR) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((demandeImportation: IDemandeImportation) => {
                demandeImportation.dateReceptionFacture =
                    demandeImportation.dateReceptionFacture != null ? moment(demandeImportation.dateReceptionFacture) : null;
                demandeImportation.dateDepotFacture =
                    demandeImportation.dateDepotFacture != null ? moment(demandeImportation.dateDepotFacture) : null;
                demandeImportation.dateReceptionPR =
                    demandeImportation.dateReceptionPR != null ? moment(demandeImportation.dateReceptionPR) : null;
            });
        }
        return res;
    }
}
