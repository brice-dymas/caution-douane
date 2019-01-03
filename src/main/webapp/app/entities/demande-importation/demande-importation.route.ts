import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DemandeImportation } from 'app/shared/model/demande-importation.model';
import { DemandeImportationService } from './demande-importation.service';
import { DemandeImportationComponent } from './demande-importation.component';
import { DemandeImportationDetailComponent } from './demande-importation-detail.component';
import { DemandeImportationUpdateComponent } from './demande-importation-update.component';
import { DemandeImportationDeletePopupComponent } from './demande-importation-delete-dialog.component';
import { IDemandeImportation } from 'app/shared/model/demande-importation.model';

@Injectable({ providedIn: 'root' })
export class DemandeImportationResolve implements Resolve<IDemandeImportation> {
    constructor(private service: DemandeImportationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DemandeImportation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DemandeImportation>) => response.ok),
                map((demandeImportation: HttpResponse<DemandeImportation>) => demandeImportation.body)
            );
        }
        return of(new DemandeImportation());
    }
}

export const demandeImportationRoute: Routes = [
    {
        path: 'demande-importation',
        component: DemandeImportationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cautiondouaneApp.demandeImportation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'demande-importation/:id/view',
        component: DemandeImportationDetailComponent,
        resolve: {
            demandeImportation: DemandeImportationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.demandeImportation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'demande-importation/new',
        component: DemandeImportationUpdateComponent,
        resolve: {
            demandeImportation: DemandeImportationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.demandeImportation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'demande-importation/:id/edit',
        component: DemandeImportationUpdateComponent,
        resolve: {
            demandeImportation: DemandeImportationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.demandeImportation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const demandeImportationPopupRoute: Routes = [
    {
        path: 'demande-importation/:id/delete',
        component: DemandeImportationDeletePopupComponent,
        resolve: {
            demandeImportation: DemandeImportationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.demandeImportation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
