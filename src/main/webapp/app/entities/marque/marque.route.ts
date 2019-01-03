import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Marque } from 'app/shared/model/marque.model';
import { MarqueService } from './marque.service';
import { MarqueComponent } from './marque.component';
import { MarqueDetailComponent } from './marque-detail.component';
import { MarqueUpdateComponent } from './marque-update.component';
import { MarqueDeletePopupComponent } from './marque-delete-dialog.component';
import { IMarque } from 'app/shared/model/marque.model';

@Injectable({ providedIn: 'root' })
export class MarqueResolve implements Resolve<IMarque> {
    constructor(private service: MarqueService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Marque> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Marque>) => response.ok),
                map((marque: HttpResponse<Marque>) => marque.body)
            );
        }
        return of(new Marque());
    }
}

export const marqueRoute: Routes = [
    {
        path: 'marque',
        component: MarqueComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cautiondouaneApp.marque.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'marque/:id/view',
        component: MarqueDetailComponent,
        resolve: {
            marque: MarqueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.marque.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'marque/new',
        component: MarqueUpdateComponent,
        resolve: {
            marque: MarqueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.marque.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'marque/:id/edit',
        component: MarqueUpdateComponent,
        resolve: {
            marque: MarqueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.marque.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marquePopupRoute: Routes = [
    {
        path: 'marque/:id/delete',
        component: MarqueDeletePopupComponent,
        resolve: {
            marque: MarqueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.marque.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
