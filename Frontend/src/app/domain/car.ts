import { FuelType } from "../enum/fuel-type";
import { Image } from "./image";
import { Rent } from "./rent";

export class Car {
    id: number;
    brand: string;
    model: string;
    year: number;
    color: string;
    seatCount: string;
    fuelType: FuelType;
    pricePerDay: number;
    available: boolean;
    images: Image[];
    location: Location;
    rents: Rent[];
}
