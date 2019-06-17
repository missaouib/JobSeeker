import { ID } from '@datorama/akita';

export class Country {
  constructor(
    public id: ID,
    public date: Date,
    public name: string,
    public population: number,
    public area: number,
    public density: number,
    public linkedin: number,
    public per100k: number
  ) {}
}
