import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IVehicule } from 'app/shared/model/vehicule.model';
import { VehiculeService } from './vehicule.service';
import { IMarque } from 'app/shared/model/marque.model';
import { MarqueService } from 'app/entities/marque';
import { ICaution } from 'app/shared/model/caution.model';
import { CautionService } from 'app/entities/caution';
import { IRVC } from 'app/shared/model/rvc.model';
import { RVCService } from 'app/entities/rvc';
import { IAppurement } from 'app/shared/model/appurement.model';
import { AppurementService } from 'app/entities/appurement';
import { ITypeVehicule } from 'app/shared/model/type-vehicule.model';
import { TypeVehiculeService } from 'app/entities/type-vehicule';

@Component({
    selector: 'jhi-vehicule-update',
    templateUrl: './vehicule-update.component.html'
})
export class VehiculeUpdateComponent implements OnInit {
    vehicule: IVehicule;
    isSaving: boolean;

    marques: IMarque[];

    cautions: ICaution[];

    rvcs: IRVC[];

    appurements: IAppurement[];

    typevehicules: ITypeVehicule[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected vehiculeService: VehiculeService,
        protected marqueService: MarqueService,
        protected cautionService: CautionService,
        protected rVCService: RVCService,
        protected appurementService: AppurementService,
        protected typeVehiculeService: TypeVehiculeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ vehicule }) => {
            this.vehicule = vehicule;
        });
        this.marqueService.query().subscribe(
            (res: HttpResponse<IMarque[]>) => {
                this.marques = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.cautionService.query().subscribe(
            (res: HttpResponse<ICaution[]>) => {
                this.cautions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rVCService.query().subscribe(
            (res: HttpResponse<IRVC[]>) => {
                this.rvcs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.appurementService.query().subscribe(
            (res: HttpResponse<IAppurement[]>) => {
                this.appurements = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.typeVehiculeService.query().subscribe(
            (res: HttpResponse<ITypeVehicule[]>) => {
                this.typevehicules = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.vehicule.id !== undefined) {
            this.subscribeToSaveResponse(this.vehiculeService.update(this.vehicule));
        } else {
            this.subscribeToSaveResponse(this.vehiculeService.create(this.vehicule));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicule>>) {
        result.subscribe((res: HttpResponse<IVehicule>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMarqueById(index: number, item: IMarque) {
        return item.id;
    }

    trackCautionById(index: number, item: ICaution) {
        return item.id;
    }

    trackRVCById(index: number, item: IRVC) {
        return item.id;
    }

    trackAppurementById(index: number, item: IAppurement) {
        return item.id;
    }

    trackTypeVehiculeById(index: number, item: ITypeVehicule) {
        return item.id;
    }
}
