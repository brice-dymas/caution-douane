import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeVehicule } from 'app/shared/model/type-vehicule.model';
import { TypeVehiculeService } from './type-vehicule.service';
import { TypeVehiculeComponent } from './type-vehicule.component';
import { TypeVehiculeDetailComponent } from './type-vehicule-detail.component';
import { TypeVehiculeUpdateComponent } from './type-vehicule-update.component';
import { TypeVehiculeDeletePopupComponent } from './type-vehicule-delete-dialog.component';
import { ITypeVehicule } from 'app/shared/model/type-vehicule.model';

@Injectable({ providedIn: 'root' })
export class TypeVehiculeResolve implements Resolve<ITypeVehicule> {
    constructor(private service: TypeVehiculeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TypeVehicule> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TypeVehicule>) => response.ok),
                map((typeVehicule: HttpResponse<TypeVehicule>) => typeVehicule.body)
            );
        }
        return of(new TypeVehicule());
    }
}

export const typeVehiculeRoute: Routes = [
    {
        path: 'type-vehicule',
        component: TypeVehiculeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cautiondouaneApp.typeVehicule.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'type-vehicule/:id/view',
        component: TypeVehiculeDetailComponent,
        resolve: {
            typeVehicule: TypeVehiculeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.typeVehicule.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'type-vehicule/new',
        component: TypeVehiculeUpdateComponent,
        resolve: {
            typeVehicule: TypeVehiculeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.typeVehicule.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'type-vehicule/:id/edit',
        component: TypeVehiculeUpdateComponent,
        resolve: {
            typeVehicule: TypeVehiculeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.typeVehicule.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeVehiculePopupRoute: Routes = [
    {
        path: 'type-vehicule/:id/delete',
        component: TypeVehiculeDeletePopupComponent,
        resolve: {
            typeVehicule: TypeVehiculeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.typeVehicule.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
