import { Car } from "./car";
import { User } from "./user";

export class Rent {
    id: number;
    startDate: Date;
    endDate: Date;
    car: Car;
    user: User;
}
