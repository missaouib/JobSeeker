import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'spaceBetween3Chars'
})
export class SpaceBetween3Chars implements PipeTransform {

  transform(value: any): String {
    if (value || value === 0) {
      return value.toLocaleString('en-US').replace(/,/g, ' ');
    }
  }

}
