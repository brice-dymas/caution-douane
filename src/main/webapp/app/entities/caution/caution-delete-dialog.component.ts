import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICaution } from 'app/shared/model/caution.model';
import { CautionService } from './caution.service';

@Component({
    selector: 'jhi-caution-delete-dialog',
    templateUrl: './caution-delete-dialog.component.html'
})
export class CautionDeleteDialogComponent {
    caution: ICaution;

    constructor(protected cautionService: CautionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cautionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'cautionListModification',
                content: 'Deleted an caution'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-caution-delete-popup',
    template: ''
})
export class CautionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ caution }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CautionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.caution = caution;
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
