import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RVC } from 'app/shared/model/rvc.model';
import { RVCService } from './rvc.service';
import { RVCComponent } from './rvc.component';
import { RVCDetailComponent } from './rvc-detail.component';
import { RVCUpdateComponent } from './rvc-update.component';
import { RVCDeletePopupComponent } from './rvc-delete-dialog.component';
import { IRVC } from 'app/shared/model/rvc.model';

@Injectable({ providedIn: 'root' })
export class RVCResolve implements Resolve<IRVC> {
    constructor(private service: RVCService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RVC> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RVC>) => response.ok),
                map((rVC: HttpResponse<RVC>) => rVC.body)
            );
        }
        return of(new RVC());
    }
}

export const rVCRoute: Routes = [
    {
        path: 'rvc',
        component: RVCComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cautiondouaneApp.rVC.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rvc/:id/view',
        component: RVCDetailComponent,
        resolve: {
            rVC: RVCResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.rVC.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rvc/new',
        component: RVCUpdateComponent,
        resolve: {
            rVC: RVCResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.rVC.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rvc/:id/edit',
        component: RVCUpdateComponent,
        resolve: {
            rVC: RVCResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.rVC.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rVCPopupRoute: Routes = [
    {
        path: 'rvc/:id/delete',
        component: RVCDeletePopupComponent,
        resolve: {
            rVC: RVCResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.rVC.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
