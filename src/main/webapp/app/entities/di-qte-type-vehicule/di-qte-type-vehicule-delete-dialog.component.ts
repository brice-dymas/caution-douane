import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDIQteTypeVehicule } from 'app/shared/model/di-qte-type-vehicule.model';
import { DIQteTypeVehiculeService } from './di-qte-type-vehicule.service';

@Component({
    selector: 'jhi-di-qte-type-vehicule-delete-dialog',
    templateUrl: './di-qte-type-vehicule-delete-dialog.component.html'
})
export class DIQteTypeVehiculeDeleteDialogComponent {
    dIQteTypeVehicule: IDIQteTypeVehicule;

    constructor(
        protected dIQteTypeVehiculeService: DIQteTypeVehiculeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dIQteTypeVehiculeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'dIQteTypeVehiculeListModification',
                content: 'Deleted an dIQteTypeVehicule'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-di-qte-type-vehicule-delete-popup',
    template: ''
})
export class DIQteTypeVehiculeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dIQteTypeVehicule }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DIQteTypeVehiculeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.dIQteTypeVehicule = dIQteTypeVehicule;
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
