import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IEmploye } from 'app/shared/model/employe.model';
import { EmployeService } from './employe.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-employe-update',
    templateUrl: './employe-update.component.html'
})
export class EmployeUpdateComponent implements OnInit {
    employe: IEmploye;
    isSaving: boolean;

    users: IUser[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected employeService: EmployeService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ employe }) => {
            this.employe = employe;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.employe.id !== undefined) {
            this.subscribeToSaveResponse(this.employeService.update(this.employe));
        } else {
            this.subscribeToSaveResponse(this.employeService.create(this.employe));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmploye>>) {
        result.subscribe((res: HttpResponse<IEmploye>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
