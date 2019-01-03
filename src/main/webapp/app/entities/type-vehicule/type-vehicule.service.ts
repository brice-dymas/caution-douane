import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeVehicule } from 'app/shared/model/type-vehicule.model';

type EntityResponseType = HttpResponse<ITypeVehicule>;
type EntityArrayResponseType = HttpResponse<ITypeVehicule[]>;

@Injectable({ providedIn: 'root' })
export class TypeVehiculeService {
    public resourceUrl = SERVER_API_URL + 'api/type-vehicules';

    constructor(protected http: HttpClient) {}

    create(typeVehicule: ITypeVehicule): Observable<EntityResponseType> {
        return this.http.post<ITypeVehicule>(this.resourceUrl, typeVehicule, { observe: 'response' });
    }

    update(typeVehicule: ITypeVehicule): Observable<EntityResponseType> {
        return this.http.put<ITypeVehicule>(this.resourceUrl, typeVehicule, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITypeVehicule>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeVehicule[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
