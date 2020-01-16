import {ID} from '@datorama/akita';

export interface JobOffer {
  id: ID,
  name: string,
  population: number,
  area: number,
  density: number,
  linkedin: number,
  indeed: number,
  pracuj?: number,
  noFluffJobs?: number,
  justJoinIt?: number,
  total: number,
  per100k: number
}
