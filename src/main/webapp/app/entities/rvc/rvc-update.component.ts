import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IRVC } from 'app/shared/model/rvc.model';
import { RVCService } from './rvc.service';
import { IDemandeImportation } from 'app/shared/model/demande-importation.model';
import { DemandeImportationService } from 'app/entities/demande-importation';

@Component({
    selector: 'jhi-rvc-update',
    templateUrl: './rvc-update.component.html'
})
export class RVCUpdateComponent implements OnInit {
    rVC: IRVC;
    isSaving: boolean;

    demandeimportations: IDemandeImportation[];
    heureDepart: string;
    heureArrivee: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected rVCService: RVCService,
        protected demandeImportationService: DemandeImportationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rVC }) => {
            this.rVC = rVC;
            this.heureDepart = this.rVC.heureDepart != null ? this.rVC.heureDepart.format(DATE_TIME_FORMAT) : null;
            this.heureArrivee = this.rVC.heureArrivee != null ? this.rVC.heureArrivee.format(DATE_TIME_FORMAT) : null;
        });
        this.demandeImportationService.query().subscribe(
            (res: HttpResponse<IDemandeImportation[]>) => {
                this.demandeimportations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.rVC.heureDepart = this.heureDepart != null ? moment(this.heureDepart, DATE_TIME_FORMAT) : null;
        this.rVC.heureArrivee = this.heureArrivee != null ? moment(this.heureArrivee, DATE_TIME_FORMAT) : null;
        if (this.rVC.id !== undefined) {
            this.subscribeToSaveResponse(this.rVCService.update(this.rVC));
        } else {
            this.subscribeToSaveResponse(this.rVCService.create(this.rVC));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRVC>>) {
        result.subscribe((res: HttpResponse<IRVC>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
