import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICaution } from 'app/shared/model/caution.model';

type EntityResponseType = HttpResponse<ICaution>;
type EntityArrayResponseType = HttpResponse<ICaution[]>;

@Injectable({ providedIn: 'root' })
export class CautionService {
    public resourceUrl = SERVER_API_URL + 'api/cautions';

    constructor(protected http: HttpClient) {}

    create(caution: ICaution): Observable<EntityResponseType> {
        return this.http.post<ICaution>(this.resourceUrl, caution, { observe: 'response' });
    }

    update(caution: ICaution): Observable<EntityResponseType> {
        return this.http.put<ICaution>(this.resourceUrl, caution, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICaution>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICaution[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
