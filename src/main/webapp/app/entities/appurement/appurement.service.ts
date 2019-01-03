import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAppurement } from 'app/shared/model/appurement.model';

type EntityResponseType = HttpResponse<IAppurement>;
type EntityArrayResponseType = HttpResponse<IAppurement[]>;

@Injectable({ providedIn: 'root' })
export class AppurementService {
    public resourceUrl = SERVER_API_URL + 'api/appurements';

    constructor(protected http: HttpClient) {}

    create(appurement: IAppurement): Observable<EntityResponseType> {
        return this.http.post<IAppurement>(this.resourceUrl, appurement, { observe: 'response' });
    }

    update(appurement: IAppurement): Observable<EntityResponseType> {
        return this.http.put<IAppurement>(this.resourceUrl, appurement, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAppurement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAppurement[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
