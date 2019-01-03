import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVehicule } from 'app/shared/model/vehicule.model';
import { VehiculeService } from './vehicule.service';

@Component({
    selector: 'jhi-vehicule-delete-dialog',
    templateUrl: './vehicule-delete-dialog.component.html'
})
export class VehiculeDeleteDialogComponent {
    vehicule: IVehicule;

    constructor(protected vehiculeService: VehiculeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.vehiculeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'vehiculeListModification',
                content: 'Deleted an vehicule'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-vehicule-delete-popup',
    template: ''
})
export class VehiculeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ vehicule }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(VehiculeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.vehicule = vehicule;
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
