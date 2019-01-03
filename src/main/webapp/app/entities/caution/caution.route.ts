import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Caution } from 'app/shared/model/caution.model';
import { CautionService } from './caution.service';
import { CautionComponent } from './caution.component';
import { CautionDetailComponent } from './caution-detail.component';
import { CautionUpdateComponent } from './caution-update.component';
import { CautionDeletePopupComponent } from './caution-delete-dialog.component';
import { ICaution } from 'app/shared/model/caution.model';

@Injectable({ providedIn: 'root' })
export class CautionResolve implements Resolve<ICaution> {
    constructor(private service: CautionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Caution> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Caution>) => response.ok),
                map((caution: HttpResponse<Caution>) => caution.body)
            );
        }
        return of(new Caution());
    }
}

export const cautionRoute: Routes = [
    {
        path: 'caution',
        component: CautionComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cautiondouaneApp.caution.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'caution/:id/view',
        component: CautionDetailComponent,
        resolve: {
            caution: CautionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.caution.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'caution/new',
        component: CautionUpdateComponent,
        resolve: {
            caution: CautionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.caution.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'caution/:id/edit',
        component: CautionUpdateComponent,
        resolve: {
            caution: CautionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.caution.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cautionPopupRoute: Routes = [
    {
        path: 'caution/:id/delete',
        component: CautionDeletePopupComponent,
        resolve: {
            caution: CautionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.caution.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
