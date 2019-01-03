export interface ITypeVehicule {
    id?: number;
    numeroType?: string;
    nomType?: string;
}

export class TypeVehicule implements ITypeVehicule {
    constructor(public id?: number, public numeroType?: string, public nomType?: string) {}
}
