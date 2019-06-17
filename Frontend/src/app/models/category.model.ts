import { ID } from '@datorama/akita';

// export class Category{
//   constructor(
//     public date: Date,
//     public polishName: string,
//     public englishName: string,
//     public pracuj: number
//   ){}
// }

export type Category = {
  id: ID;
  date: Date;
  polishName: String;
  englishName: String;
  pracuj: number;
}
