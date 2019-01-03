import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeVehicule } from 'app/shared/model/type-vehicule.model';
import { TypeVehiculeService } from './type-vehicule.service';

@Component({
    selector: 'jhi-type-vehicule-delete-dialog',
    templateUrl: './type-vehicule-delete-dialog.component.html'
})
export class TypeVehiculeDeleteDialogComponent {
    typeVehicule: ITypeVehicule;

    constructor(
        protected typeVehiculeService: TypeVehiculeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeVehiculeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typeVehiculeListModification',
                content: 'Deleted an typeVehicule'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-vehicule-delete-popup',
    template: ''
})
export class TypeVehiculeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeVehicule }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypeVehiculeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.typeVehicule = typeVehicule;
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
