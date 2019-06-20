import { ID } from '@datorama/akita';

export interface Country {
    id: ID,
    date: Date,
    name: string,
    population: number,
    area: number,
    density: number,
    linkedin: number,
    per100k: number
}
