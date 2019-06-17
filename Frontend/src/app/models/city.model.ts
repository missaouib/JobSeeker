import { ID } from '@datorama/akita';

export type City = {
  id: ID;
  date: Date;
  name: string;
  population: number;
  area: number;
  density: number;
  linkedin: number;
  pracuj: number;
  noFluffJobs: number;
  justJoin: number;
  total: number;
  per100k: number;
}

// export class City {
//   constructor(
//     public id: ID,
//     public date: Date,
//     public name: string,
//     public population: number,
//     public area: number,
//     public density: number,
//     public linkedin: number,
//     public pracuj: number,
//     public noFluffJobs: number,
//     public justJoin: number,
//     public total: number,
//     public per100k: number
//   ) {}
// }
