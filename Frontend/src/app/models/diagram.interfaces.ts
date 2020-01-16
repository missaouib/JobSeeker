export interface Diagram {
  name: String,
  series: Array<{name: string, value: string}>
  // series: Series[]
}

export interface Series {
  name: Date,
  value: number
}
