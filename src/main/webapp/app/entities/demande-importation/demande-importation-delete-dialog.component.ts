import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDemandeImportation } from 'app/shared/model/demande-importation.model';
import { DemandeImportationService } from './demande-importation.service';

@Component({
    selector: 'jhi-demande-importation-delete-dialog',
    templateUrl: './demande-importation-delete-dialog.component.html'
})
export class DemandeImportationDeleteDialogComponent {
    demandeImportation: IDemandeImportation;

    constructor(
        protected demandeImportationService: DemandeImportationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.demandeImportationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'demandeImportationListModification',
                content: 'Deleted an demandeImportation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-demande-importation-delete-popup',
    template: ''
})
export class DemandeImportationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ demandeImportation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DemandeImportationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.demandeImportation = demandeImportation;
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
