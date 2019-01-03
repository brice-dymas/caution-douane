import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DIQteTypeVehicule } from 'app/shared/model/di-qte-type-vehicule.model';
import { DIQteTypeVehiculeService } from './di-qte-type-vehicule.service';
import { DIQteTypeVehiculeComponent } from './di-qte-type-vehicule.component';
import { DIQteTypeVehiculeDetailComponent } from './di-qte-type-vehicule-detail.component';
import { DIQteTypeVehiculeUpdateComponent } from './di-qte-type-vehicule-update.component';
import { DIQteTypeVehiculeDeletePopupComponent } from './di-qte-type-vehicule-delete-dialog.component';
import { IDIQteTypeVehicule } from 'app/shared/model/di-qte-type-vehicule.model';

@Injectable({ providedIn: 'root' })
export class DIQteTypeVehiculeResolve implements Resolve<IDIQteTypeVehicule> {
    constructor(private service: DIQteTypeVehiculeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DIQteTypeVehicule> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DIQteTypeVehicule>) => response.ok),
                map((dIQteTypeVehicule: HttpResponse<DIQteTypeVehicule>) => dIQteTypeVehicule.body)
            );
        }
        return of(new DIQteTypeVehicule());
    }
}

export const dIQteTypeVehiculeRoute: Routes = [
    {
        path: 'di-qte-type-vehicule',
        component: DIQteTypeVehiculeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cautiondouaneApp.dIQteTypeVehicule.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'di-qte-type-vehicule/:id/view',
        component: DIQteTypeVehiculeDetailComponent,
        resolve: {
            dIQteTypeVehicule: DIQteTypeVehiculeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.dIQteTypeVehicule.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'di-qte-type-vehicule/new',
        component: DIQteTypeVehiculeUpdateComponent,
        resolve: {
            dIQteTypeVehicule: DIQteTypeVehiculeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.dIQteTypeVehicule.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'di-qte-type-vehicule/:id/edit',
        component: DIQteTypeVehiculeUpdateComponent,
        resolve: {
            dIQteTypeVehicule: DIQteTypeVehiculeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.dIQteTypeVehicule.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dIQteTypeVehiculePopupRoute: Routes = [
    {
        path: 'di-qte-type-vehicule/:id/delete',
        component: DIQteTypeVehiculeDeletePopupComponent,
        resolve: {
            dIQteTypeVehicule: DIQteTypeVehiculeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.dIQteTypeVehicule.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
