import {ID} from '@datorama/akita';

export interface TechnologyStatistics {
  id: ID,
  name: string,
  type: string,
  linkedin: number,
  indeed: number,
  pracuj?: number,
  noFluffJobs?: number,
  justJoinIt?: number,
  total: number
}
