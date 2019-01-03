import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAppurement } from 'app/shared/model/appurement.model';
import { AppurementService } from './appurement.service';

@Component({
    selector: 'jhi-appurement-delete-dialog',
    templateUrl: './appurement-delete-dialog.component.html'
})
export class AppurementDeleteDialogComponent {
    appurement: IAppurement;

    constructor(
        protected appurementService: AppurementService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.appurementService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'appurementListModification',
                content: 'Deleted an appurement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-appurement-delete-popup',
    template: ''
})
export class AppurementDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ appurement }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AppurementDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.appurement = appurement;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
