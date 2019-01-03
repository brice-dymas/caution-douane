import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPieceJointe } from 'app/shared/model/piece-jointe.model';
import { PieceJointeService } from './piece-jointe.service';

@Component({
    selector: 'jhi-piece-jointe-delete-dialog',
    templateUrl: './piece-jointe-delete-dialog.component.html'
})
export class PieceJointeDeleteDialogComponent {
    pieceJointe: IPieceJointe;

    constructor(
        protected pieceJointeService: PieceJointeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pieceJointeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pieceJointeListModification',
                content: 'Deleted an pieceJointe'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-piece-jointe-delete-popup',
    template: ''
})
export class PieceJointeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pieceJointe }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PieceJointeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.pieceJointe = pieceJointe;
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
