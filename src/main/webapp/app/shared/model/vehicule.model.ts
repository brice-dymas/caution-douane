import { IMarque } from 'app/shared/model//marque.model';
import { ICaution } from 'app/shared/model//caution.model';
import { IRVC } from 'app/shared/model//rvc.model';
import { IAppurement } from 'app/shared/model//appurement.model';
import { ITypeVehicule } from 'app/shared/model//type-vehicule.model';

export interface IVehicule {
    id?: number;
    numeroChassis?: string;
    marque?: IMarque;
    caution?: ICaution;
    rvc?: IRVC;
    appurement?: IAppurement;
    typeVehicule?: ITypeVehicule;
}

export class Vehicule implements IVehicule {
    constructor(
        public id?: number,
        public numeroChassis?: string,
        public marque?: IMarque,
        public caution?: ICaution,
        public rvc?: IRVC,
        public appurement?: IAppurement,
        public typeVehicule?: ITypeVehicule
    ) {}
}
