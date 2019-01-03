import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMarque } from 'app/shared/model/marque.model';
import { MarqueService } from './marque.service';

@Component({
    selector: 'jhi-marque-delete-dialog',
    templateUrl: './marque-delete-dialog.component.html'
})
export class MarqueDeleteDialogComponent {
    marque: IMarque;

    constructor(protected marqueService: MarqueService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.marqueService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'marqueListModification',
                content: 'Deleted an marque'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-marque-delete-popup',
    template: ''
})
export class MarqueDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ marque }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MarqueDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.marque = marque;
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
