import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDIQteTypeVehicule } from 'app/shared/model/di-qte-type-vehicule.model';
import { DIQteTypeVehiculeService } from './di-qte-type-vehicule.service';
import { IDemandeImportation } from 'app/shared/model/demande-importation.model';
import { DemandeImportationService } from 'app/entities/demande-importation';
import { ITypeVehicule } from 'app/shared/model/type-vehicule.model';
import { TypeVehiculeService } from 'app/entities/type-vehicule';
import { IMarque } from 'app/shared/model/marque.model';
import { MarqueService } from 'app/entities/marque';

@Component({
    selector: 'jhi-di-qte-type-vehicule-update',
    templateUrl: './di-qte-type-vehicule-update.component.html'
})
export class DIQteTypeVehiculeUpdateComponent implements OnInit {
    dIQteTypeVehicule: IDIQteTypeVehicule;
    isSaving: boolean;

    demandeimportations: IDemandeImportation[];

    typevehicules: ITypeVehicule[];

    marques: IMarque[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected dIQteTypeVehiculeService: DIQteTypeVehiculeService,
        protected demandeImportationService: DemandeImportationService,
        protected typeVehiculeService: TypeVehiculeService,
        protected marqueService: MarqueService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dIQteTypeVehicule }) => {
            this.dIQteTypeVehicule = dIQteTypeVehicule;
        });
        this.demandeImportationService.query().subscribe(
            (res: HttpResponse<IDemandeImportation[]>) => {
                this.demandeimportations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.typeVehiculeService.query().subscribe(
            (res: HttpResponse<ITypeVehicule[]>) => {
                this.typevehicules = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.marqueService.query().subscribe(
            (res: HttpResponse<IMarque[]>) => {
                this.marques = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dIQteTypeVehicule.id !== undefined) {
            this.subscribeToSaveResponse(this.dIQteTypeVehiculeService.update(this.dIQteTypeVehicule));
        } else {
            this.subscribeToSaveResponse(this.dIQteTypeVehiculeService.create(this.dIQteTypeVehicule));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDIQteTypeVehicule>>) {
        result.subscribe((res: HttpResponse<IDIQteTypeVehicule>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackDemandeImportationById(index: number, item: IDemandeImportation) {
        return item.id;
    }

    trackTypeVehiculeById(index: number, item: ITypeVehicule) {
        return item.id;
    }

    trackMarqueById(index: number, item: IMarque) {
        return item.id;
    }
}
