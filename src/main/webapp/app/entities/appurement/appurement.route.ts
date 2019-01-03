import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Appurement } from 'app/shared/model/appurement.model';
import { AppurementService } from './appurement.service';
import { AppurementComponent } from './appurement.component';
import { AppurementDetailComponent } from './appurement-detail.component';
import { AppurementUpdateComponent } from './appurement-update.component';
import { AppurementDeletePopupComponent } from './appurement-delete-dialog.component';
import { IAppurement } from 'app/shared/model/appurement.model';

@Injectable({ providedIn: 'root' })
export class AppurementResolve implements Resolve<IAppurement> {
    constructor(private service: AppurementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Appurement> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Appurement>) => response.ok),
                map((appurement: HttpResponse<Appurement>) => appurement.body)
            );
        }
        return of(new Appurement());
    }
}

export const appurementRoute: Routes = [
    {
        path: 'appurement',
        component: AppurementComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cautiondouaneApp.appurement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'appurement/:id/view',
        component: AppurementDetailComponent,
        resolve: {
            appurement: AppurementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.appurement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'appurement/new',
        component: AppurementUpdateComponent,
        resolve: {
            appurement: AppurementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.appurement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'appurement/:id/edit',
        component: AppurementUpdateComponent,
        resolve: {
            appurement: AppurementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.appurement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const appurementPopupRoute: Routes = [
    {
        path: 'appurement/:id/delete',
        component: AppurementDeletePopupComponent,
        resolve: {
            appurement: AppurementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.appurement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
