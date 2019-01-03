import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IBanque } from 'app/shared/model/banque.model';
import { BanqueService } from './banque.service';

@Component({
    selector: 'jhi-banque-update',
    templateUrl: './banque-update.component.html'
})
export class BanqueUpdateComponent implements OnInit {
    banque: IBanque;
    isSaving: boolean;

    constructor(protected banqueService: BanqueService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ banque }) => {
            this.banque = banque;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.banque.id !== undefined) {
            this.subscribeToSaveResponse(this.banqueService.update(this.banque));
        } else {
            this.subscribeToSaveResponse(this.banqueService.create(this.banque));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBanque>>) {
        result.subscribe((res: HttpResponse<IBanque>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
