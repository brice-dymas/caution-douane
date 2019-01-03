import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IMarque } from 'app/shared/model/marque.model';
import { MarqueService } from './marque.service';

@Component({
    selector: 'jhi-marque-update',
    templateUrl: './marque-update.component.html'
})
export class MarqueUpdateComponent implements OnInit {
    marque: IMarque;
    isSaving: boolean;

    constructor(protected marqueService: MarqueService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ marque }) => {
            this.marque = marque;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.marque.id !== undefined) {
            this.subscribeToSaveResponse(this.marqueService.update(this.marque));
        } else {
            this.subscribeToSaveResponse(this.marqueService.create(this.marque));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMarque>>) {
        result.subscribe((res: HttpResponse<IMarque>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
