import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDeclaration } from 'app/shared/model/declaration.model';

type EntityResponseType = HttpResponse<IDeclaration>;
type EntityArrayResponseType = HttpResponse<IDeclaration[]>;

@Injectable({ providedIn: 'root' })
export class DeclarationService {
    public resourceUrl = SERVER_API_URL + 'api/declarations';

    constructor(protected http: HttpClient) {}

    create(declaration: IDeclaration): Observable<EntityResponseType> {
        return this.http.post<IDeclaration>(this.resourceUrl, declaration, { observe: 'response' });
    }

    update(declaration: IDeclaration): Observable<EntityResponseType> {
        return this.http.put<IDeclaration>(this.resourceUrl, declaration, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDeclaration>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDeclaration[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
