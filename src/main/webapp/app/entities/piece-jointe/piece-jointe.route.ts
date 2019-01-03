import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PieceJointe } from 'app/shared/model/piece-jointe.model';
import { PieceJointeService } from './piece-jointe.service';
import { PieceJointeComponent } from './piece-jointe.component';
import { PieceJointeDetailComponent } from './piece-jointe-detail.component';
import { PieceJointeUpdateComponent } from './piece-jointe-update.component';
import { PieceJointeDeletePopupComponent } from './piece-jointe-delete-dialog.component';
import { IPieceJointe } from 'app/shared/model/piece-jointe.model';

@Injectable({ providedIn: 'root' })
export class PieceJointeResolve implements Resolve<IPieceJointe> {
    constructor(private service: PieceJointeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<PieceJointe> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PieceJointe>) => response.ok),
                map((pieceJointe: HttpResponse<PieceJointe>) => pieceJointe.body)
            );
        }
        return of(new PieceJointe());
    }
}

export const pieceJointeRoute: Routes = [
    {
        path: 'piece-jointe',
        component: PieceJointeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cautiondouaneApp.pieceJointe.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'piece-jointe/:id/view',
        component: PieceJointeDetailComponent,
        resolve: {
            pieceJointe: PieceJointeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.pieceJointe.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'piece-jointe/new',
        component: PieceJointeUpdateComponent,
        resolve: {
            pieceJointe: PieceJointeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.pieceJointe.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'piece-jointe/:id/edit',
        component: PieceJointeUpdateComponent,
        resolve: {
            pieceJointe: PieceJointeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.pieceJointe.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pieceJointePopupRoute: Routes = [
    {
        path: 'piece-jointe/:id/delete',
        component: PieceJointeDeletePopupComponent,
        resolve: {
            pieceJointe: PieceJointeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cautiondouaneApp.pieceJointe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
