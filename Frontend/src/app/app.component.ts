import { fadeAnimation } from './animations/fade.animation';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  animations: [ fadeAnimation ]
})

export class AppComponent {
  navLinks = [
    { path: '', label: 'IT Job Offers in Poland' },
    { path: 'world', label: 'IT Job Offers in World' },
    { path: 'technology', label: 'Technology Statistics' },
    { path: 'category', label: 'Category Statistics' }
  ];

  public getRouterOutletState(outlet) {
    return outlet.isActivated ? outlet.activatedRoute : '';
  }
}
