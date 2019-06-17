import { ID } from '@datorama/akita';

export class Technology {
  constructor(
    public id: ID,
    public date: Date,
    public name: string,
    public type: string,
    public linkedin: number,
    public pracuj: number,
    public noFluffJobs: number,
    public justJoin: number,
    public total: number
  ) {}
}
