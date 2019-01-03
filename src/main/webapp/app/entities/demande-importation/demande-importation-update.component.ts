import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IDemandeImportation } from 'app/shared/model/demande-importation.model';
import { DemandeImportationService } from './demande-importation.service';

@Component({
    selector: 'jhi-demande-importation-update',
    templateUrl: './demande-importation-update.component.html'
})
export class DemandeImportationUpdateComponent implements OnInit {
    demandeImportation: IDemandeImportation;
    isSaving: boolean;
    dateReceptionFactureDp: any;
    dateDepotFactureDp: any;
    dateReceptionPRDp: any;

    constructor(protected demandeImportationService: DemandeImportationService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ demandeImportation }) => {
            this.demandeImportation = demandeImportation;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.demandeImportation.id !== undefined) {
            this.subscribeToSaveResponse(this.demandeImportationService.update(this.demandeImportation));
        } else {
            this.subscribeToSaveResponse(this.demandeImportationService.create(this.demandeImportation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemandeImportation>>) {
        result.subscribe((res: HttpResponse<IDemandeImportation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
