import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Declaration } from 'app/shared/model/declaration.model';
import { DeclarationService } from './declaration.service';
import { DeclarationComponent } from './declaration.component';
import { DeclarationDetailComponent } from './declaration-detail.component';
import { DeclarationUpdateComponent } from './declaration-update.component';
import { DeclarationDeletePopupComponent } from './declaration-delete-dialog.component';
import { IDeclaration } from 'app/shared/model/declaration.model';

@Injectable({ providedIn: 'root' })
export class DeclarationResolve implements Resolve<IDeclaration> {
    constructor(private service: DeclarationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Declaration> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Declaration>) => response.ok),
                map((declaration: HttpResponse<Declaration>) => declaration.body)
            );
        }
        return of(new Declaration());
    }
}

export const declarationRoute: Routes = [
    {
        path: 'declaration',
        component: DeclarationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cautiondouaneApp.declaration.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'declaration/:id/view',
        component: DeclarationDetailComponent,
        resolve: {
            declaration: DeclarationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.declaration.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'declaration/new',
        component: DeclarationUpdateComponent,
        resolve: {
            declaration: DeclarationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.declaration.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'declaration/:id/edit',
        component: DeclarationUpdateComponent,
        resolve: {
            declaration: DeclarationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.declaration.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const declarationPopupRoute: Routes = [
    {
        path: 'declaration/:id/delete',
        component: DeclarationDeletePopupComponent,
        resolve: {
            declaration: DeclarationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.declaration.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
