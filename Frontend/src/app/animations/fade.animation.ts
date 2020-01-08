import {animate, query, style, transition, trigger} from '@angular/animations';

export const fadeAnimation =
  trigger('fadeAnimation', [
    transition('* => *', [
      query(':enter',
        [
          style({opacity: 0}),
          animate('1s', style({opacity: 1}))
        ],
        {optional: true}
      )
    ])
  ]);
